package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:brown_mushroom" )
public class BrownMushroom extends Block implements io.gomint.world.block.BlockBrownMushroom {

    @Override
    public String getBlockId() {
        return "minecraft:brown_mushroom";
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BROWN_MUSHROOM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
