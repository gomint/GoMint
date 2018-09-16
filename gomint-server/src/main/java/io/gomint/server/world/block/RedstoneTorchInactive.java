package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:unlit_redstone_torch" )
public class RedstoneTorchInactive extends Block implements io.gomint.world.block.BlockRedstoneTorchInactive {

    @Override
    public String getBlockId() {
        return "minecraft:unlit_redstone_torch";
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 0f;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public BlockType getType() {
        return BlockType.REDSTONE_TORCH_INACTIVE;
    }

}
