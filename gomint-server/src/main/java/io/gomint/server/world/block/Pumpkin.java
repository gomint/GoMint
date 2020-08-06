package io.gomint.server.world.block;

import io.gomint.world.block.BlockPumpkin;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:pumpkin" )
public class Pumpkin extends Block implements BlockPumpkin {

    @Override
    public String getBlockId() {
        return "minecraft:pumpkin";
    }

    @Override
    public long getBreakTime() {
        return 1500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PUMPKIN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
