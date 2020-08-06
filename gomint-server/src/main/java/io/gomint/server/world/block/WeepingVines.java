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
    public String getBlockId() {
        return "minecraft:weeping_vines";
    }

    @Override
    public long getBreakTime() {
        return 0;
    }

    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WEEPING_VINES;
    }
}
