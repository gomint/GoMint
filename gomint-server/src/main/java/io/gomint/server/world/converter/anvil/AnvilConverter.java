package io.gomint.server.world.converter.anvil;

import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.server.assets.AssetsLibrary;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.tileentity.BedTileEntity;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.NibbleArray;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.converter.BaseConverter;
import io.gomint.server.world.converter.anvil.entity.EntityConverters;
import io.gomint.server.world.converter.anvil.entity.v1_8.Entities;
import io.gomint.server.world.converter.anvil.tileentity.TileEntityConverters;
import io.gomint.server.world.converter.anvil.tileentity.v1_8.TileEntities;
import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockType;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AnvilConverter extends BaseConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger( AnvilConverter.class );

    private BlockConverter converter;
    private PEBlockConverter peConverter;

    private TileEntityConverters tileEntityConverter;
    private EntityConverters entityConverter;

    private boolean nukkitPMMPConverted;

    private Items items;
    private Object2IntMap<String> itemConverter;

    private final Long2ObjectMap<ByteSet> subChunksProcessed = new Long2ObjectOpenHashMap<>();

    /**
     * Build up a new converter
     *
     * @param assets      from which we get the converter data
     * @param context     application context which started this converter
     * @param worldFolder where the world is located
     */
    public AnvilConverter( AssetsLibrary assets, ApplicationContext context, File worldFolder ) {
        super( worldFolder );

        assets.ensureConvertData();

        File backupFolder = new File( worldFolder, "backup" );
        if ( !backupFolder.exists() ) {
            backupFolder.mkdir();
        }

        File alreadyConverted = new File( worldFolder, "ALREADY_CONVERTED" );
        this.nukkitPMMPConverted = alreadyConverted.exists();

        List<File> foldersToBeRemoved = new ArrayList<>();
        String parentFolder = worldFolder.toPath().toString();
        try ( Stream<Path> stream = Files.walk( worldFolder.toPath() ) ) {
            stream.forEach( path -> {
                String moveFile = path.toString().replace( parentFolder, "" );
                if ( moveFile.length() <= 1
                    || moveFile.substring( 1 ).startsWith( "backup" )
                    || moveFile.substring( 1 ).startsWith( "db" ) ) {
                    return;
                }

                File curFile = path.toFile();
                if ( curFile.isDirectory() ) {
                    File newFolderBackup = new File( backupFolder, moveFile );
                    newFolderBackup.mkdir();

                    foldersToBeRemoved.add( curFile );
                } else {
                    try {
                        Files.move( path, new File( backupFolder, moveFile ).toPath(), StandardCopyOption.ATOMIC_MOVE );
                    } catch ( IOException e ) {
                        LOGGER.error( "Could not move data into backup", e );
                    }
                }
            } );
        } catch ( IOException e ) {
            LOGGER.error( "Could not move data into backup", e );
        }

        for ( File file : foldersToBeRemoved ) {
            file.delete();
        }

        // Setup block converter
        this.converter = new BlockConverter( assets.getConverterData() );
        this.peConverter = new PEBlockConverter( assets.getPeConverter() );

        // Setup item converter
        Object2IntMap<String> itemConverter = new Object2IntOpenHashMap<>();
        for ( NBTTagCompound compound : assets.getConverterItemsData() ) {
            itemConverter.put( compound.getString( "s", "minecraft:air" ), compound.getInteger( "i", 0 ) );
        }

        // Setup entity id converter
        Object2IntMap<String> entityConverter = new Object2IntOpenHashMap<>();
        for ( NBTTagCompound compound : assets.getJeTopeEntities() ) {
            entityConverter.put( compound.getString( "s", "minecraft:chicken" ), compound.getInteger( "t", 10 ) );
        }

        this.items = context.getBean( Items.class );
        this.tileEntityConverter = new TileEntities( context, this.itemConverter = itemConverter, entityConverter );
        this.entityConverter = new Entities( this.items, this.itemConverter );

        // Convert all region files first
        File regionFolder = new File( backupFolder, "region" );
        if ( regionFolder.exists() ) {
            convertRegionFiles( regionFolder );
        }
    }

    private void convertRegionFiles( File regionFolder ) {
        File[] regionFiles = regionFolder.listFiles( ( dir, name ) -> name.endsWith( ".mca" ) );
        if ( regionFiles == null ) {
            return;
        }

        AtomicLong amountOfChunksDone = new AtomicLong( 0 );
        ConcurrentLinkedQueue<NBTTagCompound> compounds = new ConcurrentLinkedQueue<>();
        AtomicBoolean readFinished = new AtomicBoolean( false );

        int useCores = Math.floorDiv( Runtime.getRuntime().availableProcessors(), 2 );

        ExecutorService service = Executors.newFixedThreadPool( useCores, r -> {
            Thread thread = new Thread( r );
            thread.setName( "Gomint - World converter" );
            return thread;
        } );

        // Start all threads
        for ( int i = 0; i < useCores; i++ ) {
            service.execute( () -> {
                while ( !readFinished.get() || !compounds.isEmpty() ) {
                    if ( compounds.isEmpty() ) {
                        try {
                            Thread.sleep( 5 );
                        } catch ( InterruptedException e ) {
                            // Ignored
                        }

                        continue;
                    }

                    doConvert( amountOfChunksDone, compounds.poll() );
                }

                finish();
            } );
        }

        // Iterate over all region files and check if they match the pattern
        long start = System.nanoTime();
        for ( File regionFile : regionFiles ) {
            String fileName = regionFile.getName();
            if ( fileName.startsWith( "r." ) ) {
                String[] split = fileName.split( "\\." );
                if ( split.length != 4 ) {
                    continue;
                }

                try {
                    RegionFileSingleChunk regionFileReader = new RegionFileSingleChunk( regionFile );

                    for ( int x = 0; x < 32; x++ ) {
                        for ( int z = 0; z < 32; z++ ) {
                            if ( compounds.size() > 400 ) { // Throttle when the converter threads are behind
                                try {
                                    LOGGER.debug( "Throttled, not enough CPU for conversion" );
                                    Thread.sleep( 20 );
                                } catch ( InterruptedException e ) {
                                    // Ignore
                                }
                            }

                            NBTTagCompound compound = regionFileReader.loadChunk( x, z );
                            if ( compound == null ) {
                                continue;
                            }

                            compounds.offer( compound );
                        }
                    }
                } catch ( IOException | AllocationLimitReachedException e ) {
                    LOGGER.error( "Could not convert region file: {}", fileName, e );
                }
            }
        }

        // Set read to finish and tell the executors to shutdown when they are done
        readFinished.set( true );
        service.shutdown();

        // Wait for service to shutdown, help with converting if needed
        while ( !service.isTerminated() ) {
            if ( !compounds.isEmpty() ) {
                doConvert( amountOfChunksDone, compounds.poll() );
            } else {
                try {
                    service.awaitTermination( 500, TimeUnit.MILLISECONDS );
                } catch ( InterruptedException e ) {
                    // Ignore
                }
            }
        }

        // Check for missing subchunks
        BlockIdentifier[] blocks = new BlockIdentifier[4096];
        Arrays.fill( blocks, new BlockIdentifier( "minecraft:air", (NBTTagCompound) null, (short) 0 ) );

        this.subChunksProcessed.long2ObjectEntrySet().forEach( byteSetEntry -> {
            int chunkX = (int) ( byteSetEntry.getLongKey() >> 32 );
            int chunkZ = (int) ( byteSetEntry.getLongKey() & 0xFFFFFFFF ) + Integer.MIN_VALUE;

            byte maxSection = 0;
            for ( byte aByte : byteSetEntry.getValue() ) {
                if ( aByte > maxSection ) {
                    maxSection = aByte;
                }
            }

            for ( byte i = 0; i < maxSection; i++ ) {
                if ( !byteSetEntry.getValue().contains( i ) ) {
                    this.storeSubChunkBlocks( i, chunkX, chunkZ, blocks );
                }
            }
        } );

        // Persist stuff from this thread
        finish();

        // Make a level.dat
        try {
            File backupFolder = new File( this.worldFolder, "backup" );
            NBTTagCompound levelDat = NBTTagCompound.readFrom( new File( backupFolder, "level.dat" ), true, ByteOrder.BIG_ENDIAN );
            NBTTagCompound dataCompound = levelDat.getCompound( "Data", false );

            try ( FileOutputStream fileOutputStream = new FileOutputStream( new File( this.worldFolder, "level.dat" ) ) ) {
                fileOutputStream.write( new byte[8] );

                NBTTagCompound levelDBDat = new NBTTagCompound( "" );
                levelDBDat.addValue( "SpawnX", dataCompound.getInteger( "SpawnX", 0 ) );
                levelDBDat.addValue( "SpawnY", dataCompound.getInteger( "SpawnY", 0 ) );
                levelDBDat.addValue( "SpawnZ", dataCompound.getInteger( "SpawnZ", 0 ) );
                levelDBDat.addValue( "StorageVersion", 8 );
                levelDBDat.addValue( "LevelName", dataCompound.getString( "LevelName", "converted-gomint" ) );
                levelDBDat.writeTo( fileOutputStream, false, ByteOrder.LITTLE_ENDIAN );
            }
        } catch ( IOException | AllocationLimitReachedException e ) {
            LOGGER.error( "Could not convert level.dat", e );
        }

        // Performance output
        long needed = TimeUnit.NANOSECONDS.toMillis( System.nanoTime() - start );
        LOGGER.info( "Done in {} ms - Processed {} subchunks - {} subchunks/s", needed, amountOfChunksDone.get(), ( 1000 / ( needed / (double) amountOfChunksDone.get() ) ) );
    }

    private void doConvert( AtomicLong amountOfChunksDone, NBTTagCompound compound ) {
        if ( compound == null ) {
            return;
        }

        NBTTagCompound levelCompound = compound.getCompound( "Level", false );

        int chunkX = levelCompound.getInteger( "xPos", 0 );
        int chunkZ = levelCompound.getInteger( "zPos", 0 );

        this.startChunk( chunkX, chunkZ );

        List<NBTTagCompound> newTileEntities = new ArrayList<>();
        Set<String> pistonHeadPositions = new HashSet<>();

        List<Object> sections = levelCompound.getList( "Sections", false );
        for ( Object section : sections ) {
            NBTTagCompound sectionCompound = (NBTTagCompound) section;
            List<NBTTagCompound> needsMergingTiles = this.readAndConvertSubchunk( chunkX, chunkZ, sectionCompound, pistonHeadPositions, levelCompound.containsKey( "GoMintConverted" ) );
            if ( needsMergingTiles != null ) {
                newTileEntities.addAll( needsMergingTiles );
            }

            amountOfChunksDone.incrementAndGet();
        }

        if ( !this.nukkitPMMPConverted && !levelCompound.containsKey( "GoMintConverted" ) ) {
            List<Object> entities = levelCompound.getList( "Entities", false );
            if ( entities != null && !entities.isEmpty() ) {
                for ( Object entity : entities ) {
                    NBTTagCompound entityCompound = (NBTTagCompound) entity;
                    String id = entityCompound.getString( "id", null );
                    if ( id != null ) {
                        if ( id.equals( "ItemFrame" ) ) {
                            NBTTagCompound tileCompound = new NBTTagCompound( "" );
                            tileCompound.addValue( "x", entityCompound.getInteger( "TileX", 0 ) );
                            tileCompound.addValue( "y", entityCompound.getInteger( "TileY", 0 ) );
                            tileCompound.addValue( "z", entityCompound.getInteger( "TileZ", 0 ) );
                            tileCompound.addValue( "id", "ItemFrame" );

                            ItemStack itemStack = getItemStack( entityCompound.getCompound( "Item", false ) );
                            NBTTagCompound itemCompound = tileCompound.getCompound( "Item", true );
                            itemCompound.addValue( "id", itemStack.getMaterial() );
                            itemCompound.addValue( "Data", itemStack.getData() );
                            itemCompound.addValue( "Amount", itemStack.getAmount() );

                            if ( itemStack.getNbtData() != null ) {
                                itemCompound.addValue( "tag", itemStack.getNbtData().deepClone( "tag" ) );
                            }

                            tileCompound.addValue( "ItemDropChange", entityCompound.getFloat( "ItemDropChange", 1f ) );
                            tileCompound.addValue( "ItemRotation", entityCompound.getByte( "ItemRotation", (byte) 0 ) );

                            newTileEntities.add( tileCompound );
                        }
                    }
                }
            }
        }

        List<Object> tileEntities = levelCompound.getList( "TileEntities", false );
        if ( tileEntities != null && !tileEntities.isEmpty() ) {
            for ( Object entity : tileEntities ) {
                NBTTagCompound tileCompound = (NBTTagCompound) entity;
                if ( this.nukkitPMMPConverted || levelCompound.containsKey( "GoMintConverted" ) ) {
                    newTileEntities.add( tileCompound );
                } else {
                    TileEntity tileEntity = this.tileEntityConverter.read( tileCompound );
                    if ( tileEntity != null ) {
                        NBTTagCompound finalCompound = new NBTTagCompound( "" );
                        tileEntity.toCompound( finalCompound, SerializationReason.PERSIST );
                        newTileEntities.add( finalCompound );
                    }
                }
            }
        }

        if ( !newTileEntities.isEmpty() ) {
            // Check for duplicates
            Set<BlockPosition> alreadySeen = new HashSet<>();
            for ( NBTTagCompound newTileEntity : new ArrayList<>( newTileEntities ) ) {
                BlockPosition pos = new BlockPosition( newTileEntity.getInteger( "x", 0 ),
                    newTileEntity.getInteger( "y", -1 ),
                    newTileEntity.getInteger( "z", 0 ) );

                if ( pos.getY() == -1 || !alreadySeen.add( pos ) ) {
                    newTileEntities.remove( newTileEntity );
                }
            }

            this.storeTileEntities( chunkX, chunkZ, newTileEntities );
        }

        // Entities
        List<Object> entities = levelCompound.getList( "Entities", false );
        if ( entities != null && !entities.isEmpty() ) {
            List<Entity> newEntities = new ArrayList<>();

            for ( Object entity : entities ) {
                NBTTagCompound entityCompound = (NBTTagCompound) entity;
                try {
                    Entity convertedEntity = this.entityConverter.read( entityCompound );
                    if ( convertedEntity != null ) {
                        newEntities.add( convertedEntity );
                    }
                } catch ( Exception e ) {
                    LOGGER.warn( "Could not convert entity. Skipping...", e );
                }
            }

            this.storeEntities( chunkX, chunkZ, newEntities );
        }

        this.persistChunk();
    }

    protected ItemStack getItemStack( NBTTagCompound compound ) {
        if ( compound == null ) {
            return this.items.create( 0, (short) 0, (byte) 0, null );
        }

        // Check for correct ids
        int material = 0;
        try {
            material = compound.getShort( "id", (short) 0 );
        } catch ( Exception e ) {
            material = this.itemConverter.getOrDefault( compound.getString( "id", "minecraft:air" ), 0 );
        }

        // Skip non existent items for PE
        if ( material == 0 ) {
            return this.items.create( 0, (short) 0, (byte) 0, null );
        }

        short data = compound.getShort( "Damage", (short) 0 );
        byte amount = compound.getByte( "Count", (byte) 1 );

        return this.items.create( material, data, amount, compound.getCompound( "tag", false ) );
    }

    private List<NBTTagCompound> readAndConvertSubchunk( int chunkX, int chunkZ, NBTTagCompound section, Set<String> pistonHeadPositions, boolean gomintConverted ) {
        List<NBTTagCompound> tiles = null;

        byte[] blocks = section.getByteArray( "Blocks", new byte[0] );
        byte[] addBlocks = section.getByteArray( "Add", new byte[0] );
        int sectionY = section.getByte( "Y", (byte) 0 );

        NibbleArray add = addBlocks.length > 0 ? NibbleArray.create( addBlocks ) : null;
        NibbleArray data = NibbleArray.create( section.getByteArray( "Data", new byte[0] ) );

        if ( blocks == null ) {
            throw new IllegalArgumentException( "Corrupt chunk: Section is missing obligatory compounds" );
        }

        BlockIdentifier[] newBlocks = new BlockIdentifier[4096];
        Set<String> pistons = new HashSet<>();

        for ( int j = 0; j < 16; ++j ) {
            for ( int i = 0; i < 16; ++i ) {
                for ( int k = 0; k < 16; ++k ) {
                    int blockIndex = ( j << 8 | k << 4 | i );

                    int blockId = ( ( ( add != null ? add.get( blockIndex ) << 8 : 0 ) | blocks[blockIndex] ) & 0xFF );
                    byte blockData = data.get( blockIndex );

                    short newIndex = (short) ( ( i << 8 ) + ( k << 4 ) + j );

                    if ( this.nukkitPMMPConverted || gomintConverted ) {
                        // TODO: Calculate proper state map
                        newBlocks[newIndex] = new BlockIdentifier( this.peConverter.convert( blockId ), (NBTTagCompound) null, (short) blockData );
                    } else {
                        // Block data converter
                        if ( blockId == 3 && blockData == 1 ) {
                            blockId = 198;
                            blockData = 0;
                        } else if ( blockId == 3 && blockData == 2 ) {
                            blockId = 243;
                            blockData = 0;
                        }

                        // Fix water & lava at the bottom of a chunk
                        if ( sectionY + j == 0 && ( blockId == 8 || blockId == 9 || blockId == 10 || blockId == 11 ) ) {
                            blockId = 7;
                            blockData = 0;
                        }

                        BlockIdentifier converted = this.converter.convert( blockId, blockData );
                        if ( converted == null ) {
                            newBlocks[newIndex] = new BlockIdentifier( "minecraft:air", (NBTTagCompound) null, (short) 0 );
                            LOGGER.warn( "Could not convert block {}:{}", blockId, blockData );
                        } else {
                            // Is this a piston? (they may lack tiles)
                            String block = converted.getBlockId();
                            switch ( block ) {
                                case "minecraft:flower_pot":
                                    int fullX = i + ( chunkX << 4 );
                                    int fullY = j + ( sectionY << 4 );
                                    int fullZ = k + ( chunkZ << 4 );

                                    NBTTagCompound tile = new NBTTagCompound( "" );
                                    tile.addValue( "x", fullX );
                                    tile.addValue( "y", fullY );
                                    tile.addValue( "z", fullZ );
                                    tile.addValue( "id", "FlowerPot" );
                                    tile.addValue( "isMovable", (byte) 1 );

                                    if ( converted.getData() > 0 ) {
                                        NBTTagCompound convertedPlant = tile.getCompound( "PlantBlock", true );

                                        switch ( converted.getData() ) {
                                            case 1: // Red flower
                                                convertedPlant.addValue( "name", "minecraft:red_flower" );
                                                convertedPlant.addValue( "val", (short) 0 );
                                                break;
                                            case 2: // Yellow flower
                                                convertedPlant.addValue( "name", "minecraft:yellow_flower" );
                                                convertedPlant.addValue( "val", (short) 0 );
                                                break;
                                            case 3: // Sapling
                                            case 4: // Sapling
                                            case 5: // Sapling
                                            case 6: // Sapling
                                                convertedPlant.addValue( "name", "minecraft:sapling" );
                                                convertedPlant.addValue( "val", (short) ( converted.getData() - 3 ) );
                                                break;
                                            case 7: // Red mushroom
                                                convertedPlant.addValue( "name", "minecraft:red_mushroom" );
                                                convertedPlant.addValue( "val", (short) 0 );
                                                break;
                                            case 8: // Brown mushroom
                                                convertedPlant.addValue( "name", "minecraft:brown_mushroom" );
                                                convertedPlant.addValue( "val", (short) 0 );
                                                break;
                                            case 9: // Cactus
                                                convertedPlant.addValue( "name", "minecraft:cactus" );
                                                convertedPlant.addValue( "val", (short) 0 );
                                                break;
                                            case 10: // Brown mushroom
                                                convertedPlant.addValue( "name", "minecraft:deadbush" );
                                                convertedPlant.addValue( "val", (short) 0 );
                                                break;
                                            case 11: // Tall grass
                                                convertedPlant.addValue( "name", "minecraft:tallgrass" );
                                                convertedPlant.addValue( "val", (short) 3 );
                                                break;
                                            case 12: // Sapling
                                            case 13: // Sapling
                                                convertedPlant.addValue( "name", "minecraft:sapling" );
                                                convertedPlant.addValue( "val", (short) ( converted.getData() - 8 ) );
                                                break;
                                        }

                                        // TODO: Calculate proper state map
                                        converted = new BlockIdentifier( converted.getBlockId(), (NBTTagCompound) null, (short) 1 );
                                    }

                                    if ( tiles == null ) {
                                        tiles = new ArrayList<>();
                                    }

                                    tiles.add( tile );

                                    break;

                                case "minecraft:pistonArmCollision":
                                    fullX = i + ( chunkX << 4 );
                                    fullY = j + ( sectionY << 4 );
                                    fullZ = k + ( chunkZ << 4 );

                                    pistonHeadPositions.add( fullX + ":" + fullY + ":" + fullZ );
                                    break;

                                case "minecraft:sticky_piston":
                                case "minecraft:piston":
                                    fullX = i + ( chunkX << 4 );
                                    fullY = j + ( sectionY << 4 );
                                    fullZ = k + ( chunkZ << 4 );

                                    pistons.add( fullX + ":" + fullY + ":" + fullZ + ":" + ( block.equals( "minecraft:sticky_piston" ) ? 1 : 0 ) );

                                    break;

                                case "minecraft:bed":
                                    fullX = i + ( chunkX << 4 );
                                    fullY = j + ( sectionY << 4 );
                                    fullZ = k + ( chunkZ << 4 );

                                    BedTileEntity bedTileEntity = new BedTileEntity( new Block() {
                                        @Override
                                        public float getBlastResistance() {
                                            return 0;
                                        }

                                        @Override
                                        public BlockType getType() {
                                            return null;
                                        }

                                        @Override
                                        public Location getLocation() {
                                            return new Location( null, fullX, fullY, fullZ );
                                        }
                                    } );
                                    NBTTagCompound compound = new NBTTagCompound( "" );
                                    bedTileEntity.toCompound( compound, SerializationReason.PERSIST );

                                    if ( tiles == null ) {
                                        tiles = new ArrayList<>();
                                    }

                                    tiles.add( compound );
                                    break;
                            }

                            newBlocks[newIndex] = converted;
                        }
                    }
                }
            }
        }

        if ( !this.nukkitPMMPConverted && !gomintConverted ) {
            for ( String piston : pistons ) {
                String[] split = piston.split( ":" );

                int fullX = Integer.parseInt( split[0] );
                int fullY = Integer.parseInt( split[1] );
                int fullZ = Integer.parseInt( split[2] );
                boolean sticky = split[3].equals( "1" );

                if ( tiles == null ) {
                    tiles = new ArrayList<>();
                }

                NBTTagCompound compound = new NBTTagCompound( "" );
                compound.addValue( "x", fullX );
                compound.addValue( "y", fullY );
                compound.addValue( "z", fullZ );
                compound.addValue( "id", "PistonArm" );
                compound.addValue( "Sticky", (byte) ( sticky ? 1 : 0 ) );

                if ( pistonHeadPositions.contains( fullX + ":" + ( fullY + 1 ) + ":" + fullZ ) ) {
                    compound.addValue( "State", (byte) 1 );
                    compound.addValue( "NewState", (byte) 1 );

                    compound.addValue( "Progress", 1F );
                    compound.addValue( "LastProgress", 1F );
                } else {
                    compound.addValue( "State", (byte) 0 );
                    compound.addValue( "NewState", (byte) 0 );

                    compound.addValue( "Progress", 0F );
                    compound.addValue( "LastProgress", 0F );
                }

                tiles.add( compound );
            }
        }

        this.storeSubChunkBlocks( sectionY, chunkX, chunkZ, newBlocks );

        synchronized ( this.subChunksProcessed ) {
            long hash = CoordinateUtils.toLong( chunkX, chunkZ );
            ByteSet subChunkSet = this.subChunksProcessed.get( hash );
            if ( subChunkSet == null ) {
                subChunkSet = new ByteOpenHashSet();
                this.subChunksProcessed.put( hash, subChunkSet );
            }

            subChunkSet.add( (byte) sectionY );
        }

        return tiles;
    }

}
