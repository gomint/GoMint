package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockBlueTorch;
import io.gomint.world.block.BlockType;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:colored_torch_bp" )
public class BlueTorch extends Torch implements BlockBlueTorch {

    @Override
    public String getBlockId() {
        return "minecraft:colored_torch_bp";
    }

    @Override
    public BlockType blockType() {
        return BlockType.BLUE_TORCH;
    }

}
