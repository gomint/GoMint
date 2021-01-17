package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.BlockUnderwaterTorch;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:underwater_torch" )
public class UnderwaterTorch extends Torch implements BlockUnderwaterTorch {

    @Override
    public String blockId() {
        return "minecraft:underwater_torch";
    }

    @Override
    public BlockType blockType() {
        return BlockType.UNDERWATER_TORCH;
    }

}
