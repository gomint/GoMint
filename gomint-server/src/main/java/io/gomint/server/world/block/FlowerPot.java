package io.gomint.server.world.block;

import io.gomint.world.block.BlockFlowerPot;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:flower_pot" )
public class FlowerPot extends Block implements BlockFlowerPot {

    @Override
    public String getBlockId() {
        return "minecraft:flower_pot";
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.FLOWER_POT;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
