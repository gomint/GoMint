package io.gomint.server.world.block;

import io.gomint.world.block.BlockRedstoneWire;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:redstone_wire" )
public class RedstoneWire extends Block implements BlockRedstoneWire {

    @Override
    public String blockId() {
        return "minecraft:redstone_wire";
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.REDSTONE_WIRE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
