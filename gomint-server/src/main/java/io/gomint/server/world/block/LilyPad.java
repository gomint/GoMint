package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.server.entity.Entity;
import io.gomint.world.block.BlockLilyPad;
import io.gomint.world.block.data.Facing;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:waterlily" )
public class LilyPad extends Block implements BlockLilyPad {

    @Override
    public String getBlockId() {
        return "minecraft:waterlily";
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LILY_PAD;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public boolean beforePlacement(Entity entity, ItemStack item, Facing face, Location location) {
        Block block = (Block) location.getWorld().getBlockAt( location.toBlockPosition() ).getSide( Facing.UP );
        if( block instanceof StationaryWater ) {
            return true;
        }

        return false;
    }

}
