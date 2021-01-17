package io.gomint.server.world.block;

import io.gomint.world.block.BlockRedstoneTorchInactive;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:unlit_redstone_torch" )
public class RedstoneTorchInactive extends Block implements BlockRedstoneTorchInactive {

    @Override
    public String blockId() {
        return "minecraft:unlit_redstone_torch";
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public float blastResistance() {
        return 0f;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public BlockType blockType() {
        return BlockType.REDSTONE_TORCH_INACTIVE;
    }

}
