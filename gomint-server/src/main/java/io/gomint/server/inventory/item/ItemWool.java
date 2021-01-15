package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wool", id = 35)
public class ItemWool extends ItemStack< io.gomint.inventory.item.ItemWool> implements io.gomint.inventory.item.ItemWool {

    @Override
    public ItemType itemType() {
        return ItemType.WOOL;
    }

    @Override
    public BlockColor color() {
        return BlockColor.values()[this.data()];
    }

    @Override
    public ItemWool color(BlockColor color) {
        this.data((short) color.ordinal());
        return this;
    }

}
