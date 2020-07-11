package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:melon_stem" )
public class MelonStem extends Growable implements io.gomint.world.block.BlockMelonStem {

    @Override
    public String getBlockId() {
        return "minecraft:melon_stem";
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
        return BlockType.MELON_STEM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
