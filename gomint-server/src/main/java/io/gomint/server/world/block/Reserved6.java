package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 255 )
public class Reserved6 extends Block implements io.gomint.world.block.BlockReserved6 {

    @Override
    public int getBlockId() {
        return 255;
    }

    @Override
    public float getBlastResistance() {
        return 1.8E7f;
    }

    @Override
    public BlockType getType() {
        return BlockType.RESERVED6;
    }

}