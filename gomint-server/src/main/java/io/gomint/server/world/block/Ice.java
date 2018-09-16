package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemFlowingWater;
import io.gomint.math.BlockPosition;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:ice" )
public class Ice extends Block implements io.gomint.world.block.BlockIce {

    @Override
    public String getBlockId() {
        return "minecraft:ice";
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.ICE;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        return new ArrayList<>();
    }

    @Override
    public boolean onBreak( boolean creative ) {
        Block below = this.world.getBlockAt( this.location.toBlockPosition().add( BlockPosition.DOWN ) );
        if( !creative || below.getType() != BlockType.AIR ) {
            this.setType( FlowingWater.class );
        }

        return super.onBreak( creative );
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
