package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.ProgressBlockState;
import io.gomint.world.block.BlockFarmland;
import io.gomint.world.block.BlockType;

import io.gomint.math.BlockPosition;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.StatefulBlockSearcher;
import io.gomint.server.world.UpdateReason;

import java.util.function.Function;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:farmland" )
public class Farmland extends Block implements BlockFarmland {

    private static final ProgressBlockState WATER_LEVEL = new ProgressBlockState( () -> new String[]{"moisturized_amount"}, 7, aVoid -> {} );

    @Override
    public String getBlockId() {
        return "minecraft:farmland";
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

    @Override
    public boolean transparent() {
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
                    this.position.add( new BlockPosition( -4, 0, -4 ) ),
                    this.position.add( new BlockPosition( 4, 1, 4 ) ),
                    block -> block instanceof FlowingWater || block instanceof StationaryWater
                );

                if ( recalc( blockSearcher ) ) {
                    StatefulBlockSearcher finalBlockSearcher = blockSearcher;
                    storeInTemporaryStorage( "blockSearcher", (Function<StatefulBlockSearcher, StatefulBlockSearcher>) statefulBlockSearcher -> finalBlockSearcher );
                }
            }
        }

        // No need for reschedule
        return -1;
    }

    private boolean recalc( StatefulBlockSearcher blockSearcher ) {
        BlockPosition waterBlock = blockSearcher.validate();
        if ( waterBlock != null ) {
            if ( WATER_LEVEL.getState(this) < 1f ) {
                WATER_LEVEL.setState(this,  1f );
            }

            return true;
        } else if ( WATER_LEVEL.getState(this) > 0 ) {
            WATER_LEVEL.setState(this,  WATER_LEVEL.getState(this) - WATER_LEVEL.getStep() );
        }

        return false;
    }

    @Override
    public float getBlastResistance() {
        return 3.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.FARMLAND;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.SHOVEL;
    }

    @Override
    public float moisture() {
        return WATER_LEVEL.getState(this);
    }

    @Override
    public BlockFarmland moisture(float moisture) {
        WATER_LEVEL.setState(this, moisture);
        return this;
    }

}
