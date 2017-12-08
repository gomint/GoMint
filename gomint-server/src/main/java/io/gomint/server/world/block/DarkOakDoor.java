package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 197 )
public class DarkOakDoor extends Door implements io.gomint.world.block.BlockDarkOakDoor {

    @Override
    public int getBlockId() {
        return 197;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.DARK_OAK_DOOR;
    }

}
