package io.gomint.server.world.block;

import io.gomint.world.block.BlockEndGateway;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:end_gateway" )
public class EndGateway extends Block implements BlockEndGateway {

    @Override
    public String blockId() {
        return "minecraft:end_gateway";
    }

    @Override
    public long breakTime() {
        return -1;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public float blastResistance() {
        return 1.8E7f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.END_GATEWAY;
    }

}
