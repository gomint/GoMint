package io.gomint.server.world.block;

import io.gomint.world.block.BlockSugarCane;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:reeds" )
public class SugarCane extends Block implements BlockSugarCane {

    @Override
    public String getBlockId() {
        return "minecraft:reeds";
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.SUGAR_CANE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
