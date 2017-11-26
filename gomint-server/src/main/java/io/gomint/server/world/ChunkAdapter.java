/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.server.async.Delegate2;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.CommandBlockTileEntity;
import io.gomint.server.entity.tileentity.TileEntities;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketWorldChunk;
import io.gomint.server.util.collection.EntityIDMap;
import io.gomint.server.world.postprocessor.PostProcessor;
import io.gomint.server.world.storage.TemporaryStorage;
import io.gomint.taglib.NBTReader;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.taglib.NBTWriter;
import io.gomint.world.Biome;
import io.gomint.world.Chunk;
import io.gomint.world.block.Block;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.ByteOrder;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@RequiredArgsConstructor
@EqualsAndHashCode( callSuper = false, of = { "x", "z" } )
public class ChunkAdapter implements Chunk {

    private static final Logger LOGGER = LoggerFactory.getLogger( ChunkAdapter.class );

    // CHECKSTYLE:OFF
    // World
    @Getter protected final WorldAdapter world;

    // Networking
    boolean dirty;
    SoftReference<PacketWorldChunk> cachedPacket;

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
    protected EntityIDMap entities = EntityIDMap.withExpectedSize( 20 );

    // Post loading processing
    protected Queue<PostProcessor> postProcessors = new LinkedList<>();

    // CHECKSTYLE:ON

    /**
     * Ticks this chunk for random block updates
     *
     * @param currentTimeMS The current time in milliseconds. Used to reduce the number of calls to System#currentTimeMillis()
     * @param dT            The delta from the full second which has been calculated in the last tick
     */
    public void update( long currentTimeMS, float dT ) {
        for ( ChunkSlice chunkSlice : this.chunkSlices ) {
            // When we hit a nulled slice there is only air left
            if ( chunkSlice == null ) {
                break;
            }

            // Skip for only air chunk slices
            if ( chunkSlice.isAllAir() ) {
                continue;
            }

            this.world.randomUpdateNumber = ( ( this.world.randomUpdateNumber << 2 ) - this.world.randomUpdateNumber ) + 1013904223;
            int blockHash = this.world.randomUpdateNumber >> 2;
            for ( int i = 0; i < 3; ++i, blockHash >>= 10 ) {
                short index = (short) ( blockHash & 0xfff );
                byte blockId = chunkSlice.getBlockInternal( index );
                switch ( blockId ) {
                    case (byte) 244:    // Beetroot
                    case 2:             // Grass
                    case 60:            // Farmland
                    case 110:           // Mycelium
                    case 6:             // Sapling
                    case 16:            // Leaves
                    case (byte) 161:    // Acacia leaves
                    case 78:            // Top snow
                    case 79:            // Ice
                    case 11:            // Stationary lava
                    case 10:            // FlowingLava
                    case 9:             // Stationary water
                    case 8:             // FlowingWater
                        int blockX = ( blockHash >> 8 ) & 0x0f;
                        int blockY = ( blockHash ) & 0x0f;
                        int blockZ = ( blockHash >> 4 ) & 0x0f;

                        Block block = chunkSlice.getBlockInstance( blockX, blockY, blockZ );
                        if ( block instanceof io.gomint.server.world.block.Block ) {
                            long next = ( (io.gomint.server.world.block.Block) block )
                                .update( UpdateReason.RANDOM, currentTimeMS, dT );

                            if ( next > currentTimeMS ) {
                                Location location = block.getLocation();
                                this.world.tickQueue.add( next,
                                    CoordinateUtils.toLong( (int) location.getX(),
                                        (int) location.getY(),
                                        (int) location.getZ() )
                                );
                            }
                        }

                    default:
                        break;
                }
            }
        }
    }

    private ChunkSlice ensureSlice( int y ) {
        ChunkSlice slice = this.chunkSlices[y];
        if ( slice != null ) {
            return slice;
        } else {
            this.chunkSlices[y] = new ChunkSlice( this, y );
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
        this.entities.justPut( player.getEntityId(), player );
    }

    /**
     * Remove a player from this chunk. This is needed to know when we can GC a chunk
     *
     * @param player The player which we want to remove from this chunk
     */
    void removePlayer( EntityPlayer player ) {
        this.players.remove( player );
        this.lastPlayerOnThisChunk = System.currentTimeMillis();
        this.entities.justRemove( player.getEntityId() );
    }

    /**
     * Add a entity to this chunk
     *
     * @param entity The entity which should be added
     */
    void addEntity( Entity entity ) {
        LOGGER.debug( "Adding entity " + entity + " to chunk " + x + ", " + z );
        this.entities.justPut( entity.getEntityId(), entity );
    }

    /**
     * Remove a entity from this chunk
     *
     * @param entity The entity which should be removed
     */
    void removeEntity( Entity entity ) {
        LOGGER.debug( "Removing entity " + entity + " from chunk " + x + ", " + z );
        this.entities.justRemove( entity.getEntityId() );
    }

    /**
     * Remove the dirty state for the chunk and set the batched packet to the
     * cache.
     *
     * @param batch The batch which has been generated to be sent to the clients
     */
    void setCachedPacket( PacketWorldChunk batch ) {
        this.dirty = false;
        this.cachedPacket = new SoftReference<>( batch );
    }

    /**
     * Gets the time at which this chunk was last written out to disk.
     *
     * @return The timestamp this chunk was last written out at
     */
    long getLastSavedTimestamp() {
        return this.lastSavedTimestamp;
    }

    /**
     * Sets the timestamp on which this chunk was last written out to disk.
     *
     * @param timestamp The timestamp to set
     */
    void setLastSavedTimestamp( long timestamp ) {
        this.lastSavedTimestamp = timestamp;
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
    void packageChunk( Delegate2<Long, ChunkAdapter> callback ) {
        if ( !this.dirty && this.cachedPacket != null ) {
            Packet packet = this.cachedPacket.get();
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
    protected void addTileEntity( NBTTagCompound tileEntity ) {
        int x = tileEntity.getInteger( "x", 0 ) & 0xF;
        int y = tileEntity.getInteger( "y", -1 );
        int z = tileEntity.getInteger( "z", 0 ) & 0xF;

        TileEntity tileEntity1 = TileEntities.construct( tileEntity, this.world );
        if ( tileEntity1 != null ) {
            if ( tileEntity1 instanceof CommandBlockTileEntity ) {
                LOGGER.debug( "Custom name: " + ( (CommandBlockTileEntity) tileEntity1 ).getCustomName() );
            }

            ChunkSlice slice = ensureSlice( y >> 4 );
            slice.addTileEntity( x, y - slice.getSectionY() * 16, z, tileEntity1 );
        }
    }

    /**
     * Sets the ID of a block at the specified coordinates given in chunk coordinates.
     *
     * @param x  The x-coordinate of the block
     * @param y  The y-coordinate of the block
     * @param z  The z-coordinate of the block
     * @param id The ID to set the block to
     */
    public void setBlock( int x, int y, int z, int id ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        slice.setBlock( x, y - 16 * ( y >> 4 ), z, (byte) id );
        this.dirty = true;
    }

    /**
     * Sets the ID of a block at the specified coordinates given in chunk coordinates.
     *
     * @param x The x-coordinate of the block
     * @param y The y-coordinate of the block
     * @param z The z-coordinate of the block
     * @return The ID of the block
     */
    public byte getBlock( int x, int y, int z ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        return slice.getBlock( x, y - 16 * ( y >> 4 ), z );
    }

    /**
     * Sets the metadata value of the block at the specified coordinates.
     *
     * @param x    The x-coordinate of the block
     * @param y    The y-coordinate of the block
     * @param z    The z-coordinate of the block
     * @param data The data value to set
     */
    public void setData( int x, int y, int z, byte data ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        slice.setData( x, y - 16 * ( y >> 4 ), z, data );
        this.dirty = true;
    }

    /**
     * Gets the metadata value of the block at the specified coordinates.
     *
     * @param x The x-coordinate of the block
     * @param y The y-coordinate of the block
     * @param z The z-coordinate of the block
     * @return The data value of the block
     */
    public byte getData( int x, int y, int z ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        return slice.getData( x, y - 16 * ( y >> 4 ), z );
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
    public byte getHeight( int x, int z ) {
        return this.height[( z << 4 ) + x];
    }

    /**
     * Sets a block column's biome.
     *
     * @param x     The x-coordinate of the block column
     * @param z     The z-coordinate of the block column
     * @param biome The biome to set
     */
    protected void setBiome( int x, int z, Biome biome ) {
        this.biomes[( x << 4 ) + z] = (byte) biome.getId();
        this.dirty = true;
    }

    /**
     * Gets a block column's biome.
     *
     * @param x The x-coordinate of the block column
     * @param z The z-coordinate of the block column
     * @return The block column's biome
     */
    public Biome getBiome( int x, int z ) {
        return Biome.getBiomeById( this.biomes[( x << 4 ) + z] );
    }

    @Override
    public <T extends Block> T getBlockAt( int x, int y, int z ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        return slice.getBlockInstance( x, y & 0x000000F, z );
    }

    public TemporaryStorage getTemporaryStorage( int x, int y, int z ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        return slice.getTemporaryStorage( x, y - 16 * ( y >> 4 ), z );
    }

    public void resetTemporaryStorage( int x, int y, int z ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        slice.resetTemporaryStorage( x, y - 16 * ( y >> 4 ), z );
    }

    // ==================================== MISCELLANEOUS ==================================== //

    /**
     * Recalculates the height map of the chunk.
     */
    public void calculateHeightmap() {
        for ( int i = 0; i < 16; ++i ) {
            for ( int k = 0; k < 16; ++k ) {
                for ( int j = 255; j > 0; --j ) {
                    if ( this.getBlock( i, j, k ) != 0 ) {
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
    PacketWorldChunk createPackagedData() {
        PacketBuffer buffer = new PacketBuffer( 512 );

        // Detect how much data we can skip
        int topEmpty = 16;
        for ( int i = 15; i >= 0; i-- ) {
            ChunkSlice slice = chunkSlices[i];
            if ( slice == null || slice.isAllAir() ) {
                topEmpty = i;
            } else {
                break;
            }
        }

        buffer.writeByte( (byte) topEmpty );
        for ( int i = 0; i < topEmpty; i++ ) {
            buffer.writeByte( (byte) 0 );
            buffer.writeBytes( ensureSlice( i ).getBytes() );
        }

        buffer.writeBytes( this.height );
        buffer.writeBytes( this.biomes );
        buffer.writeSignedVarInt( 0 );
        buffer.writeSignedVarInt( 0 );

        // Write tile entity data
        Collection<TileEntity> tileEntities = this.getTileEntities();
        if ( tileEntities.size() > 0 ) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            NBTWriter nbtWriter = new NBTWriter( baos, ByteOrder.LITTLE_ENDIAN );
            nbtWriter.setUseVarint( true );

            for ( TileEntity tileEntity : tileEntities ) {
                NBTTagCompound compound = new NBTTagCompound( "" );
                tileEntity.toCompound( compound );

                try {
                    nbtWriter.write( compound );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }

            buffer.writeBytes( baos.toByteArray() );
        }

        PacketWorldChunk packet = new PacketWorldChunk();
        packet.setX( this.x );
        packet.setZ( this.z );
        packet.setData( Arrays.copyOf( buffer.getBuffer(), buffer.getPosition() ) );
        return packet;
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
                tileEntities.addAll( chunkSlice.getTileEntities() );
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
        return this.entities.containsKey( entity.getEntityId() );
    }

    @Override
    public Collection<io.gomint.entity.Entity> getEntities() {
        return this.entities.size() == 0 ? null : this.entities.values();
    }

    public PacketWorldChunk getCachedPacket() {
        if ( this.dirty ) {
            this.cachedPacket.clear();
            this.cachedPacket = new SoftReference<>( createPackagedData() );
            this.dirty = false;
        }

        return this.cachedPacket.get();
    }

    public void setTileEntity( int x, int y, int z, TileEntity tileEntity ) {
        ChunkSlice slice = ensureSlice( y >> 4 );
        slice.addTileEntity( x, y - 16 * ( y >> 4 ), z, tileEntity );
        this.dirty = true;
    }

    public void runPostProcessors() {
        while ( !this.postProcessors.isEmpty() ) {
            this.postProcessors.poll().process();
        }
    }

}
