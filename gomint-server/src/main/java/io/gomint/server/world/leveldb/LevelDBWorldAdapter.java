/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.leveldb;

import com.google.common.io.Files;
import io.gomint.leveldb.DB;
import io.gomint.leveldb.NativeLoader;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.plugin.PluginClassloader;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.ChunkCache;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.WorldCreateException;
import io.gomint.server.world.WorldLoadException;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTStream;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Chunk;
import io.gomint.world.Difficulty;
import io.gomint.world.generator.ChunkGenerator;
import io.gomint.world.generator.GeneratorContext;
import io.gomint.world.generator.integrated.LayeredGenerator;
import io.gomint.world.generator.integrated.NormalGenerator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LevelDBWorldAdapter extends WorldAdapter {

    static {
        if ( !NativeLoader.load() ) {
            System.out.println( "Could not load native leveldb. Please be sure you have a supported OS installed" );
            System.exit( -1 );
        }
    }

    private DB db;
    private int worldVersion;

    // Generator things
    private int generatorType;
    private int generatorVersion;
    private String generatorOptions;
    private long generatorSeed;

    // Custom generators
    private Class<? extends ChunkGenerator> generatorClass;
    private GeneratorContext generatorContext;

    private LevelDBWorldAdapter( final GoMintServer server, final String name, final Class<? extends ChunkGenerator> generator ) throws WorldCreateException {
        super( server, new File( name ) );
        this.chunkCache = new ChunkCache( this );

        // Build up generator
        GeneratorContext context = new GeneratorContext();
        this.constructGenerator( generator, context );

        // Generate a spawnpoint
        BlockPosition spawnPoint = this.chunkGenerator.getSpawnPoint();
        this.spawn = new Location( this, spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ() );

        // Take over level name
        this.levelName = name;

        // We need a level.dat
        try {
            this.saveLevelDat();
        } catch ( IOException e ) {
            throw new WorldCreateException( "level.dat for world '" + name + "' could not be saved", e );
        }

        try {
            this.open();
        } catch ( WorldLoadException e ) {
            throw new WorldCreateException( "Could not open/load world", e );
        }
    }

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
        if ( this.worldVersion < 8 ) {
            throw new WorldLoadException( "Version of the world is too old. Please update your MC:PE and import this world. After that you can use the exported version again." );
        }

        this.open();
    }

    private void open() throws WorldLoadException {
        try {
            this.db = new DB( new File( this.worldDir, "db" ) );
            this.db.open();
        } catch ( Exception e ) {
            throw new WorldLoadException( "Could not open leveldb connection: " + e.getMessage() );
        }

        this.prepareSpawnRegion();

        // Adjust spawn location if needed
        this.adjustSpawn();
    }

    /**
     * Create a new leveldb based world. This will not override old worlds. It will fail with a WorldCreateException when
     * a folder has been found with the same name (regardless of the content of that folder)
     *
     * @param server    which wants to create this world
     * @param name      of the new world
     * @param generator which is used to generate this worlds chunks and spawn point
     * @return new world
     * @throws WorldCreateException when there already is a world or a error during creating occured
     */
    public static LevelDBWorldAdapter create( GoMintServer server, String name, Class<? extends ChunkGenerator> generator ) throws WorldCreateException {
        File worldFolder = new File( name );
        if ( worldFolder.exists() ) {
            throw new WorldCreateException( "Folder with name '" + name + "' already exists" );
        }

        if ( !worldFolder.mkdir() ) {
            throw new WorldCreateException( "World '" + name + "' could not be created. Folder could not be created" );
        }

        File regionFolder = new File( worldFolder, "db" );
        if ( !regionFolder.mkdir() ) {
            throw new WorldCreateException( "World '" + name + "' could not be created. Folder could not be created" );
        }

        return new LevelDBWorldAdapter( server, name, generator );
    }

    private void saveLevelDat() throws IOException {
        File levelDat = new File( this.worldDir, "level.dat" );
        if ( levelDat.exists() ) {
            // Backup old level.dat
            Files.copy( levelDat, new File( this.worldDir, "level.dat.bak" ) );

            // Delete the old one
            levelDat.delete();
        }

        //
        NBTTagCompound compound = new NBTTagCompound( "" );

        // Add version number
        compound.addValue( "StorageVersion", 8 );

        // Spawn
        compound.addValue( "SpawnX", (int) this.spawn.getX() );
        compound.addValue( "SpawnY", (int) this.spawn.getY() );
        compound.addValue( "SpawnZ", (int) this.spawn.getZ() );
        compound.addValue( "Difficulty", this.difficulty.getDifficultyDegree() );

        // Level name
        compound.addValue( "LevelName", this.levelName );

        // Save generator
        this.saveGenerator( compound );

        // Save level.dat
        try ( FileOutputStream stream = new FileOutputStream( levelDat ) ) {
            stream.write( new byte[8] );
            compound.writeTo( stream, false, ByteOrder.LITTLE_ENDIAN );
        }
    }

    private void saveGenerator( NBTTagCompound compound ) {
        if ( this.chunkGenerator instanceof NormalGenerator ) {
            compound.addValue( "Generator", 1 );
            compound.addValue( "RandomSeed", (long) this.chunkGenerator.getContext().get( "seed" ) );
        } else if ( this.chunkGenerator instanceof LayeredGenerator ) {
            compound.addValue( "Generator", 2 );
        } else {
            compound.addValue( "Generator", -1 );
            compound.addValue( "GeneratorClass", this.chunkGenerator.getClass().getName() );
            compound.addValue( "GeneratorContext", this.chunkGenerator.getContext().toString() );
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

    @Override
    public void setSpawnLocation( Location location ) {
        super.setSpawnLocation( location );
        this.sneakySaveLevelDat();
    }

    @Override
    public void setDifficulty( Difficulty difficulty ) {
        super.setDifficulty( difficulty );
        this.sneakySaveLevelDat();
    }

    @Override
    protected void prepareGenerator() {
        if ( this.generatorType == -1 ) { // Custom generators
            try {
                constructGenerator( this.generatorClass, this.generatorContext );
            } catch ( WorldCreateException e ) {
                this.getLogger().error( "Could not reconstruct generator class", e );
            }
        } else {
            Generators generators = Generators.valueOf( this.generatorType );
            if ( generators != null ) {
                switch ( generators ) {
                    case NORMAL:
                        GeneratorContext context = new GeneratorContext();
                        context.put( "seed", this.generatorSeed );
                        this.chunkGenerator = new NormalGenerator( this, context );
                        break;

                    case FLAT:
                        context = new GeneratorContext();

                        // Check for flat configuration
                        JSONParser parser = new JSONParser();
                        try {
                            List<Block> blocks = new ArrayList<>();

                            JSONObject jsonObject = (JSONObject) parser.parse( this.generatorOptions );
                            if ( jsonObject != null && jsonObject.containsKey( "block_layers" ) ) {
                                JSONArray blockLayers = (JSONArray) jsonObject.get( "block_layers" );
                                for ( Object layer : blockLayers ) {
                                    JSONObject layerConfig = (JSONObject) layer;
                                    int count = 1;
                                    if ( layerConfig.containsKey( "count" ) ) {
                                        count = ( (Long) layerConfig.get( "count" ) ).intValue();
                                    }

                                    // TODO: look at new format of the flat layers
                                    int blockId = ( (Long) layerConfig.get( "block_id" ) ).intValue();
                                    byte blockData = ( (Long) layerConfig.get( "block_data" ) ).byteValue();

                                /*Block block = this.server.getBlocks().get( blockId, blockData, (byte) 0, (byte) 0, null, null, 0 );
                                for ( int i = 0; i < count; i++ ) {
                                    blocks.add( block );
                                }*/
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
    }

    public ByteBuf getKey( int chunkX, int chunkZ, byte dataType ) {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer( 9 );
        return buf.writeIntLE( chunkX ).writeIntLE( chunkZ ).writeByte( dataType );
    }

    public ByteBuf getKeySubChunk( int chunkX, int chunkZ, byte dataType, byte subChunk ) {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer( 10 );
        return buf.writeIntLE( chunkX ).writeIntLE( chunkZ ).writeByte( dataType ).writeByte( subChunk );
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
                    case ".GeneratorClass":
                        LevelDBWorldAdapter.this.generatorClass = (Class<? extends ChunkGenerator>) PluginClassloader.find( (String) value );
                        break;
                    case ".GeneratorContext":
                        LevelDBWorldAdapter.this.generatorContext = new GeneratorContext();

                        String jsonContext = (String) value;
                        JSONParser parser = new JSONParser();
                        JSONObject jsonObject = (JSONObject) parser.parse( jsonContext );
                        jsonObject.forEach( ( o, o2 ) -> LevelDBWorldAdapter.this.generatorContext.put( (String) o, o2 ) );

                        break;
                    case ".RandomSeed":
                        LevelDBWorldAdapter.this.generatorSeed = (long) value;
                        break;
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
            DB.Snapshot snapshot = this.db.getSnapshot();

            // Get version bit
            ByteBuf versionKey = this.getKey( x, z, (byte) 0x76 );
            byte[] version = this.db.get( snapshot, versionKey );
            versionKey.release();

            if ( version == null ) {
                if ( generate ) {
                    return this.generate( x, z );
                } else {
                    return null;
                }
            }

            // Get version bit
            ByteBuf finalizedKey = this.getKey( x, z, (byte) 0x36 );
            byte[] finalized = this.db.get( snapshot, finalizedKey );
            finalizedKey.release();

            if ( finalized == null ) {
                if ( generate ) {
                    return this.generate( x, z );
                } else {
                    return null;
                }
            }

            byte v = version[0];
            boolean populated = finalized[0] == 2;

            LevelDBChunkAdapter loadingChunk = new LevelDBChunkAdapter( this, x, z, v, populated );

            for ( int sectionY = 0; sectionY < 16; sectionY++ ) {
                try {
                    ByteBuf chunkKey = this.getKeySubChunk( x, z, (byte) 0x2f, (byte) sectionY );
                    byte[] chunkData = this.db.get( snapshot, chunkKey );
                    chunkKey.release();

                    if ( chunkData != null ) {
                        loadingChunk.loadSection( sectionY, chunkData );
                    } else {
                        break;
                    }
                } catch ( Exception e ) {
                    this.logger.warn( "Could not load subchunk", e );
                }
            }

            try {
                ByteBuf tileEntityKey = this.getKey( x, z, (byte) 0x31 );
                byte[] tileEntityData = this.db.get( snapshot, tileEntityKey );
                tileEntityKey.release();

                if ( tileEntityData != null ) {
                    loadingChunk.loadTileEntities( tileEntityData );
                }
            } catch ( Exception e ) {
                this.logger.warn( "Could not load tiles", e );
            }

            try {
                ByteBuf entityKey = this.getKey( x, z, (byte) 0x32 );
                byte[] entityData = this.db.get( snapshot, entityKey );
                entityKey.release();

                if ( entityData != null ) {
                    loadingChunk.loadEntities( entityData );
                }
            } catch ( Exception e ) {
                this.logger.warn( "Could not load entities", e );
            }

            /*byte[] extraData = null;
            try {
                extraData = this.db.get( snapshot, this.getKey( x, z, (byte) 0x34 ) );
            } catch ( Exception ignored ) {
                ignored.printStackTrace();
                // TODO: Implement proper error handling here
            }

            if ( extraData != null ) {
                DumpUtil.dumpByteArray( extraData );
            }*/

            // Register entities
            this.registerEntitiesFromChunk( loadingChunk );

            // Do some work on the chunk if needed (like population)
            if ( !populated ) {
                this.addPopulateTask( loadingChunk );
            }

            this.chunkCache.putChunk( loadingChunk );
            return loadingChunk;
        }

        return chunk;
    }

    @Override
    protected void saveChunk( ChunkAdapter chunk ) {
        if ( chunk == null ) {
            return;
        }

        LevelDBChunkAdapter adapter = (LevelDBChunkAdapter) chunk;
        adapter.save( this.db );
    }

    @Override
    protected void closeFDs() {
        try {
            this.db.close();
        } catch ( Exception e ) {
            this.logger.error( "Could not close leveldb", e );
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

    protected final void sneakySaveLevelDat() {
        try {
            this.saveLevelDat();
        } catch ( IOException cause ) {
            this.logger.error( "level.dat for world '{}' could not be saved: ", this.levelName, cause );
        }
    }

}
