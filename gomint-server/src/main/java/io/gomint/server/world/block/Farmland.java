package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.math.BlockPosition;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.StatefulBlockSearcher;
import io.gomint.server.world.UpdateReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 60 )
public class Farmland extends Block implements io.gomint.world.block.BlockFarmland {

    private static final Logger LOGGER = LoggerFactory.getLogger( Farmland.class );

    @Override
    public int getBlockId() {
        return 60;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public long update( UpdateReason updateReason, long currentTimeMS, float dT ) {
        // Check water state when random ticked
        if ( updateReason == UpdateReason.RANDOM || updateReason == UpdateReason.EXPLOSION ) {
            // Do we have a stored block searcher?
            StatefulBlockSearcher blockSearcher = getFromTemporaryStorage( "blockSearcher" );
            if ( blockSearcher != null ) {
                recalc( blockSearcher );
            } else {
                blockSearcher = new StatefulBlockSearcher(
                    this.world,
                    this.location.toBlockPosition().add( new BlockPosition( -4, 0, -4 ) ),
                    this.location.toBlockPosition().add( new BlockPosition( 4, 1, 4 ) ),
                    new Predicate<io.gomint.world.block.Block>() {
                        @Override
                        public boolean test( io.gomint.world.block.Block block ) {
                            // We need any sort of water
                            return block instanceof FlowingWater || block instanceof StationaryWater;
                        }
                    }
                );

                if ( recalc( blockSearcher ) ) {
                    StatefulBlockSearcher finalBlockSearcher = blockSearcher;
                    storeInTemporaryStorage( "blockSearcher", new Function<StatefulBlockSearcher, StatefulBlockSearcher>() {
                        @Override
                        public StatefulBlockSearcher apply( StatefulBlockSearcher statefulBlockSearcher ) {
                            return finalBlockSearcher;
                        }
                    } );
                }
            }
        }

        // No need for reschedule
        return -1;
    }

    private boolean recalc( StatefulBlockSearcher blockSearcher ) {
        BlockPosition waterBlock = blockSearcher.validate();
        if ( waterBlock != null ) {
            if ( this.getBlockData() != 7 ) {
                this.setBlockData( (byte) 7 );
                this.updateBlock();
            }

            return true;
        } else if ( this.getBlockData() > 0 ) {
            this.setBlockData( (byte) ( this.getBlockData() - 1 ) );
            this.updateBlock();
        }

        return false;
    }

    @Override
    public float getBlastResistance() {
        return 3.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.FARMLAND;
    }

}