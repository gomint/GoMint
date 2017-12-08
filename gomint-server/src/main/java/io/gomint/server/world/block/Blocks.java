package io.gomint.server.world.block;

import io.gomint.event.world.BlockPlaceEvent;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.item.generator.ItemGenerator;
import io.gomint.server.registry.GeneratorCallback;
import io.gomint.server.registry.Registry;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.block.generator.BlockGenerator;
import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Blocks {

    private static final Logger LOGGER = LoggerFactory.getLogger( Blocks.class );
    private static final Registry<BlockGenerator> GENERATORS = new Registry<>( ( id, clazz ) -> {
        try {
            return (BlockGenerator) Class.forName( "io.gomint.server.world.block.generator." + clazz.getSimpleName() + "Generator" ).newInstance();
        } catch ( ClassNotFoundException | IllegalAccessException | InstantiationException e1 ) {
            LOGGER.error( "Could not use pre generated generator: ", e1 );
        }

        return null;
    } );

    static {
        GENERATORS.register( "io.gomint.server.world.block" );
    }

    public static <T extends Block> T get( int blockId, byte blockData, byte skyLightLevel, byte blockLightLevel,
                                           TileEntity tileEntity, Location location ) {
        BlockGenerator instance = GENERATORS.getGenerator( blockId );
        if ( instance != null ) {
            return instance.generate( blockData, skyLightLevel, blockLightLevel, tileEntity, location );
        }

        LOGGER.warn( "Unknown block {}", blockId );
        return null;
    }

    public static <T extends Block> T get( Class<?> apiInterface ) {
        BlockGenerator instance = GENERATORS.getGenerator( apiInterface );
        if ( instance != null ) {
            return instance.generate();
        }

        return null;
    }

    public static int getID( Class<?> block ) {
        return GENERATORS.getId( block );
    }

    public static boolean replaceWithItem( EntityPlayer entity, Block clickedBlock, Block block, ItemStack item, Vector clickVector ) {
        // We need to change the block id first
        int id = ( (io.gomint.server.inventory.item.ItemStack) item ).getBlockId();
        BlockGenerator blockGenerator = GENERATORS.getGenerator( id );
        Block newBlock = blockGenerator.generate();
        if ( !newBlock.beforePlacement( item, block.location ) ) {
            return false;
        }

        // We decided that the block would fit
        BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent( entity, clickedBlock, block, item );
        block.world.getServer().getPluginManager().callEvent( blockPlaceEvent );

        if ( blockPlaceEvent.isCancelled() ) {
            return false;
        }

        PlacementData data = newBlock.calculatePlacementData( entity, item, clickVector );
        block = block.setType( newBlock.getClass(), data );
        block.afterPlacement();
        return true;
    }

}
