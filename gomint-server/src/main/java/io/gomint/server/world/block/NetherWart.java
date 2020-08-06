package io.gomint.server.world.block;

import io.gomint.world.block.BlockNetherWart;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:nether_wart" )
public class NetherWart extends Block implements BlockNetherWart {

    @Override
    public String getBlockId() {
        return "minecraft:nether_wart";
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHER_WART;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
