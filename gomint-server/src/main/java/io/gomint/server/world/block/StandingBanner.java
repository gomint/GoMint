package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:standing_banner" )
public class StandingBanner extends Banner {

    @Override
    public String getBlockId() {
        return "minecraft:standing_banner";
    }

    @Override
    public BlockType getType() {
        return BlockType.STANDING_BANNER;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
