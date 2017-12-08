package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 15 )
public class IronOre extends Block implements io.gomint.world.block.BlockIronOre {

    @Override
    public int getBlockId() {
        return 15;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.IRON_ORE;
    }

}