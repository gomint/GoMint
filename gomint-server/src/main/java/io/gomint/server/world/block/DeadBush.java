package io.gomint.server.world.block;

import io.gomint.world.block.BlockDeadBush;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:deadbush" )
public class DeadBush extends Block implements BlockDeadBush {

    @Override
    public String getBlockId() {
        return "minecraft:deadbush";
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
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.DEAD_BUSH;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
