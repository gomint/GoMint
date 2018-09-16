package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:wall_banner" )
public class WallBanner extends Banner {

    @Override
    public String getBlockId() {
        return "minecraft:wall_banner";
    }

    @Override
    public BlockType getType() {
        return BlockType.WALL_BANNER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
