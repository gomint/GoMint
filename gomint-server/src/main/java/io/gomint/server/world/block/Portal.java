package io.gomint.server.world.block;

import io.gomint.world.block.BlockPortal;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:portal" )
public class Portal extends Block implements BlockPortal {

    @Override
    public String blockId() {
        return "minecraft:portal";
    }

    @Override
    public long breakTime() {
        return -1;
    }

    @Override
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.PORTAL;
    }

}
