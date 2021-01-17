package io.gomint.server.world;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.maintenance.ReportUploader;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.util.Palette;
import io.gomint.server.world.block.Air;
import io.gomint.server.world.storage.TemporaryStorage;
import io.gomint.world.block.Block;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ChunkSlice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkSlice.class);
    private static final ThreadLocal<int[]> INDEX_IDS = ThreadLocal.withInitial(() -> new int[4096]);
    private static final ThreadLocal<Short2IntMap> INDEX_LIST = ThreadLocal.withInitial(() -> {
        Short2IntMap map = new Short2IntOpenHashMap(4096);
        map.defaultReturnValue(-1);
        return map;
    });
    private static final ThreadLocal<IntList> RUNTIME_INDEX = ThreadLocal.withInitial(() -> new IntArrayList(4096));

    protected static final BlockIdentifier AIR_RUNTIME_ID = BlockRuntimeIDs.toBlockIdentifier("minecraft:air", null);

    private final ChunkAdapter chunk;
    private final int sectionY;

    // Cache
    private final int shiftedMinX;
    private final int shiftedMinY;
    private final int shiftedMinZ;

    protected boolean isAllAir = true;

    private final ByteBuf[] blocks = new ByteBuf[2]; // MC currently supports two layers, we init them as we need

    private final NibbleArray blockLight = null; // NibbleArray.create( (short) 4096 );
    private final NibbleArray skyLight = null; // NibbleArray.create( (short) 4096 )

    private Short2ObjectOpenHashMap<TileEntity> tileEntities = null;
    private final Short2ObjectOpenHashMap<TemporaryStorage>[] temporaryStorages = new Short2ObjectOpenHashMap[2];   // MC currently supports two layers, we init them as we need

    private boolean needsPersistence;
    private boolean isReleased;

    public ChunkSlice(ChunkAdapter chunkAdapter, int sectionY) {
        this.chunk = chunkAdapter;
        this.sectionY = sectionY;

        // Calc shifted values for inline getter
        this.shiftedMinX = this.chunk.x << 4;
        this.shiftedMinY = this.sectionY << 4;
        this.shiftedMinZ = this.chunk.z << 4;
    }

    public Short2ObjectOpenHashMap<TileEntity> getTileEntities() {
        return tileEntities;
    }

    public int getSectionY() {
        return sectionY;
    }

    public ChunkAdapter getChunk() {
        return chunk;
    }

    private short getIndex(int x, int y, int z) {
        return (short) ((x << 8) + (z << 4) + y);
    }

    /**
     * Get the ID of the specific block in question
     *
     * @param x     coordinate in this slice (capped to 16)
     * @param y     coordinate in this slice (capped to 16)
     * @param z     coordinate in this slice (capped to 16)
     * @param layer on which the block is
     * @return id of the block
     */
    String getBlock(int x, int y, int z, int layer) {
        return this.getBlock(layer, getIndex(x, y, z));
    }

    String getBlock(int layer, int index) {
        int runtimeId = this.getRuntimeID(layer, index);
        BlockIdentifier identifier = BlockRuntimeIDs.toBlockIdentifier(runtimeId);
        return identifier.blockId();
    }

    /**
     * Get a block by its index
     *
     * @param x     coordinate in this slice (capped to 16)
     * @param y     coordinate in this slice (capped to 16)
     * @param z     coordinate in this slice (capped to 16)
     * @param layer on which the block is
     * @return block id of the index
     */
    int getRuntimeID(int x, int y, int z, int layer) {
        return this.getRuntimeID(layer, getIndex(x, y, z));
    }

    protected short getRuntimeID(int layer, int index) {
        ByteBuf blockStorage = this.blocks[layer];
        if (blockStorage == null) {
            return AIR_RUNTIME_ID.runtimeId();
        }

        return blockStorage.getShort(index << 1);
    }

    public <T extends Block> T getBlockInstanceInternal(short index, int layer, BlockPosition blockLocation) {
        if (blockLocation == null) {
            int blockX = (index >> 8) & 0x0f;
            int blockY = (index) & 0x0f;
            int blockZ = (index >> 4) & 0x0f;

            blockLocation = this.getBlockLocation(blockX, blockY, blockZ);
        }

        short runtimeID = this.getRuntimeID(layer, index);
        if (runtimeID == AIR_RUNTIME_ID.runtimeId()) {
            return (T) this.getAirBlockInstance(blockLocation);
        }

        BlockIdentifier identifier = BlockRuntimeIDs.toBlockIdentifier(runtimeID);
        return (T) this.chunk.world().getServer().blocks().get(identifier, this.skyLight != null ? this.skyLight.get(index) : 0,
            this.blockLight != null ? this.blockLight.get(index) : 0, this.tileEntities != null ? this.tileEntities.get(index) : null,
            new Location(this.chunk.world, blockLocation.x(), blockLocation.y(), blockLocation.z()), blockLocation, layer, this, index);
    }

    <T extends Block> T getBlockInstance(int x, int y, int z, int layer) {
        short index = getIndex(x, y, z);
        return getBlockInstanceInternal(index, layer, getBlockLocation(x, y, z));
    }

    private Air getAirBlockInstance(BlockPosition location) {
        return  this.chunk.world().getServer().blocks().get(AIR_RUNTIME_ID,
                (byte) 15, (byte) 15, null, new Location(this.chunk.world, location.x(), location.y(), location.z()),
            location, 0, null, (short) 0);
    }

    private BlockPosition getBlockLocation(int x, int y, int z) {
        return new BlockPosition(this.shiftedMinX + x, this.shiftedMinY + y, this.shiftedMinZ + z);
    }

    void removeTileEntity(int x, int y, int z) {
        this.removeTileEntityInternal(getIndex(x, y, z));
    }

    private void removeTileEntityInternal(short index) {
        if (this.tileEntities == null) { // Not tiles in this chunk. This happens because on break still wants to reset any tiles on that position even though there might not be one
            return;
        }

        this.tileEntities.remove(index);
        this.needsPersistence = true;
    }

    void addTileEntity(int x, int y, int z, TileEntity tileEntity) {
        this.addTileEntityInternal(getIndex(x, y, z), tileEntity);
    }

    public void addTileEntityInternal(short index, TileEntity tileEntity) {
        if (this.tileEntities == null) {
            this.tileEntities = new Short2ObjectOpenHashMap<>();
        }

        this.tileEntities.put(index, tileEntity);
        this.needsPersistence = true;
    }

    public void setBlock(int x, int y, int z, int layer, int runtimeId) {
        short index = getIndex(x, y, z);
        this.setRuntimeIdInternal(index, layer, runtimeId);
    }

    public void setRuntimeIdInternal(short index, int layer, int runtimeID) {
        if (this.isReleased) {
            LOGGER.warn("Trying to set a block into a released chunk");
            return;
        }

        if (runtimeID != AIR_RUNTIME_ID.runtimeId() && this.blocks[layer] == null) {
            this.blocks[layer] = PooledByteBufAllocator.DEFAULT.directBuffer(4096 * 2); // Defaults to all 0
            for (int i = 0; i < 4096; i++) {
                this.blocks[layer].writeShort(AIR_RUNTIME_ID.runtimeId());
            }

            this.isAllAir = false;
        }

        if (this.blocks[layer] != null) {
            this.blocks[layer].setShort(index * 2, (short) runtimeID);
            this.needsPersistence = true;
        }
    }

    boolean isAllAir() {
        return this.blocks[0] == null;
    }

    public int getAmountOfLayers() {
        return this.blocks[1] != null ? 2 : 1;
    }

    private int log2(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    public TemporaryStorage getTemporaryStorage(int x, int y, int z, int layer) {
        short index = getIndex(x, y, z);

        // Select correct layer
        Short2ObjectOpenHashMap<TemporaryStorage> storage = this.temporaryStorages[layer];
        if (storage == null) {
            storage = new Short2ObjectOpenHashMap<>();
            this.temporaryStorages[layer] = storage;
        }

        TemporaryStorage blockStorage = storage.get(index);
        if (blockStorage == null) {
            blockStorage = new TemporaryStorage();
            storage.put(index, blockStorage);
        }

        return blockStorage;
    }

    public void resetTemporaryStorage(int x, int y, int z, int layer) {
        Short2ObjectOpenHashMap<TemporaryStorage> storage = this.temporaryStorages[layer];
        if (storage == null) {
            return;
        }

        short index = getIndex(x, y, z);
        storage.remove(index);
    }

    public void serializeNetwork(PacketBuffer buffer) {
        buffer.writeByte((byte) 8);

        // Check how many layers we have
        int amountOfLayers = this.getAmountOfLayers();
        buffer.writeByte((byte) amountOfLayers);

        for (int layer = 0; layer < amountOfLayers; layer++) {
            ByteBuf layerBuf = this.blocks[layer];
            int foundIndex = 0;
            int nextIndex = 0;
            int lastRuntimeID = -1;

            int[] indexIDs = INDEX_IDS.get();
            Short2IntMap indexList = INDEX_LIST.get();
            IntList runtimeIndex = RUNTIME_INDEX.get();

            indexList.clear();
            runtimeIndex.clear();

            for (short blockIndex = 0; blockIndex < indexIDs.length; blockIndex++) {
                short runtimeID = layerBuf.getShort(blockIndex << 1);

                if (runtimeID != lastRuntimeID) {
                    foundIndex = indexList.get(runtimeID);
                    if (foundIndex == -1) {
                        runtimeIndex.add(runtimeID);
                        indexList.put(runtimeID, nextIndex);
                        foundIndex = nextIndex;
                        nextIndex++;
                    }

                    lastRuntimeID = runtimeID;
                }

                indexIDs[blockIndex] = foundIndex;
            }

            // Get correct wordsize
            int value = indexList.size();
            if (value == 0) {
                LOGGER.error("Trying to persist without any blocks");
                ReportUploader.create().includeWorlds()
                    .tag("invalid.leveldb.palette.sending")
                    .property("block.count", String.valueOf(indexIDs.length))
                    .upload("Invalid amount of runtime index entries");
            }

            float numberOfBits = log2(value) + 1;

            // Prepare palette
            int amountOfBlocks = MathUtils.fastFloor(32 / numberOfBits);
            Palette palette = new Palette(buffer.getBuffer(), amountOfBlocks, false);

            byte paletteWord = (byte) ((byte) (palette.getPaletteVersion().getVersionId() << 1) | 1);
            buffer.writeByte(paletteWord);
            palette.addIndexIDs(indexIDs);
            palette.finish();

            // Write runtimeIDs
            buffer.writeSignedVarInt(indexList.size());
            runtimeIndex.forEach((IntConsumer) buffer::writeSignedVarInt);
        }
    }

    public List<BlockIdentifier> getBlocks(int layer) {
        List<BlockIdentifier> blocks = new ArrayList<>(4096);

        for (int i = 0; i < 4096; i++) {
            int runtime = this.getRuntimeID(layer, i);
            blocks.add(BlockRuntimeIDs.toBlockIdentifier(runtime));
        }

        return blocks;
    }

    public void release() {
        this.isReleased = true;

        for (ByteBuf block : this.blocks) {
            if (block != null && !block.release()) {
                LOGGER.warn("Chunk got released but block memory is not refcount 0");
            }
        }

        if (this.blockLight != null) {
            this.blockLight.release();
        }

        if (this.skyLight != null) {
            this.skyLight.release();
        }
    }

    public boolean isNeedsPersistence() {
        // Did a block change?
        if (this.needsPersistence) {
            return true;
        }

        // Check for tile entity changes
        if (this.getTileEntities() != null) {
            ObjectIterator<Short2ObjectMap.Entry<TileEntity>> iterator = this.getTileEntities().short2ObjectEntrySet().fastIterator();
            while (iterator.hasNext()) {
                TileEntity tileEntity = iterator.next().getValue();
                if (tileEntity.isNeedsPersistence()) {
                    return true;
                }
            }
        }

        return false;
    }

    public void resetPersistenceFlag() {
        this.needsPersistence = false;

        if (this.getTileEntities() != null) {
            ObjectIterator<Short2ObjectMap.Entry<TileEntity>> iterator = this.getTileEntities().short2ObjectEntrySet().fastIterator();
            while (iterator.hasNext()) {
                TileEntity tileEntity = iterator.next().getValue();
                tileEntity.resetPersistenceFlag();
            }
        }
    }

}
