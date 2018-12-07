package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:sapling" )
public class Sapling extends Block implements io.gomint.world.block.BlockSapling {

    @Override
    public String getBlockId() {
        return "minecraft:sapling";
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
    public BlockType getType() {
        return BlockType.SAPLING;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

}
