package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockWeepingVines;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:weeping_vines" )
public class WeepingVines extends Block implements BlockWeepingVines {

    @Override
    public String blockId() {
        return "minecraft:weeping_vines";
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
        return BlockType.WEEPING_VINES;
    }
}
