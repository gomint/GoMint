package io.gomint.server.world.block;

import io.gomint.world.block.BlockRedstoneRepeaterInactive;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:unpowered_repeater" )
public class RedstoneRepeaterInactive extends Block implements BlockRedstoneRepeaterInactive {

    @Override
    public String getBlockId() {
        return "minecraft:unpowered_repeater";
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.REDSTONE_REPEATER_INACTIVE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
