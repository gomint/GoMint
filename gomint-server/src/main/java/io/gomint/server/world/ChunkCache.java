/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world;

import io.gomint.server.entity.EntityPlayer;
import com.koloboke.collect.LongCursor;
import com.koloboke.collect.map.LongObjMap;
import com.koloboke.collect.map.hash.HashLongObjMaps;
import com.koloboke.collect.set.LongSet;
import com.koloboke.collect.set.hash.HashLongSets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class ChunkCache {

    private static final Logger LOGGER = LoggerFactory.getLogger( ChunkCache.class );

    // CHECKSTYLE:OFF
    // ==================================== FIELDS ==================================== //
    private final WorldAdapter world;
    private final LongObjMap<ChunkAdapter> cachedChunks;
    private boolean enableAutoSave;
    private long autoSaveInterval;

    // Internals for the GC
    private final BiConsumer<EntityPlayer, ChunkAdapter> viewDistanceConsumer = new BiConsumer<EntityPlayer, ChunkAdapter>() {
        @Override
        public void accept( EntityPlayer entityPlayer, ChunkAdapter chunkAdapter ) {
            int viewDistance = entityPlayer.getViewDistance();

            if ( currentX >= chunkAdapter.getX() - viewDistance && currentX <= chunkAdapter.getX() + viewDistance &&
                    currentZ >= chunkAdapter.getZ() - viewDistance && currentZ <= chunkAdapter.getZ() + viewDistance ) {
                skip.set( true );
            }
        }
    };
    private AtomicBoolean skip = new AtomicBoolean( false );
    private int currentX;
    private int currentZ;

    public ChunkCache( WorldAdapter world ) {
        this.world = world;
        this.cachedChunks = HashLongObjMaps.newMutableMap();
        this.enableAutoSave = world.getServer().getServerConfig().isAutoSave();
        this.autoSaveInterval = world.getServer().getServerConfig().getAutoSaveInterval();
    }
    // CHECKSTYLE:ON

    /**
     * Ticking for Chunk GC
     *
     * @param currentTimeMS The current time in milliseconds. Used to reduce the number of calls to System#currentTimeMillis()
     */
    public void tick( long currentTimeMS ) {
        int spawnXChunk = CoordinateUtils.fromBlockToChunk( (int) this.world.getSpawnLocation().getX() );
        int spawnZChunk = CoordinateUtils.fromBlockToChunk( (int) this.world.getSpawnLocation().getZ() );

        int spawnAreaSize = this.world.getServer().getServerConfig().getAmountOfChunksForSpawnArea();

        // Check for gc
        synchronized ( this.cachedChunks ) {
            // Copy over the current loaded chunk hashes
            LongSet toRemoveHashes = null;
            LongCursor loadedHashes = this.cachedChunks.keySet().cursor();

            while ( loadedHashes.moveNext() ) {
                long chunkHash = loadedHashes.elem();

                ChunkAdapter chunk = this.cachedChunks.get( chunkHash );
                if ( chunk == null ) {
                    continue;
                }

                this.currentX = (int) ( chunkHash >> 32 );
                this.currentZ = (int) ( chunkHash ) + Integer.MIN_VALUE;

                // Check if this is part of the spawn
                if ( spawnAreaSize > 0 ) {
                    if ( this.currentX >= spawnXChunk - spawnAreaSize && this.currentX <= spawnXChunk + spawnAreaSize &&
                            this.currentZ >= spawnZChunk - spawnAreaSize && this.currentZ <= spawnZChunk + spawnAreaSize ) {
                        continue;
                    }
                }

                // Ask this chunk if he wants to be gced
                if ( !chunk.canBeGCed( currentTimeMS ) ) {
                    continue;
                }

                // Calculate the hashes which are used by players view distances
                this.world.getPlayers0().forEach( viewDistanceConsumer );
                if ( skip.get() ) {
                    skip.set( false );
                    continue;
                }

                LOGGER.debug( "Cleaning up chunk @ " + this.currentX + " " + this.currentZ );

                if ( chunk.getLastSavedTimestamp() + this.autoSaveInterval < currentTimeMS ) {
                    this.world.saveChunkAsynchronously( chunk );
                    chunk.setLastSavedTimestamp( currentTimeMS );
                }

                // Ask this chunk if he wants to be gced
                if ( toRemoveHashes == null ) {
                    toRemoveHashes = HashLongSets.newMutableSet();
                }

                toRemoveHashes.add( chunkHash );
            }

            if ( toRemoveHashes != null ) {
                LongCursor toRemoveCursor = toRemoveHashes.cursor();
                while ( toRemoveCursor.moveNext() ) {
                    this.cachedChunks.remove( toRemoveCursor.elem() );
                }
            }
        }
    }

    // ==================================== CHUNK CACHE ==================================== //

    /**
     * Checks whether or not the specified chunk is currently in cache.
     *
     * @param x The x-coordinate of the chunk
     * @param z The z-coordinate of the chunk
     * @return Whether or not the specified chunk is currently in cache
     */
    public boolean hasChunk( int x, int z ) {
        synchronized ( this.cachedChunks ) {
            return this.cachedChunks.containsKey( CoordinateUtils.toLong( x, z ) );
        }
    }

    /**
     * Attempts to get the specified chunk.
     *
     * @param x The x-coordinate of the chunk
     * @param z The z-coordinate of the chunk
     * @return The chunk if it is loaded or null otherwise
     */
    public ChunkAdapter getChunk( int x, int z ) {
        synchronized ( this.cachedChunks ) {
            long chunkHash = CoordinateUtils.toLong( x, z );
            return this.cachedChunks.get( chunkHash );
        }
    }

    /**
     * Puts the specified chunk into the cache thus making it available to the outside
     *
     * @param chunk The chunk to put into the cache
     */
    public void putChunk( ChunkAdapter chunk ) {
        synchronized ( this.cachedChunks ) {
            this.cachedChunks.put( CoordinateUtils.toLong( chunk.getX(), chunk.getZ() ), chunk );
        }
    }

    // ==================================== AUTOSAVE ==================================== //

    /**
     * Checks whether or not the auto-save feature is enabled.
     *
     * @return Whether or not the auto-save feature is enabled
     */
    public boolean isAutosaveEnabled() {
        return this.enableAutoSave;
    }

    /**
     * Gets the interval in milliseconds in which chunks get saved automatically.
     *
     * @return The auto-save interval
     */
    public long getAutoSaveInterval() {
        return this.autoSaveInterval;
    }

    /**
     * Get stored chunk hashes
     *
     * @return array of chunk hashes
     */
    long[] getChunkHashes() {
        synchronized ( this.cachedChunks ) {
            return this.cachedChunks.keySet().toLongArray();
        }
    }

}
