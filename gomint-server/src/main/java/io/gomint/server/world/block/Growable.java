package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.server.util.random.FastRandom;
import io.gomint.server.util.random.WeightedRandom;
import io.gomint.server.world.UpdateReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 * <p>
 * This is a generic class for anything that can grow with metadata like crops, beetroot etc.
 */
public abstract class Growable extends Block {

    private static final Logger LOGGER = LoggerFactory.getLogger( Growable.class );

    /**
     * Randomizer for seed drop output
     */
    protected static final WeightedRandom<Integer> SEED_RANDOMIZER = new WeightedRandom<>();

    static {
        SEED_RANDOMIZER.add( 0.15, 0 );
        SEED_RANDOMIZER.add( 0.35, 1 );
        SEED_RANDOMIZER.add( 0.35, 2 );
        SEED_RANDOMIZER.add( 0.15, 3 );
    }

    @Override
    public boolean beforePlacement( ItemStack item, Location location ) {
        // Check if we place on farmland
        return location.getWorld().getBlockAt( location.toBlockPosition().add( BlockPosition.DOWN ) ) instanceof Farmland;
    }

    @Override
    public long update( UpdateReason updateReason, long currentTimeMS, float dT ) {
        if ( updateReason == UpdateReason.NEIGHBOUR_UPDATE ) {
            // Check if farmland is still under us
            if ( !( this.world.getBlockAt( this.location.toBlockPosition().add( BlockPosition.DOWN ) ) instanceof Farmland ) ) {
                this.world.breakBlock( this.location.toBlockPosition(), false, ItemAir.create( 0 ) );
            }
        } else if ( updateReason == UpdateReason.RANDOM ) {
            // Check for growth state
            if ( this.getBlockData() < 0x07 ) {
                float growthDivider = getGrowthDivider();
                int random = FastRandom.current().nextInt( (int) ( ( 25f / growthDivider ) + 1 ) );

                // Grow it
                if ( random == 0 ) {
                    // TODO: Some sort of growth event
                    this.setBlockData( (byte) ( this.getBlockData() + 1 ) );
                    this.updateBlock();
                }
            }
        }

        return -1;
    }

    private float getGrowthDivider() {
        float divider = 1f;
        BlockPosition underCrops = this.location.toBlockPosition().add( BlockPosition.DOWN );

        // Check for farmland blocks around (hydration states)
        for ( int x = -1; x <= 1; ++x ) {
            for ( int z = -1; z <= 1; ++z ) {
                float currentBlockDivider = 0f;

                Block block = this.world.getBlockAt( underCrops.clone().add( new BlockPosition( x, 0, z ) ) );
                if ( block instanceof Farmland ) {
                    currentBlockDivider = 1f;

                    if ( block.getBlockData() > 0 ) {
                        currentBlockDivider = 3f;
                    }
                }

                // Surrounding blocks affect only to 25%
                if ( x != 0 || z != 0 ) {
                    currentBlockDivider /= 4f;
                }

                divider += currentBlockDivider;
            }
        }

        // Check if there are similar crops around (slow down bigger farms)
        // We check in a pattern which goes up and clockwise around
        BlockPosition start = this.location.toBlockPosition().add( BlockPosition.NORTH );
        if ( this.world.getBlockAt( start ).getType() == getType() ||
            this.world.getBlockAt( start.add( BlockPosition.EAST ) ).getType() == getType() ||
            this.world.getBlockAt( start.add( BlockPosition.SOUTH ) ).getType() == getType() ||
            this.world.getBlockAt( start.add( BlockPosition.SOUTH ) ).getType() == getType() ||
            this.world.getBlockAt( start.add( BlockPosition.WEST ) ).getType() == getType() ||
            this.world.getBlockAt( start.add( BlockPosition.WEST ) ).getType() == getType() ||
            this.world.getBlockAt( start.add( BlockPosition.NORTH ) ).getType() == getType() ||
            this.world.getBlockAt( start.add( BlockPosition.NORTH ) ).getType() == getType() ) {
            divider /= 2f;
        }

        return divider;
    }

}
