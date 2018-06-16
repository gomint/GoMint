package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 91 )
public class JackOLantern extends Block implements io.gomint.world.block.BlockJackOLantern {

    @Override
    public int getBlockId() {
        return 91;
    }

    @Override
    public long getBreakTime() {
        return 1500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.JACK_O_LANTERN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
