package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:weeping_vines" )
public class WeepingVines extends Block implements io.gomint.world.block.BlockWeepingVines {

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
    public BlockType getType() {
        return BlockType.WEEPING_VINES;
    }
}
