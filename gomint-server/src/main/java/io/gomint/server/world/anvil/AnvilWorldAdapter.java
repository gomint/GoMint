/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil;

import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.world.*;
import io.gomint.taglib.NBTStream;
import io.gomint.taglib.NBTStreamListener;
import io.gomint.world.Difficulty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteOrder;
import java.util.zip.GZIPInputStream;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public final class AnvilWorldAdapter extends WorldAdapter {

    // ==================================== FIELDS ==================================== //

    // I/O
    private int regionXRead;
    private int regionZRead;
    private RegionFile regionFileRead;

    /**
     * Construct and init a new Anvil based World
     *
     * @param server   which has requested to load this world
     * @param worldDir the folder where the world should be in
     */
    private AnvilWorldAdapter( final GoMintServer server, final File worldDir ) {
        super( server, worldDir );
        this.chunkCache = new ChunkCache( this );

        // Load this world
        // CHECKSTYLE:OFF
        try {
            this.loadLevelDat();
            this.prepareSpawnRegion();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
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
     * @throws Exception Thrown in case the world could not be loaded successfully
     */
    public static AnvilWorldAdapter load( GoMintServer server, File pathToWorld ) throws Exception {
        return new AnvilWorldAdapter( server, pathToWorld );
    }

    /**
     * Loads all information about the world given inside the level.dat file found
     * in the world's root directory.
     *
     * @throws IOException Thrown in case the level.dat file could not be loaded
     */
    private void loadLevelDat() throws Exception {
        try {
            File levelDat = new File( this.worldDir, "level.dat" );
            if ( !levelDat.exists() || !levelDat.isFile() ) {
                throw new IOException( "Missing level.dat" );
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

                nbtStream.addListener( new NBTStreamListener() {
                    @Override
                    public void onNBTValue( String path, Object value ) throws Exception {
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
                                // logger.debug( "Found level dat NBT Tag: " + path );
                                break;
                        }
                    }
                } );

                nbtStream.parse();
            }
        } catch ( IOException e ) {
            throw new IOException( "Failed to load anvil world: " + e.getMessage() );
        }
    }

    @Override
    protected ChunkAdapter loadChunk( int x, int z, boolean generate ) {
        ChunkAdapter chunk = this.chunkCache.getChunk( x, z );
        if ( chunk == null ) {
            try {
                int regionX = CoordinateUtils.fromChunkToRegion( x );
                int regionZ = CoordinateUtils.fromChunkToRegion( z );

                RegionFile regionFile = null;
                if ( this.regionFileRead != null && this.regionXRead == regionX && this.regionZRead == regionZ ) {
                    regionFile = this.regionFileRead;
                }

                if ( regionFile == null ) {
                    this.regionFileRead = new RegionFile( this, new File( this.worldDir, String.format( "region%sr.%d.%d.mca", File.separator, regionX, regionZ ) ) );
                    this.regionXRead = regionX;
                    this.regionZRead = regionZ;
                    regionFile = this.regionFileRead;
                }

                try {
                    chunk = regionFile.loadChunk( x, z );
                } catch ( WorldLoadException e ) {
                    // This means the chunk is corrupted, generate a new one?
                    this.logger.error( "Found corrupted chunk in %s, generating a new one if needed", String.format( "region%sr.%d.%d.mca", File.separator, regionX, regionZ ) );
                }

                if ( chunk != null ) {
                    this.chunkCache.putChunk( chunk );
                    chunk.runPostProcessors();
                } else if ( generate ) {
                    return this.generate( x, z );
                }

                return chunk;
            } catch ( IOException e ) {
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
    protected void saveChunk( ChunkAdapter chunk ) {
        if ( chunk == null ) {
            return;
        }

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        int regionX = CoordinateUtils.fromChunkToRegion( chunkX );
        int regionZ = CoordinateUtils.fromChunkToRegion( chunkZ );

        try {
            RegionFile regionFile = null;
            if ( this.regionFileRead != null && this.regionXRead == regionX && this.regionZRead == regionZ ) {
                regionFile = this.regionFileRead;
            }

            if ( regionFile == null ) {
                this.regionFileRead = new RegionFile( this, new File( this.worldDir, String.format( "region%sr.%d.%d.mca", File.separator, regionX, regionZ ) ) );
                this.regionXRead = regionX;
                this.regionZRead = regionZ;
                regionFile = this.regionFileRead;
            }

            regionFile.saveChunk( (AnvilChunkAdapter) chunk, true );
        } catch ( IOException e ) {
            this.logger.error( "Failed to save chunk to region file", e );
        }
    }

}
