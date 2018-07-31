/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.leveldb;

import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.util.DumpUtil;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.ChunkCache;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.WorldLoadException;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTStream;
import io.gomint.world.Chunk;
import io.gomint.world.Difficulty;
import io.gomint.world.generator.GeneratorContext;
import io.gomint.world.generator.integrated.LayeredGenerator;
import lombok.EqualsAndHashCode;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.DbImpl;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode( callSuper = true )
public class LevelDBWorldAdapter extends WorldAdapter {

    private DB db;
    private int worldVersion;

    // Generator things
    private int generatorType;
    private int generatorVersion;
    private String generatorOptions;
    private long generatorSeed;

    /**
     * Construct and init a new levedb based World
     *
     * @param server   which has requested to load this world
     * @param worldDir the folder where the world should be in
     * @throws WorldLoadException Thrown in case the world could not be loaded successfully
     */
    LevelDBWorldAdapter( GoMintServer server, File worldDir ) throws WorldLoadException {
        super( server, worldDir );
        this.chunkCache = new ChunkCache( this );

        this.loadLevelDat();

        // We only support storage version 3 and up (MC:PE >= 1.0)
        if ( this.worldVersion < 3 ) {
            throw new WorldLoadException( "Version of the world is too old. Please update your MC:PE and import this world. After that you can use the exported version again." );
        }

        try {
            this.db = Iq80DBFactory.factory.open( new File( this.worldDir, "db" ), new Options().createIfMissing( true ) );
        } catch ( IOException e ) {
            throw new WorldLoadException( "Could not open leveldb connection: " + e.getMessage() );
        }

        this.prepareGenerator();
        this.prepareSpawnRegion();

        // Adjust spawn location if needed
        this.adjustSpawn();
    }

    private void prepareGenerator() {
        Generators generators = Generators.valueOf( this.generatorType );
        if ( generators != null ) {
            switch ( generators ) {
                case FLAT:
                    GeneratorContext context = new GeneratorContext();

                    // Check for flat configuration
                    JSONParser parser = new JSONParser();
                    try {
                        List<Block> blocks = new ArrayList<>();

                        JSONObject jsonObject = (JSONObject) parser.parse( this.generatorOptions );
                        if ( jsonObject.containsKey( "block_layers" ) ) {
                            JSONArray blockLayers = (JSONArray) jsonObject.get( "block_layers" );
                            for ( Object layer : blockLayers ) {
                                JSONObject layerConfig = (JSONObject) layer;
                                int count = 1;
                                if ( layerConfig.containsKey( "count" ) ) {
                                    count = ( (Long) layerConfig.get( "count" ) ).intValue();
                                }

                                int blockId = ( (Long) layerConfig.get( "block_id" ) ).intValue();
                                byte blockData = ( (Long) layerConfig.get( "block_data" ) ).byteValue();

                                Block block = this.server.getBlocks().get( blockId, blockData, (byte) 0, (byte) 0, null, null, 0 );
                                for ( int i = 0; i < count; i++ ) {
                                    blocks.add( block );
                                }
                            }
                        }

                        context.put( "amountOfLayers", blocks.size() );
                        int i = 0;
                        for ( Block block : blocks ) {
                            context.put( "layer." + ( i++ ), block );
                        }
                    } catch ( ParseException e ) {
                        // Ignore this, if this happens the context is empty and the generator will fallback to default
                        // behaviour
                    }

                    this.chunkGenerator = new LayeredGenerator( this, context );
                    break;
            }
        }
    }

    /**
     * Loads an leveldb world given the path to the world's directory. This operation
     * performs synchronously and will at least load the entire spawn region before
     * completing.
     *
     * @param server      The GoMint Server which runs this
     * @param pathToWorld The path to the world's directory
     * @return The leveldb world adapter used to access the world
     * @throws WorldLoadException Thrown in case the world could not be loaded successfully
     */
    public static LevelDBWorldAdapter load( GoMintServer server, File pathToWorld ) throws WorldLoadException {
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

    private void loadLevelDat() throws WorldLoadException {
        File levelDat = new File( this.worldDir, "level.dat" );
        if ( !levelDat.exists() || !levelDat.isFile() ) {
            throw new WorldLoadException( "Missing level.dat" );
        }

        // Default the settings
        this.levelName = "";
        this.spawn = new Location( this, 0, 0, 0 );
        this.worldVersion = 0;

        try ( FileInputStream stream = new FileInputStream( levelDat ) ) {
            // Skip some data. For example the amount of bytes of this NBT Tag
            stream.skip( 8 );

            NBTStream nbtStream = new NBTStream( stream, ByteOrder.LITTLE_ENDIAN );
            nbtStream.addListener( ( path, value ) -> {
                switch ( path ) {
                    case ".FlatWorldLayers":
                        LevelDBWorldAdapter.this.generatorOptions = (String) value;
                        break;
                    case ".Generator":
                        LevelDBWorldAdapter.this.generatorType = (int) value;
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
                        LevelDBWorldAdapter.this.logger.info( "Found unknown path: " + path + " with value " + value );
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
        } catch ( IOException e ) {
            throw new WorldLoadException( "Failed to load leveldb world: " + e.getMessage() );
        }
    }

    @Override
    public ChunkAdapter loadChunk( int x, int z, boolean generate ) {
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

            byte v = version[0];
            LevelDBChunkAdapter loadingChunk = new LevelDBChunkAdapter( this, x, z, v );

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
                ignored.printStackTrace();
                // TODO: Implement proper error handling here
            }

            try {
                byte[] entityData = this.db.get( this.getKey( x, z, (byte) 0x32 ) );
                if ( entityData != null ) {
                    loadingChunk.loadEntities( entityData );
                }
            } catch ( DbImpl.BackgroundProcessingException ignored ) {
                ignored.printStackTrace();
                // TODO: Implement proper error handling here
            }

            byte[] extraData = null;
            try {
                extraData = this.db.get( this.getKey( x, z, (byte) 0x34 ) );
            } catch ( DbImpl.BackgroundProcessingException ignored ) {
                ignored.printStackTrace();
                // TODO: Implement proper error handling here
            }

            if ( extraData != null ) {
                DumpUtil.dumpByteArray( extraData );
            }

            // Register entities
            this.registerEntitiesFromChunk( loadingChunk );
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

    @Override
    protected void closeFDs() {
        try {
            this.db.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean persistPlayer( EntityPlayer player ) {
        return false;
    }

    @Override
    public Chunk generateEmptyChunk( int x, int z ) {
        return new LevelDBChunkAdapter( this, x, z );
    }

}
