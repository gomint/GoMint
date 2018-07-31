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
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.io.Files;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.MaterialMagicNumbers;
import io.gomint.server.plugin.PluginClassloader;
import io.gomint.server.util.Pair;
import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.ChunkCache;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.WorldCreateException;
import io.gomint.server.world.WorldLoadException;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTStream;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Chunk;
import io.gomint.world.Difficulty;
import io.gomint.world.block.BlockType;
import io.gomint.world.generator.ChunkGenerator;
import io.gomint.world.generator.GeneratorContext;
import io.gomint.world.generator.integrated.LayeredGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
        .removalListener( (RemovalListener<Pair<Integer, Integer>, RegionFile>) removalNotification -> {
            if ( removalNotification.wasEvicted() ) {
                removalNotification.getValue().closeFDs();
            }
        } )
        .build( new CacheLoader<Pair<Integer, Integer>, RegionFile>() {
            @Override
            public RegionFile load( Pair<Integer, Integer> pair ) throws Exception {
                AnvilWorldAdapter.this.logger.debug( "Opening new region file {}, {}", pair.getFirst(), pair.getSecond() );
                return new RegionFile( AnvilWorldAdapter.this, new File( AnvilWorldAdapter.this.worldDir,
                    String.format( REGION_FILE_FORMAT, File.separator, pair.getFirst(), pair.getSecond() ) ) );
            }
        } );

    // Overrides
    @Getter
    private boolean overrideConverter;

    // Generator things
    private String generatorName;
    private int generatorVersion;
    private String generatorOptions;
    private long generatorSeed;

    //
    private Lock currentlyLoadingLock = new ReentrantLock( true );
    private Map<Long, Lock> currentlyLoading = new HashMap<>();
    private Map<Long, Condition> currentlyLoadingConditions = new HashMap<>();

    private AnvilWorldAdapter( final GoMintServer server, final String name, final Class<? extends ChunkGenerator> generator ) throws WorldCreateException {
        super( server, new File( name ) );
        this.chunkCache = new ChunkCache( this );

        // Build up generator
        GeneratorContext context = new GeneratorContext();
        this.constructGenerator( generator, context );

        // Generate a spawnpoint
        BlockPosition spawnPoint = this.chunkGenerator.getSpawnPoint();
        this.spawn = new Location( this, spawnPoint.getX(), spawnPoint.getY(), spawnPoint.getZ() );

        // We need a level.dat
        try {
            this.saveLevelDat();
        } catch ( IOException e ) {
            throw new WorldCreateException( "level.dat for world '" + name + "' could not be saved", e );
        }

        // Prepare spawn
        this.prepareSpawnRegion();
    }

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
        this.prepareChunkGenerator();
        this.prepareSpawnRegion();
        // CHECKSTYLE:ON

        // Fix spawn point
        if ( this.getBlockAt( this.spawn.toBlockPosition() ).getType() != BlockType.AIR ) {
            this.adjustSpawn();
        }
    }

    private void prepareChunkGenerator() {
        switch ( this.generatorName ) {
            case "flat":
                // The first integer is a version number in options
                int optionVersion = 0;
                String[] optionSplit = this.generatorOptions.split( ";" );
                if ( optionSplit.length > 1 ) {
                    try {
                        optionVersion = Integer.parseInt( optionSplit[0] );
                    } catch ( NumberFormatException e ) {
                        // Ignore (vanilla falls back to 0 but its already 0)
                    }
                }

                // Check if version is valid (0-3)
                if ( optionVersion >= 0 && optionVersion <= 3 ) {
                    List<Block> layers = new ArrayList<>();

                    int layerInfoIndex = optionSplit.length == 1 ? 0 : 1;
                    String layerInfo = optionSplit[layerInfoIndex];
                    for ( String layer : layerInfo.split( "," ) ) {
                        String[] temp;
                        if ( optionVersion >= 3 ) {
                            // This format uses "*" as delimiter and uses new ids
                            temp = layer.split( "\\*", 2 );

                            int amountOfLayers = 1;
                            int blockId;
                            if ( temp.length == 1 ) {
                                blockId = MaterialMagicNumbers.valueOfWithId( temp[0] );
                            } else {
                                amountOfLayers = Integer.parseInt( temp[0] );
                                blockId = MaterialMagicNumbers.valueOfWithId( temp[1] );
                            }

                            Block block = this.server.getBlocks().get( blockId, (byte) 0, (byte) 0, (byte) 0, null, null, 0 );
                            for ( int i = 0; i < amountOfLayers; i++ ) {
                                layers.add( block );
                            }
                        } else {
                            if ( layer.length() > 0 ) {
                                // This format uses "x" as delimiter and uses old ids
                                temp = layer.split( "x", 2 );

                                int amountOfLayers = 1;
                                int blockId;
                                if ( temp.length == 1 ) {
                                    blockId = Integer.parseInt( temp[0] );
                                } else {
                                    amountOfLayers = Integer.parseInt( temp[0] );
                                    blockId = Integer.parseInt( temp[1] );
                                }

                                Block block = this.server.getBlocks().get( blockId, (byte) 0, (byte) 0, (byte) 0, null, null, 0 );
                                for ( int i = 0; i < amountOfLayers; i++ ) {
                                    layers.add( block );
                                }
                            }
                        }

                        GeneratorContext generatorContext = new GeneratorContext();
                        generatorContext.put( "amountOfLayers", layers.size() );

                        int i = 0;
                        for ( Block block : layers ) {
                            generatorContext.put( "layer." + ( i++ ), block );
                        }

                        this.chunkGenerator = new LayeredGenerator( this, generatorContext );
                    }
                } else {
                    this.chunkGenerator = new LayeredGenerator( this, new GeneratorContext() );
                }

                break;

            case "default": // Currently not implemented ones
            case "largeBiomes":
            case "amplified":
            case "debug_all_block_states":
            case "default_1_1":
            case "customized":
                break;

            default:
                JSONParser parser = new JSONParser();
                GeneratorContext context = new GeneratorContext();

                try {
                    JSONObject object = (JSONObject) parser.parse( this.generatorOptions );
                    object.forEach( ( o, o2 ) -> context.put( (String) o, o2 ) );
                } catch ( ParseException e ) {
                    // Ignore this
                }

                try {
                    Class<? extends ChunkGenerator> chunkGeneratorClass = (Class<? extends ChunkGenerator>) PluginClassloader.find( this.generatorName );
                    this.constructGenerator( chunkGeneratorClass, context );
                } catch ( ClassNotFoundException e ) {
                    this.logger.warn( "Could not find original chunk generator: {}", this.generatorName );
                } catch ( WorldCreateException e ) {
                    this.logger.error( "Found chunk generator for {} but it did throw an error on instancing", this.generatorName, e );
                }
        }
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
     * Create a new anvil based world. This will not override old worlds. It will fail with a WorldCreateException when
     * a folder has been found with the same name (regardless of the content of that folder)
     *
     * @param server    which wants to create this world
     * @param name      of the new world
     * @param generator which is used to generate this worlds chunks and spawn point
     * @return new world
     * @throws WorldCreateException when there already is a world or a error during creating occured
     */
    public static AnvilWorldAdapter create( GoMintServer server, String name, Class<? extends ChunkGenerator> generator ) throws WorldCreateException {
        File worldFolder = new File( name );
        if ( worldFolder.exists() ) {
            throw new WorldCreateException( "Folder with name '" + name + "' already exists" );
        }

        if ( !worldFolder.mkdir() ) {
            throw new WorldCreateException( "World '" + name + "' could not be created. Folder could not be created" );
        }

        File regionFolder = new File( worldFolder, "region" );
        if ( !regionFolder.mkdir() ) {
            throw new WorldCreateException( "World '" + name + "' could not be created. Folder could not be created" );
        }

        return new AnvilWorldAdapter( server, name, generator );
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
        NBTTagCompound dataCompound = new NBTTagCompound( "Data" );
        compound.addValue( "Data", dataCompound );

        // Add version number
        dataCompound.addValue( "version", 19133 );

        // Spawn
        dataCompound.addValue( "SpawnX", (int) this.spawn.getX() );
        dataCompound.addValue( "SpawnY", (int) this.spawn.getY() );
        dataCompound.addValue( "SpawnZ", (int) this.spawn.getZ() );

        // Save generator
        this.saveGenerator( dataCompound );

        // Save level.dat
        compound.writeTo( levelDat, true, ByteOrder.BIG_ENDIAN );
    }

    private void saveGenerator( NBTTagCompound dataCompound ) {
        // We have "normal" vanilla generators which need to be saved different
        if ( this.chunkGenerator instanceof LayeredGenerator ) {
            dataCompound.addValue( "generatorName", "flat" );

            String currentBlock = "";
            int currentAmount = 1;

            StringBuilder optionBuilder = new StringBuilder( "3" );    // We build option version 3 only

            LayeredGenerator layeredGenerator = (LayeredGenerator) this.chunkGenerator;
            for ( io.gomint.world.block.Block block : layeredGenerator.getLayers() ) {
                Block implBlock = (Block) block;
                String id = MaterialMagicNumbers.newIdFromValue( implBlock.getBlockId() );
                if ( id.equals( currentBlock ) ) {
                    currentAmount++;
                } else {
                    // Check if we need to save the old block
                    if ( !currentBlock.isEmpty() ) {
                        if ( currentAmount == 1 ) {
                            optionBuilder.append( ";" ).append( currentBlock );
                        } else {
                            optionBuilder.append( ";" ).append( currentAmount ).append( "*" ).append( currentBlock );
                        }
                    }

                    currentBlock = id;
                    currentAmount = 1;
                }
            }

            // Check if we need to save the old block
            if ( !currentBlock.isEmpty() ) {
                if ( currentAmount == 1 ) {
                    optionBuilder.append( ";" ).append( currentBlock );
                } else {
                    optionBuilder.append( ";" ).append( currentAmount ).append( "*" ).append( currentBlock );
                }
            }

            dataCompound.addValue( "generatorOptions", optionBuilder.toString() );
        } else {
            dataCompound.addValue( "generatorName", this.chunkGenerator.getClass().getName() );

            // Serialize context
            String json = new JSONObject( this.chunkGenerator.getContext().asMap() ).toJSONString();
            dataCompound.addValue( "generatorOptions", json );
        }
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
                        case ".Data.generatorName":
                            AnvilWorldAdapter.this.generatorName = (String) value;
                            break;
                        case ".Data.generatorOptions":
                            AnvilWorldAdapter.this.generatorOptions = (String) value;
                            break;
                        case ".Data.generatorVersion":
                            AnvilWorldAdapter.this.generatorVersion = (int) value;
                            break;
                        case ".Data.RandomSeed":
                            AnvilWorldAdapter.this.generatorSeed = (long) value;
                            break;
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
                            logger.debug( "Found level dat NBT Tag: {} -> {}", path, value );
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
    public ChunkAdapter loadChunk( int x, int z, boolean generate ) {
        ChunkAdapter chunk = this.chunkCache.getChunk( x, z );
        if ( chunk == null ) {
            // this.logger.debug( "Starting to load chunk {} {}", x, z );

            try {
                int regionX = CoordinateUtils.fromChunkToRegion( x );
                int regionZ = CoordinateUtils.fromChunkToRegion( z );

                RegionFile regionFile = this.openFileCache.get( new Pair<>( regionX, regionZ ) );

                try {
                    chunk = regionFile.loadChunk( x, z );

                    // Register entities
                    if ( chunk != null ) {
                        this.registerEntitiesFromChunk( chunk );
                    }
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
                logger.error( "Could not load chunk {}, {}", x, z, e );

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
            RegionFile regionFile = this.openFileCache.get( new Pair<>( regionX, regionZ ) );
            regionFile.saveChunk( (AnvilChunkAdapter) chunk );
        } catch ( IOException | ExecutionException e ) {
            this.logger.error( "Failed to save chunk to region file", e );
        }
    }

    @Override
    protected void closeFDs() {
        this.openFileCache.invalidateAll();
    }

    @Override
    public boolean persistPlayer( EntityPlayer player ) {
        // We need the playerdata folder
        File playerDataFolder = new File( this.worldDir, "playerdata" );
        if ( !playerDataFolder.exists() ) {
            playerDataFolder.mkdirs();
        }

        // We need to store basically everything
        NBTTagCompound persistCompound = player.persistToNBT();

        return false;
    }

    @Override
    public Chunk generateEmptyChunk( int x, int z ) {
        return new AnvilChunkAdapter( this, x, z, System.currentTimeMillis() );
    }

}
