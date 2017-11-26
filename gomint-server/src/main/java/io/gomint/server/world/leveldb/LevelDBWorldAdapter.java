/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.leveldb;

import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.util.DumpUtil;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.ChunkCache;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.WorldLoadException;
import io.gomint.server.world.generator.ConfigurableLayerGenerator;
import io.gomint.server.world.generator.Generators;
import io.gomint.taglib.NBTStream;
import io.gomint.world.Difficulty;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.DbImpl;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LevelDBWorldAdapter extends WorldAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger( LevelDBWorldAdapter.class );

    private DB db;
    private int worldVersion;

    /**
     * Construct and init a new levedb based World
     *
     * @param server   which has requested to load this world
     * @param worldDir the folder where the world should be in
     */
    private LevelDBWorldAdapter( GoMintServer server, File worldDir ) {
        super( server, worldDir );
        this.chunkCache = new ChunkCache( this );

        // CHECKSTYLE:OFF
        try {
            this.loadLevelDat();

            // We only support storage version 3 and up (MC:PE >= 1.0)
            if ( this.worldVersion < 3 ) {
                throw new WorldLoadException( "Version of the world is too old. Please update your MC:PE and import this world. After that you can use the exported version again." );
            }

            this.db = Iq80DBFactory.factory.open( new File( this.worldDir, "db" ), new Options().createIfMissing( true ) );
            this.prepareSpawnRegion();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        // CHECKSTYLE:ON
    }

    /**
     * Loads an leveldb world given the path to the world's directory. This operation
     * performs synchronously and will at least load the entire spawn region before
     * completing.
     *
     * @param server      The GoMint Server which runs this
     * @param pathToWorld The path to the world's directory
     * @return The leveldb world adapter used to access the world
     * @throws Exception Thrown in case the world could not be loaded successfully
     */
    public static LevelDBWorldAdapter load( GoMintServer server, File pathToWorld ) throws Exception {
        return new LevelDBWorldAdapter( server, pathToWorld );
    }

    private byte[] getKey( int chunkX, int chunkZ, byte dataType ) {
        return new byte[]{
                (byte) ( chunkX & 0xFF ),
                (byte) ( ( chunkX >>> 8 ) & 0xFF ),
                (byte) ( ( chunkX >>> 16 ) & 0xFF ),
                (byte) ( ( chunkX >>> 24 ) & 0xFF ),
                (byte) ( chunkZ & 0xFF ),
                (byte) ( ( chunkZ >>> 8 ) & 0xFF ),
                (byte) ( ( chunkZ >>> 16 ) & 0xFF ),
                (byte) ( ( chunkZ >>> 24 ) & 0xFF ),
                dataType
        };
    }

    private byte[] getKeySubChunk( int chunkX, int chunkZ, byte dataType, byte subChunk ) {
        return new byte[]{
                (byte) ( chunkX & 0xFF ),
                (byte) ( ( chunkX >>> 8 ) & 0xFF ),
                (byte) ( ( chunkX >>> 16 ) & 0xFF ),
                (byte) ( ( chunkX >>> 24 ) & 0xFF ),
                (byte) ( chunkZ & 0xFF ),
                (byte) ( ( chunkZ >>> 8 ) & 0xFF ),
                (byte) ( ( chunkZ >>> 16 ) & 0xFF ),
                (byte) ( ( chunkZ >>> 24 ) & 0xFF ),
                dataType,
                subChunk
        };
    }

    private void loadLevelDat() throws Exception {
        File levelDat = new File( this.worldDir, "level.dat" );
        if ( !levelDat.exists() || !levelDat.isFile() ) {
            throw new IOException( "Missing level.dat" );
        }

        // Default the settings
        this.levelName = "";
        this.spawn = new Location( this, 0, 0, 0 );
        this.worldVersion = 0;

        // Temporary stuff needed for the generator
        final String[] flatWorldSettings = { "" };
        final int[] generator = { 0 };

        try ( FileInputStream stream = new FileInputStream( levelDat ) ) {
            // Skip some data. For example the amount of bytes of this NBT Tag
            stream.skip( 8 );

            NBTStream nbtStream = new NBTStream( stream, ByteOrder.LITTLE_ENDIAN );
            nbtStream.addListener( ( path, value ) -> {
                switch ( path ) {
                    case ".FlatWorldLayers":
                        flatWorldSettings[0] = (String) value;
                        break;
                    case ".Generator":
                        generator[0] = (int) value;
                        break;
                    case ".LevelName":
                        LevelDBWorldAdapter.this.levelName = (String) value;
                        break;
                    case ".SpawnX":
                        LevelDBWorldAdapter.this.spawn.setX( (int) value );
                        break;
                    case ".SpawnY":
                        LevelDBWorldAdapter.this.spawn.setY( (int) value );
                        break;
                    case ".SpawnZ":
                        LevelDBWorldAdapter.this.spawn.setZ( (int) value );
                        break;
                    case ".StorageVersion":
                        LevelDBWorldAdapter.this.worldVersion = (int) value;
                        break;
                    case ".Difficulty":
                        LevelDBWorldAdapter.this.difficulty = Difficulty.valueOf( (int) value );
                        break;
                    default:
                        LOGGER.debug( "Found unknown path: " + path + " with value " + value );
                        break;
                }
            } );
            nbtStream.parse();
        }

        Generators generators = Generators.valueOf( generator[0] );
        if ( generators != null ) {
            switch ( generators ) {
                case FLAT:
                    ConfigurableLayerGenerator chunkGenerator = new ConfigurableLayerGenerator( this );

                    // Check for flat configuration
                    JSONParser parser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject) parser.parse( flatWorldSettings[0] );
                        if ( jsonObject.containsKey( "block_layers" ) ) {
                            JSONArray blockLayers = (JSONArray) jsonObject.get( "block_layers" );
                            for ( Object layer : blockLayers ) {
                                JSONObject layerConfig = (JSONObject) layer;
                                int count = 1;
                                if ( layerConfig.containsKey( "count" ) ) {
                                    count = ((Long) layerConfig.get( "count" )).intValue();
                                }

                                int blockId = ((Long) layerConfig.get( "block_id" )).intValue();
                                byte blockData = ((Long) layerConfig.get( "block_data" )).byteValue();

                                for ( int i = 0; i < count; i++ ) {
                                    chunkGenerator.addLayer( blockId, blockData );
                                }
                            }
                        }
                    } catch ( ParseException e ) {
                        // Remember its from bottom to top
                        chunkGenerator.addLayer( 7, (byte) 0 );
                        chunkGenerator.addLayer( 3, (byte) 0 );
                        chunkGenerator.addLayer( 3, (byte) 0 );
                        chunkGenerator.addLayer( 2, (byte) 0 );
                    }

                    this.chunkGenerator = chunkGenerator;
                    break;
            }
        }
    }

    @Override
    protected ChunkAdapter loadChunk( int x, int z, boolean generate ) {
        ChunkAdapter chunk = this.chunkCache.getChunk( x, z );
        if ( chunk == null ) {
            // Get version bit
            byte[] version = this.db.get( this.getKey( x, z, (byte) 0x76 ) );
            if ( version == null ) {
                if ( generate ) {
                    return this.generate( x, z );
                } else {
                    return null;
                }
            }

            LevelDBChunkAdapter loadingChunk = new LevelDBChunkAdapter( this, x, z );

            for ( int sectionY = 0; sectionY < 16; sectionY++ ) {
                try {
                    byte[] chunkData = this.db.get( this.getKeySubChunk( x, z, (byte) 0x2f, (byte) sectionY ) );
                    if ( chunkData != null ) {
                        loadingChunk.loadSection( sectionY, chunkData );
                    } else {
                        break;
                    }
                } catch ( DbImpl.BackgroundProcessingException ignored ) {
                    break;
                }
            }

            try {
                byte[] tileEntityData = this.db.get( this.getKey( x, z, (byte) 0x31 ) );
                if ( tileEntityData != null ) {
                    loadingChunk.loadTileEntities( tileEntityData );
                }
            } catch ( DbImpl.BackgroundProcessingException ignored ) {
                // TODO: Implement proper error handling here
            }

            try {
                byte[] entityData = this.db.get( this.getKey( x, z, (byte) 0x32 ) );
                if ( entityData != null ) {
                    loadingChunk.loadEntities( entityData );
                }
            } catch ( DbImpl.BackgroundProcessingException ignored ) {
                // TODO: Implement proper error handling here
            }

            byte[] extraData = null;
            try {
                extraData = this.db.get( this.getKey( x, z, (byte) 0x34 ) );
            } catch ( DbImpl.BackgroundProcessingException ignored ) {
                // TODO: Implement proper error handling here
            }

            if ( extraData != null ) {
                DumpUtil.dumpByteArray( extraData );
            }

            this.chunkCache.putChunk( loadingChunk );

            // Run post processors
            loadingChunk.runPostProcessors();

            return loadingChunk;
        }

        return chunk;
    }

    @Override
    protected void saveChunk( ChunkAdapter chunk ) {
        /*if ( chunk == null ) {
            return;
        }

        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        WriteBatch writeBatch = this.db.createWriteBatch();
        writeBatch.put( this.getKey( chunkX, chunkZ, (byte) 0x30 ), ( (LevelDBChunkAdapter) chunk ).getSaveData() );
        this.db.write( writeBatch );*/
    }

}
