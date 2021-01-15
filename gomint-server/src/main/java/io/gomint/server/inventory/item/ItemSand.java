package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.inventory.item.data.SandType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockSand;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sand", id = 12)
public class ItemSand extends ItemStack< io.gomint.inventory.item.ItemSand> implements io.gomint.inventory.item.ItemSand {

    @Override
    public ItemType itemType() {
        return ItemType.SAND;
    }

    @Override
    public ItemSand type(SandType type) {
        this.data((short) type.ordinal());
        return this;
    }

    @Override
    public SandType type() {
        return SandType.values()[this.data()];
    }

    @Override
    public Block block() {
        BlockSand block = (BlockSand) super.block();
        block.type(this.type());
        return block;
    }

}
