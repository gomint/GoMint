package io.gomint.server.world.block;

import io.gomint.entity.Entity;
import io.gomint.event.world.BlockPlaceEvent;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.maintenance.ReportUploader;
import io.gomint.server.registry.Generator;
import io.gomint.server.registry.Registry;
import io.gomint.server.world.PlacementData;
import io.gomint.server.world.WorldAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Blocks {

    private static final Logger LOGGER = LoggerFactory.getLogger( Blocks.class );
    private static long lastReport = 0;
    private final Registry<Block> generators;

    /**
     * Create a new block registry
     *
     * @param server which builds this registry
     */
    public Blocks( GoMintServer server ) {
        this.generators = new Registry<>( server, clazz -> () -> {
            try {
                return (Block) clazz.newInstance();
            } catch ( InstantiationException | IllegalAccessException e ) {
                LOGGER.error( "Could not generate new block", e );
            }

            return null;
        } );

        this.generators.register( "io.gomint.server.world.block" );
    }

    public <T extends Block> T get( int blockId, byte blockData, byte skyLightLevel, byte blockLightLevel,
                                    TileEntity tileEntity, Location location, int layer ) {
        Generator<Block> instance = this.generators.getGenerator( blockId );
        if ( instance != null ) {
            T block = (T) instance.generate();
            if ( location == null ) {
                return block;
            }

            block.setData( blockId, blockData, tileEntity, (WorldAdapter) location.getWorld(), location, layer, skyLightLevel, blockLightLevel );
            return block;
        }

        // Don't spam the report server pls
        if ( System.currentTimeMillis() - lastReport > TimeUnit.SECONDS.toSeconds( 10 ) ) {
            ReportUploader.create().includeWorlds().property( "missing_block", String.valueOf( blockId ) ).upload();
            lastReport = System.currentTimeMillis();
        }

        LOGGER.warn( "Unknown block {} @ {}", blockId, location, new Exception() );
        return null;
    }

    public Block get( int blockId ) {
        Generator<Block> instance = this.generators.getGenerator( blockId );
        if ( instance != null ) {
            return instance.generate();
        }

        LOGGER.warn( "Unknown block {}", blockId, new Exception() );
        return null;
    }

    public <T extends Block> T get( Class<?> apiInterface ) {
        Generator<Block> instance = this.generators.getGenerator( apiInterface );
        if ( instance != null ) {
            return (T) instance.generate();
        }

        return null;
    }

    public int getID( Class<?> block ) {
        return this.generators.getId( block );
    }

    public boolean replaceWithItem( EntityPlayer entity, Block clickedBlock, Block block, ItemStack item, Vector clickVector ) {
        // We need to change the block id first
        int id = ( (io.gomint.server.inventory.item.ItemStack) item ).getBlockId();
        Generator<Block> blockGenerator = this.generators.getGenerator( id );
        Block newBlock = blockGenerator.generate();
        if ( !newBlock.beforePlacement( entity, item, block.location ) ) {
            return false;
        }

        WorldAdapter adapter = (WorldAdapter) block.location.getWorld();
        PlacementData data = newBlock.calculatePlacementData( entity, item, clickVector );

        // Check only solid blocks for bounding box intersects
        if ( newBlock.isSolid() ) {
            newBlock.setLocation( block.location ); // Temp setting, needed for getting bounding boxes
            newBlock.setBlockData( data.getMetaData() );
            newBlock.generateBlockStates();

            for ( AxisAlignedBB bb : newBlock.getBoundingBox() ) {
                // Check other entities in the bounding box
                Collection<Entity> collidedWith = adapter.getNearbyEntities( bb, null, null );
                if ( collidedWith != null ) {
                    return false;
                }
            }
        }

        // We decided that the block would fit
        BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent( entity, clickedBlock, block, item, newBlock );
        block.world.getServer().getPluginManager().callEvent( blockPlaceEvent );

        if ( blockPlaceEvent.isCancelled() ) {
            return false;
        }


        block = block.setBlockFromPlacementData( data );
        block.afterPlacement( data );
        return true;
    }

}
