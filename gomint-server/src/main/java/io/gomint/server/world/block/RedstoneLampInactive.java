package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:redstone_lamp" )
public class RedstoneLampInactive extends Block implements io.gomint.world.block.BlockRedstoneLampInactive {

    @Override
    public String getBlockId() {
        return "minecraft:redstone_lamp";
    }

    @Override
    public long getBreakTime() {
        return 450;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 1.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.REDSTONE_LAMP_INACTIVE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
