/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.util.Pair;
import io.gomint.server.world.*;
import io.gomint.taglib.NBTStream;
import io.gomint.world.Difficulty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.*;
import java.nio.ByteOrder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = true )
public final class AnvilWorldAdapter extends WorldAdapter {

    private static final String REGION_FILE_FORMAT = "region%sr.%d.%d.mca";

    // ==================================== FIELDS ==================================== //

    // Cache
    private LoadingCache<Pair<Integer, Integer>, RegionFile> openFileCache = CacheBuilder.newBuilder()
        .expireAfterAccess( 10, TimeUnit.MINUTES )
        .build( new CacheLoader<Pair<Integer, Integer>, RegionFile>() {
            @Override
            public RegionFile load( Pair<Integer, Integer> pair ) throws Exception {
                AnvilWorldAdapter.this.logger.debug( "Opening new region file {}, {}", pair.getFirst(), pair.getSecond() );
                return new RegionFile( AnvilWorldAdapter.this, new File( AnvilWorldAdapter.this.worldDir,
                    String.format( REGION_FILE_FORMAT, File.separator, pair.getFirst(), pair.getSecond() ) ) );
            }
        } );

    // Overrides
    @Getter private boolean overrideConverter;

    /**
     * Construct and init a new Anvil based World
     *
     * @param server   which has requested to load this world
     * @param worldDir the folder where the world should be in
     * @throws WorldLoadException Thrown in case the world could not be loaded successfully
     */
    private AnvilWorldAdapter( final GoMintServer server, final File worldDir ) throws WorldLoadException {
        super( server, worldDir );
        this.chunkCache = new ChunkCache( this );

        // Load this world
        // CHECKSTYLE:OFF
        // Check for non convert override
        File convertOverride = new File( worldDir, "ALREADY_CONVERTED" );
        if ( convertOverride.exists() ) {
            this.overrideConverter = true;
        }

        this.loadLevelDat();
        this.prepareSpawnRegion();
        // CHECKSTYLE:ON
    }

    /**
     * Loads an anvil world given the path to the world's directory. This operation
     * performs synchronously and will at least load the entire spawn region before
     * completing.
     *
     * @param server      The GoMint Server which runs this
     * @param pathToWorld The path to the world's directory
     * @return The anvil world adapter used to access the world
     * @throws WorldLoadException Thrown in case the world could not be loaded successfully
     */
    public static AnvilWorldAdapter load( GoMintServer server, File pathToWorld ) throws WorldLoadException {
        return new AnvilWorldAdapter( server, pathToWorld );
    }

    /**
     * Loads all information about the world given inside the level.dat file found
     * in the world's root directory.
     *
     * @throws WorldLoadException Thrown in case the level.dat file could not be loaded
     */
    private void loadLevelDat() throws WorldLoadException {
        try {
            File levelDat = new File( this.worldDir, "level.dat" );
            if ( !levelDat.exists() || !levelDat.isFile() ) {
                throw new WorldLoadException( "Missing level.dat" );
            }

            // Default the settings
            this.levelName = "";
            this.spawn = new Location( this, 0, 0, 0 );

            // Stream the contents to save memory usage
            try ( InputStream in = new BufferedInputStream( new GZIPInputStream( new FileInputStream( levelDat ) ) ) ) {
                NBTStream nbtStream = new NBTStream(
                    in,
                    ByteOrder.BIG_ENDIAN
                );

                nbtStream.addListener( ( path, value ) -> {
                    switch ( path ) {
                        case ".Data.version":
                            if ( (int) value != 19133 ) {
                                throw new IOException( "unsupported world format" );
                            }
                            break;
                        case ".Data.LevelName":
                            AnvilWorldAdapter.this.levelName = (String) value;
                            break;
                        case ".Data.SpawnX":
                            AnvilWorldAdapter.this.spawn.setX( (int) value );
                            break;
                        case ".Data.SpawnY":
                            AnvilWorldAdapter.this.spawn.setY( (int) value );
                            break;
                        case ".Data.SpawnZ":
                            AnvilWorldAdapter.this.spawn.setZ( (int) value );
                            break;
                        case ".Data.Difficulty":
                            AnvilWorldAdapter.this.difficulty = Difficulty.valueOf( (byte) value );
                            break;
                        default:
                            logger.debug( "Found level dat NBT Tag: {}", path );
                            break;
                    }
                } );

                // CHECKSTYLE:OFF
                try {
                    nbtStream.parse();
                } catch ( Exception e ) {
                    throw new WorldLoadException( "Could not load level.dat NBT: " + e.getMessage() );
                }
                // CHECKSTYLE:ON
            }
        } catch ( IOException e ) {
            throw new WorldLoadException( "Failed to load anvil world: " + e.getMessage() );
        }
    }

    @Override
    protected synchronized ChunkAdapter loadChunk( int x, int z, boolean generate ) {
        ChunkAdapter chunk = this.chunkCache.getChunk( x, z );
        if ( chunk == null ) {
            try {
                int regionX = CoordinateUtils.fromChunkToRegion( x );
                int regionZ = CoordinateUtils.fromChunkToRegion( z );

                RegionFile regionFile = this.openFileCache.get( new Pair<>( regionX, regionZ ) );

                try {
                    chunk = regionFile.loadChunk( x, z );
                } catch ( WorldLoadException e ) {
                    // This means the chunk is corrupted, generate a new one?
                    this.logger.error( "Found corrupted chunk in %s, generating a new one if needed", String.format( REGION_FILE_FORMAT, File.separator, regionX, regionZ ) );
                }

                if ( chunk != null ) {
                    this.chunkCache.putChunk( chunk );
                    chunk.runPostProcessors();
                } else if ( generate ) {
                    return this.generate( x, z );
                }

                return chunk;
            } catch ( IOException | ExecutionException e ) {
                if ( generate ) {
                    return this.generate( x, z );
                } else {
                    return null;
                }
            }
        }

        return chunk;
    }

    @Override
    protected synchronized void saveChunk( ChunkAdapter chunk ) {
        if ( chunk == null ) {
            return;
        }

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        int regionX = CoordinateUtils.fromChunkToRegion( chunkX );
        int regionZ = CoordinateUtils.fromChunkToRegion( chunkZ );

        try {
            RegionFile regionFile = this.openFileCache.get( new Pair<>( regionX, regionZ ) );
            regionFile.saveChunk( (AnvilChunkAdapter) chunk );
        } catch ( IOException | ExecutionException e ) {
            this.logger.error( "Failed to save chunk to region file", e );
        }
    }

}
