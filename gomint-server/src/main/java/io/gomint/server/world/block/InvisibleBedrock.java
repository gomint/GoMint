package io.gomint.server.world.block;

import io.gomint.world.block.BlockInvisibleBedrock;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:invisibleBedrock" )
public class InvisibleBedrock extends Block implements BlockInvisibleBedrock {

    @Override
    public String blockId() {
        return "minecraft:invisibleBedrock";
    }

    @Override
    public long breakTime() {
        return -1;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 1.8E7f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.INVISIBLE_BEDROCK;
    }

}
