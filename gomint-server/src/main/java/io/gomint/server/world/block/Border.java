package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBorder;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:border_block" )
public class Border extends Block implements BlockBorder {

    @Override
    public String blockId() {
        return "minecraft:border_block";
    }

    @Override
    public long breakTime() {
        return -1;
    }

    @Override
    public float blastResistance() {
        return 1.8E7f;
    }

    @Override
    public boolean onBreak( boolean creative ) {
        return creative;
    }

    @Override
    public BlockType blockType() {
        return BlockType.BORDER;
    }

    public boolean canBeBrokenWithHand() {
        return false;
    }

}
