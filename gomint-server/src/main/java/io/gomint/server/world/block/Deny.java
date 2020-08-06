package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockDeny;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:deny" )
public class Deny extends Block implements BlockDeny {

    @Override
    public String getBlockId() {
        return "minecraft:deny";
    }

    @Override
    public long getBreakTime() {
        return -1;
    }

    @Override
    public boolean onBreak( boolean creative ) {
        return creative;
    }

    @Override
    public float getBlastResistance() {
        return 1.8E7f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DENY;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }
}
