package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:end_rod" )
public class EndRod extends Block implements io.gomint.world.block.BlockEndRod {

    @Override
    public String getBlockId() {
        return "minecraft:end_rod";
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.END_ROD;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
