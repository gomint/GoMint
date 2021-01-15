package io.gomint.server.world.block;

import io.gomint.world.block.BlockRedstoneLampInactive;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:redstone_lamp" )
public class RedstoneLampInactive extends Block implements BlockRedstoneLampInactive {

    @Override
    public String getBlockId() {
        return "minecraft:redstone_lamp";
    }

    @Override
    public long breakTime() {
        return 450;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 1.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.REDSTONE_LAMP_INACTIVE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
