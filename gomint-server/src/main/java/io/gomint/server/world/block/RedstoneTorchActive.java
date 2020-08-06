package io.gomint.server.world.block;

import io.gomint.world.block.BlockRedstoneTorchActive;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:redstone_torch" )
public class RedstoneTorchActive extends Block implements BlockRedstoneTorchActive {

    @Override
    public String getBlockId() {
        return "minecraft:redstone_torch";
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
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.REDSTONE_TORCH_ACTIVE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
