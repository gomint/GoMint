package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockAllow;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:allow" )
public class Allow extends Block implements BlockAllow {

    @Override
    public String getBlockId() {
        return "minecraft:allow";
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
        return BlockType.ALLOW;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return false;
    }
}
