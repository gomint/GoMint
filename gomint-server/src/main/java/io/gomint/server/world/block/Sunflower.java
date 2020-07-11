package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:double_plant" )
public class Sunflower extends Block implements io.gomint.world.block.BlockSunflower {

    @Override
    public String getBlockId() {
        return "minecraft:double_plant";
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
    
    @Override
    public long getBreakTime() {
        return 0;
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
        return BlockType.SUNFLOWER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
