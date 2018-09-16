package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:snow" )
public class Snow extends Block implements io.gomint.world.block.BlockSnow {

    @Override
    public String getBlockId() {
        return "minecraft:snow";
    }

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public float getBlastResistance() {
        return 1.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.SNOW;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
