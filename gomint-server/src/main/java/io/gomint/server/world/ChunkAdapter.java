/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.async.Delegate2;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.entity.tileentity.TileEntities;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketBatch;
import io.gomint.server.network.packet.PacketWorldChunk;
import io.gomint.server.util.PerformanceHacks;
import io.gomint.server.world.postprocessor.PostProcessor;
import io.gomint.server.world.storage.TemporaryStorage;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.taglib.NBTWriter;
import io.gomint.world.Biome;
import io.gomint.world.Chunk;
import io.gomint.world.WorldLayer;
import io.gomint.world.block.Block;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@ToString( of = { "world", "x", "z" } )
@RequiredArgsConstructor
@EqualsAndHashCode( callSuper = false, of = { "world", "x", "z" } )
public class ChunkAdapter implements Chunk {

    private static final Logger LOGGER = LoggerFactory.getLogger( ChunkAdapter.class );
    private static final AtomicLong LAST_WARNING = new AtomicLong( System.currentTimeMillis() );

    // CHECKSTYLE:OFF
    // World
    @Getter
    protected final WorldAdapter world;

    // Networking
    boolean dirty;
    SoftReference<Packet> cachedPacket;

    // Chunk
    protected final int x;
    protected final int z;
    protected long inhabitedTime;

    // Biomes
    protected byte[] biomes = new byte[16 * 16];

    // Blocks
    @Getter
    protected ChunkSlice[] chunkSlices = new ChunkSlice[16];
    private byte[] height = new byte[16 * 16 * 2];

    // Players / Chunk GC
    protected List<EntityPlayer> players = new ArrayList<>();
    private long lastPlayerOnThisChunk;
    protected long loadedTime;
    protected long lastSavedTimestamp;

    // Entities
    protected Long2ObjectMap<io.gomint.entity.Entity> entities = null;

    // Post loading processing
    protected Queue<PostProcessor> postProcessors = new LinkedList<>();

    // State saving flag
    @Getter
    private boolean needsPersistance;
    @Getter
    @Setter
    private boolean populated;

    // CHECKSTYLE:ON

    /**
     * Ticks this chunk for random block updates
     *
     * @param currentTimeMS The current time in milliseconds. Used to reduce the number of calls to System#currentTimeMillis()
     * @param dT            The delta from the full second which has been calculated in the last tick
     */
    final void tickRandomBlocks( long currentTimeMS, float dT ) {
        for ( ChunkSlice chunkSlice : this.chunkSlices ) {
            if ( chunkSlice != null && !chunkSlice.isAllAir() ) {
                this.tickRandomBlocksForSlice( chunkSlice, currentTimeMS, dT );
            }
        }
    }

    private void tickRandomBlocksForSlice( ChunkSlice chunkSlice, long currentTimeMS, float dT ) {
        int blockHash = this.getRandomBlockHash();
        this.iterateRandomBlocks( chunkSlice, currentTimeMS, dT, blockHash, 0, this.world.getConfig().getRandomUpdatesPerTick() );
    }

    private void iterateRandomBlocks( ChunkSlice chunkSlice, long currentTimeMS, float dT, int blockHash, int i, int randomUpdatesPerTick ) {
        if ( i < randomUpdatesPerTick ) {
            blockHash >>= 10;
            int index = blockHash & 0xfff;
            int blockId = chunkSlice.getBlockInternal( 0, index );
            this.tickRandomBlock( blockHash, blockId, chunkSlice, currentTimeMS, dT );
            this.iterateRandomBlocks( chunkSlice, currentTimeMS, dT, blockHash, i + 1, randomUpdatesPerTick );
        }
    }

    private void tickRandomBlock( int blockHash, int blockId, ChunkSlice chunkSlice, long currentTimeMS, float dT ) {
        switch ( blockId ) {
            case 244:           // Beetroot
            case 2:             // Grass
            case 60:            // Farmland
            case 110:           // Mycelium
            case 6:             // Sapling
            case 16:            // Leaves
            case 161:           // Acacia leaves
            case 78:            // Top snow
            case 79:            // Ice
                this.updateRandomBlock( chunkSlice, blockHash, currentTimeMS, dT );
                break;

            default:
                break;
        }
    }

    private void updateRandomBlock( ChunkSlice chunkSlice, int blockHash, long currentTimeMS, float dT ) {
        int blockX = ( blockHash >> 8 ) & 0x0f;
        int blockY = ( blockHash ) & 0x0f;
        int blockZ = ( blockHash >> 4 ) & 0x0f;

        Block block = chunkSlice.getBlockInstance( blockX, blockY, blockZ, 0 );
        if ( block instanceof io.gomint.server.world.block.Block ) {
            long next = ( (io.gomint.server.world.block.Block) block )
                .update( UpdateReason.RANDOM, currentTimeMS, dT );

            if ( next > currentTimeMS ) {
                this.world.addTickingBlock( next, block.getLocation().toBlockPosition() );
            }
        }
    }

    private int getRandomBlockHash() {
        this.world.randomUpdateNumber = ( ( this.world.randomUpdateNumber << 2 ) - this.world.randomUpdateNumber ) + 1013904223;
        return this.world.randomUpdateNumber >> 2;
    }

    private ChunkSlice ensureSlice( int y ) {
        ChunkSlice slice = this.chunkSlices[y];
        if ( slice != null ) {
            return slice;
        } else {
            this.chunkSlices[y] = PerformanceHacks.isUnsafeEnabled() ? new UnsafeChunkSlice( this, y ) : new ChunkSlice( this, y );
            return this.chunkSlices[y];
        }
    }

    /**
     * Add a player to this chunk. This is needed to know when we can GC a chunk
     *
     * @param player The player which we want to add to this chunk
     */
    void addPlayer( EntityPlayer player ) {
        this.players.add( player );

        if ( this.entities == null ) {
            this.entities = new Long2ObjectOpenHashMap<>();
        }

        this.entities.put( player.getEntityId(), player );
    }

    /**
     * Remove a player from this chunk. This is needed to know when we can GC a chunk
     *
     * @param player The player which we want to remove from this chunk
     */
    void removePlayer( EntityPlayer player ) {
        this.players.remove( player );
        this.lastPlayerOnThisChunk = System.currentTimeMillis();

        if ( this.entities == null ) {
            return;
        }

        this.entities.remove( player.getEntityId() );
        if ( this.entities.size() == 0 ) {
            this.entities = null;
        }
    }

    /**
     * Add a entity to this chunk
     *
     * @param entity The entity which should be added
     */
    protected void addEntity( Entity entity ) {
        if ( this.entities == null ) {
            this.entities = new Long2ObjectOpenHashMap<>();
        }

        this.entities.put( entity.getEntityId(), entity );
    }

    /**
     * Remove a entity from this chunk
     *
     * @param entity The entity which should be removed
     */
    void removeEntity( Entity entity ) {
        if ( this.entities == null ) {
            return;
        }

        this.entities.remove( entity.getEntityId() );
        if ( this.entities.size() == 0 ) {
            this.entities = null;
        }
    }

    /**
     * Remove the dirty state for the chunk and set the batched packet to the
     * cache.
     *
     * @param batch     The batch which has been generated to be sent to the clients
<<<<<<< HEAD
     * @param betaBatch The batch which has been generated to be sent to the beta clients
=======
>>>>>>> 893927c5c519bac56ae7d9e5f6a224f821340990
     */
    void setCachedPacket( Packet batch ) {
        this.dirty = false;
        this.cachedPacket = new SoftReference<>( batch );
    }

    /**
     * Gets the time at which this chunk was last written out to disk.
     *
     * @return The timestamp this chunk was last written out at
     */
    public long getLastSavedTimestamp() {
        return this.lastSavedTimestamp;
    }

    /**
     * Sets the timestamp on which this chunk was last written out to disk.
     *
     * @param timestamp The timestamp to set
     */
    void setLastSavedTimestamp( long timestamp ) {
        this.lastSavedTimestamp = timestamp;
        this.needsPersistance = false;
    }

    // ==================================== MANIPULATION ==================================== //

    /**
     * Makes a request to package this chunk asynchronously. The package that will be
     * given to the provided callback will be a world chunk packet inside a batch packet.
     * <p>
     * This operation is done asynchronously in order to limit how many chunks are being
     * packaged in parallel as well as to cache some chunk packets.
     *
     * @param callback The callback to be invoked once the operation is complete
     */
<<<<<<< HEAD
    void packageChunk( EntityPlayer player, Delegate2<Long, ChunkAdapter> callback ) {
        SoftReference<Packet> cachedPacketRef = ( player.getConnection().getProtocolID() >=
            Protocol.MINECRAFT_PE_NEXT_STABLE_PROTOCOL_VERSION ) ? this.cachedBetaPacket : this.cachedPacket;
=======
    void packageChunk( Delegate2<Long, ChunkAdapter> callback ) {
        SoftReference<Packet> cachedPacketRef = this.cachedPacket;
>>>>>>> 893927c5c519bac56ae7d9e5f6a224f821340990

        if ( !this.dirty && cachedPacketRef != null ) {
            Packet packet = cachedPacketRef.get();
            if ( packet != null ) {
                callback.invoke( CoordinateUtils.toLong( x, z ), this );
            } else {
                this.world.notifyPackageChunk( x, z, callback );
            }
        } else {
            this.world.notifyPackageChunk( x, z, callback );
        }
    }

    /**
     * Checks if this chunk can be gced
     *
     * @param currentTimeMillis The time when this collection cycle started
     * @return true when it can be gced, false when not
     */
    boolean canBeGCed( long currentTimeMillis ) {
        int secondsAfterLeft = this.world.getConfig().getSecondsUntilGCAfterLastPlayerLeft();
        int waitAfterLoad = this.world.getConfig().getWaitAfterLoadForGCSeconds();

        return currentTimeMillis - this.loadedTime > TimeUnit.SECONDS.toMillis( waitAfterLoad ) &&
            this.players.isEmpty() &&
            currentTimeMillis - this.lastPlayerOnThisChunk > TimeUnit.SECONDS.toMillis( secondsAfterLeft );
    }

    /**
     * Return a collection of players which are currently on this chunk
     *
     * @return non modifiable collection of players on this chunk
     */
    public Collection<EntityPlayer> getPlayers() {
        return Collections.unmodifiableCollection( this.players );
    }

    /**
     * Gets the x-coordinate of the chunk.
     *
     * @return The chunk's x-coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the z-coordinate of the chunk.
     *
     * @return The chunk's z-coordinate
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Add a new tile entity to the chunk
     *
     * @param tileEntity The NBT tag of the tile entity which should be added
     */
    protected void addTileEntity( TileEntity tileEntity ) {
        BlockPosition tileEntityLocation = tileEntity.getLocation().toBlockPosition();
        int xPos = tileEntityLocation.getX() & 0xF;
        int yPos = tileEntityLocation.getY();
        int zPos = tileEntityLocation.getZ() & 0xF;

        ChunkSlice slice = ensureSlice( yPos >> 4 );
        slice.addTileEntity( xPos, yPos - slice.getSectionY() * 16, zPos, tileEntity );
    }

    /**
     * Sets the ID of a block at the specified coordinates given in chunk coordinates.
     *
     * @param x     The x-coordinate of the block
     * @param y     The y-coordinate of the block
     * @param z     The z-coordinate of the block
     * @param layer layer on which this block is
     * @param id    The ID to set the block to
     */
    public void setBlock( int x, int y, int z, int layer, int id ) {
        int ySection = y >> 4;
        ChunkSlice slice = ensureSlice( ySection );
        slice.setBlock( x, y - ( ySection << 4 ), z, layer, id );
        this.dirty = true;
        this.needsPersistance = true;
    }

    /**
     * Sets the ID of a block at the specified coordinates given in chunk coordinates.
     *
     * @param x     The x-coordinate of the block
     * @param y     The y-coordinate of the block
     * @param z     The z-coordinate of the block
     * @param layer in which the block is
     * @return The ID of the block
     */
    public int getBlock( int x, int y, int z, int layer ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        return slice.getBlock( x, y - 16 * ( y >> 4 ), z, layer );
    }

    /**
     * Sets the metadata value of the block at the specified coordinates.
     *
     * @param x     The x-coordinate of the block
     * @param y     The y-coordinate of the block
     * @param z     The z-coordinate of the block
     * @param layer layer on which this block is
     * @param data  The data value to set
     */
    public void setData( int x, int y, int z, int layer, byte data ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        slice.setData( x, y - 16 * ( y >> 4 ), z, layer, data );
        this.dirty = true;
        this.needsPersistance = true;
    }

    /**
     * Gets the metadata value of the block at the specified coordinates.
     *
     * @param x     The x-coordinate of the block
     * @param y     The y-coordinate of the block
     * @param z     The z-coordinate of the block
     * @param layer in which the block is
     * @return The data value of the block
     */
    public byte getData( int x, int y, int z, int layer ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        return slice.getData( x, y - 16 * ( y >> 4 ), z, layer );
    }

    /**
     * Sets the maximum block height at a specific coordinate-pair.
     *
     * @param x      The x-coordinate relative to the chunk
     * @param z      The z-coordinate relative to the chunk
     * @param height The maximum block height
     */
    private void setHeight( int x, int z, byte height ) {
        this.height[( z << 4 ) + x] = height;
        this.dirty = true;
    }

    /**
     * Gets the maximum block height at a specific coordinate-pair. Requires the height
     * map to be up-to-date.
     *
     * @param x The x-coordinate relative to the chunk
     * @param z The z-coordinate relative to the chunk
     * @return The maximum block height
     */
    public int getHeight( int x, int z ) {
        return this.height[( z << 4 ) + x] & 0xFF;
    }

    @Override
    public void setBiome( int x, int z, Biome biome ) {
        this.biomes[( x << 4 ) + z] = (byte) biome.getId();
        this.dirty = true;
    }

    @Override
    public Biome getBiome( int x, int z ) {
        return Biome.getBiomeById( this.biomes[( x << 4 ) + z] );
    }

    @Override
    public <T extends Block> T getBlockAt( int x, int y, int z ) {
        return getBlockAt( x, y, z, WorldLayer.NORMAL );
    }

    public <T extends Block> T getBlockAt( int x, int y, int z, int layer ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        return slice.getBlockInstance( x, y & 0x000000F, z, layer );
    }

    @Override
    public <T extends Block> T getBlockAt( int x, int y, int z, WorldLayer layer ) {
        return this.getBlockAt( x, y, z, layer.ordinal() );
    }

    public TemporaryStorage getTemporaryStorage( int x, int y, int z, int layer ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        return slice.getTemporaryStorage( x, y - 16 * ( y >> 4 ), z, layer );
    }

    public void resetTemporaryStorage( int x, int y, int z, int layer ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        slice.resetTemporaryStorage( x, y - 16 * ( y >> 4 ), z, layer );
    }

    // ==================================== MISCELLANEOUS ==================================== //

    /**
     * Recalculates the height map of the chunk.
     *
     * @param maxHeight max height of this chunk. Used to reduce load on the CPU
     */
    public void calculateHeightmap( int maxHeight ) {
        if ( maxHeight == 0 ) {
            return;
        }

        for ( int i = 0; i < 16; ++i ) {
            for ( int k = 0; k < 16; ++k ) {
                for ( int j = ( maxHeight + 16 ) - 1; j > 0; --j ) {
                    if ( this.getBlock( i, j, k, 0 ) != 0 ) { // For height MC uses normal layer (0)
                        this.setHeight( i, k, (byte) j );
                        break;
                    }
                }
            }
        }
    }

    /**
     * Invoked by the world's asynchronous worker thread once the chunk is supposed
     * to actually pack itself into a world chunk packet.
     *
     * @return The world chunk packet that is to be sent
     */
    Packet createPackagedData() {
        PacketBuffer buffer = new PacketBuffer( 16 );

        // Detect how much data we can skip
        int topEmpty = 15;
        for ( int i = 15; i >= 0; i-- ) {
            ChunkSlice slice = this.chunkSlices[i];
            if ( slice == null || slice.isAllAir() ) {
                topEmpty = i;
            } else {
                break;
            }
        }

        buffer.writeByte( (byte) topEmpty );
        for ( int i = 0; i < topEmpty; i++ ) {
            ensureSlice( i ).writeToNetwork( buffer );
        }

        buffer.writeBytes( this.height );
        buffer.writeBytes( this.biomes );
        buffer.writeSignedVarInt( 0 );
        buffer.writeSignedVarInt( 0 );

        // Write tile entity data
        Collection<TileEntity> tileEntities = this.getTileEntities();
        if ( !tileEntities.isEmpty() ) {
            NBTWriter nbtWriter = new NBTWriter( new OutputStream() {
                @Override
                public void write( int b ) throws IOException {
                    buffer.writeByte( (byte) b );
                }
            }, ByteOrder.LITTLE_ENDIAN );
            nbtWriter.setUseVarint( true );

            for ( TileEntity tileEntity : tileEntities ) {
                NBTTagCompound compound = new NBTTagCompound( "" );
                tileEntity.toCompound( compound, SerializationReason.NETWORK );

                try {
                    nbtWriter.write( compound );
                } catch ( IOException e ) {
                    LOGGER.warn( "Could not persist nbt for network", e );
                }
            }
        }

        PacketWorldChunk packet = new PacketWorldChunk();
        packet.setX( this.x );
        packet.setZ( this.z );
        packet.setData( Arrays.copyOf( buffer.getBuffer(), buffer.getPosition() ) );

        // Don't pack the chunk if using TCP
        if ( this.world.getServer().getServerConfig().getListener().isUseTCP() ) {
            return packet;
        }

        return packChunk( packet );
    }

    private PacketBatch packChunk( PacketWorldChunk chunkPacket ) {
        PacketBatch chunkPacketBatch = new PacketBatch();
        PacketBuffer buffer = new PacketBuffer( 64 );
        chunkPacket.serializeHeader( buffer );
        chunkPacket.serialize( buffer, Protocol.MINECRAFT_PE_PROTOCOL_VERSION );

        ByteBuffer finalOut = ByteBuffer.allocate( buffer.getPosition() + 5 );
        writeVarInt( buffer.getPosition(), finalOut );
        finalOut.put( buffer.getBuffer(), buffer.getBufferOffset(), buffer.getBufferOffset() + buffer.getPosition() );
        chunkPacketBatch.setPayload( Arrays.copyOf( finalOut.array(), finalOut.position() ) );

        return chunkPacketBatch;
    }

    private void writeVarInt( int value, ByteBuffer stream ) {
        while ( ( value & -128 ) != 0 ) {
            stream.put( (byte) ( value & 127 | 128 ) );
            value >>>= 7;
        }

        stream.put( (byte) value );
    }

    /**
     * Get all tiles in this chunk for saving the data
     *
     * @return collection of all tiles in this chunks
     */
    public Collection<TileEntity> getTileEntities() {
        List<TileEntity> tileEntities = new ArrayList<>();

        for ( ChunkSlice chunkSlice : this.chunkSlices ) {
            if ( chunkSlice != null ) {
                tileEntities.addAll( chunkSlice.getTileEntities().values() );
            }
        }

        return tileEntities;
    }

    /**
     * Check if this chunk contains the given entity
     *
     * @param entity The entity which should be checked for
     * @return true if the chunk contains that entity, false if not
     */
    public boolean knowsEntity( Entity entity ) {
        return this.entities != null && this.entities.containsKey( entity.getEntityId() );
    }

    @Override
    public <T extends io.gomint.entity.Entity> void iterateEntities( Class<T> entityClass, Consumer<T> entityConsumer ) {
        // Iterate over all chunks
        if ( this.entities != null ) {
            for ( Long2ObjectMap.Entry<io.gomint.entity.Entity> entry : this.entities.long2ObjectEntrySet() ) {
                if ( entityClass.isAssignableFrom( entry.getValue().getClass() ) ) {
                    entityConsumer.accept( (T) entry.getValue() );
                }
            }
        }
    }

    @Override
    public void setBlock( int x, int y, int z, Block block ) {
        this.setBlock( x, y, z, WorldLayer.NORMAL, block );
    }

    @Override
    public void setBlock( int x, int y, int z, WorldLayer layer, Block block ) {
        int layerID = layer.ordinal();

        io.gomint.server.world.block.Block implBlock = (io.gomint.server.world.block.Block) block;

        // Copy block id
        this.setBlock( x, y, z, layerID, implBlock.getBlockId() );

        // Copy metadata
        this.setData( x, y, z, layerID, implBlock.getBlockData() );

        // Copy NBT
        if ( implBlock.getTileEntity() != null ) {
            // Get compound
            NBTTagCompound compound = new NBTTagCompound( "" );
            implBlock.getTileEntity().toCompound( compound, SerializationReason.PERSIST );

            // Change position
            int fullX = CoordinateUtils.getChunkMin( this.x ) + x;
            int fullZ = CoordinateUtils.getChunkMin( this.z ) + z;

            // Change the position
            compound.addValue( "x", fullX );
            compound.addValue( "y", y );
            compound.addValue( "z", fullZ );

            // Create new tile entity
            TileEntity tileEntity = TileEntities.construct( compound, this.world );
            this.setTileEntity( x, y, z, tileEntity );
        }
    }

    public Packet getCachedPacket() {
        if ( this.dirty ) {
            this.cachedPacket.clear();

            this.cachedPacket = new SoftReference<>( createPackagedData() );
            this.dirty = false;
        }

        // Check if we have a object
        Packet packetWorldChunk = this.cachedPacket.get();
        if ( packetWorldChunk == null ) {
            // The packet got cleared from the JVM due to memory limits
            if ( this.world.getServer().getCurrentTickTime() - LAST_WARNING.get() >= 5000 ) {
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberFormat.setMaximumFractionDigits( 2 );

                LOGGER.warn( "We need to create new chunk data for the network. This only happens when the JVM runs low on " +
                        "memory. Please consider raising -Xmx in your start parameters. Current free: {} MB",
                    numberFormat.format( ( Runtime.getRuntime().freeMemory() / (double) 1024 ) / (double) 1024 ) );

                LAST_WARNING.set( this.world.getServer().getCurrentTickTime() );
            }

            return createPackagedData();
        }

        return packetWorldChunk;
    }

    public void setTileEntity( int x, int y, int z, TileEntity tileEntity ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        slice.addTileEntity( x, y - 16 * ( y >> 4 ), z, tileEntity );
        this.dirty = true;
        this.needsPersistance = true;
    }

    public void runPostProcessors() {
        while ( !this.postProcessors.isEmpty() ) {
            this.postProcessors.poll().process();
        }
    }

    public long longHashCode() {
        return CoordinateUtils.toLong( this.x, this.z );
    }

    public Long2ObjectMap<io.gomint.entity.Entity> getEntities() {
        return this.entities;
    }

    public void tickTiles( long currentTimeMS ) {
        for ( ChunkSlice chunkSlice : this.chunkSlices ) {
            if ( chunkSlice != null ) {
                ObjectIterator<Short2ObjectMap.Entry<TileEntity>> iterator = chunkSlice.getTileEntities().short2ObjectEntrySet().fastIterator();
                while ( iterator.hasNext() ) {
                    TileEntity tileEntity = iterator.next().getValue();
                    tileEntity.update( currentTimeMS );

                    if ( tileEntity.isNeedsPersistance() ) {
                        this.needsPersistance = true;
                    }
                }
            }
        }
    }

    public void flagNeedsPersistance() {
        this.needsPersistance = true;
    }

}
