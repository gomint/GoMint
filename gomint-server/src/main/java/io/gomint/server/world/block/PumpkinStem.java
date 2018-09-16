package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:pumpkin_stem" )
public class PumpkinStem extends Block implements io.gomint.world.block.BlockPumpkinStem {

    @Override
    public String getBlockId() {
        return "minecraft:pumpkin_stem";
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
        return BlockType.PUMPKIN_STEM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
