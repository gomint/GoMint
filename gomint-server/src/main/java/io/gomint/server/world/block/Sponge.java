package io.gomint.server.world.block;

import io.gomint.world.block.BlockSponge;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:sponge" )
public class Sponge extends Block implements BlockSponge {

    @Override
    public String blockId() {
        return "minecraft:sponge";
    }

    @Override
    public long breakTime() {
        return 900;
    }

    @Override
    public float blastResistance() {
        return 3.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.SPONGE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
}
