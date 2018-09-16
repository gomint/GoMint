package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:glowstone" )
public class Glowstone extends Block implements io.gomint.world.block.BlockGlowstone {

    @Override
    public String getBlockId() {
        return "minecraft:glowstone";
    }

    @Override
    public long getBreakTime() {
        return 450;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 1.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.GLOWSTONE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
}
