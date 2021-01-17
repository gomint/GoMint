package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockTwistingVines;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:twisting_vines" )
public class TwistingVines extends Block implements BlockTwistingVines {

    @Override
    public String blockId() {
        return "minecraft:twisting_vines";
    }

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public float blastResistance() {
        return 0;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public BlockType blockType() {
        return BlockType.TWISTING_VINES;
    }
}
