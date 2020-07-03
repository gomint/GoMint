package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemHoe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:twisting_vines" )
public class TwistingVines extends Block implements io.gomint.world.block.BlockTwistingVines {

    @Override
    public String getBlockId() {
        return "minecraft:twisting_vines";
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
        return BlockType.TWISTING_VINES;
    }
}
