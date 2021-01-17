package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.world.block.BlockPackedIce;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:packed_ice" )
public class PackedIce extends Block implements BlockPackedIce {

    @Override
    public String blockId() {
        return "minecraft:packed_ice";
    }

    @Override
    public long breakTime() {
        return 750;
    }

    @Override
    public float blastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.PACKED_ICE;
    }

    @Override
    public List<ItemStack<?>> drops(ItemStack<?> itemInHand ) {
        return new ArrayList<>();
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
