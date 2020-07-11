package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockRedTorch;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:colored_torch_rg" )
public class RedTorch extends Torch implements BlockRedTorch {

    @Override
    public String getBlockId() {
        return "minecraft:colored_torch_rg";
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RED_TORCH;
    }

}
