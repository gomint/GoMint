package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:end_gateway" )
public class EndGateway extends Block implements io.gomint.world.block.BlockEndGateway {

    @Override
    public String getBlockId() {
        return "minecraft:end_gateway";
    }

    @Override
    public long getBreakTime() {
        return -1;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 1.8E7f;
    }

    @Override
    public BlockType getType() {
        return BlockType.END_GATEWAY;
    }

}
