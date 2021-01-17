package io.gomint.server.world.block;

import io.gomint.world.block.BlockPumpkinStem;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:pumpkin_stem" )
public class PumpkinStem extends Growable implements BlockPumpkinStem {

    @Override
    public String blockId() {
        return "minecraft:pumpkin_stem";
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.PUMPKIN_STEM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
