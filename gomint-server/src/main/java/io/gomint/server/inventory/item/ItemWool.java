package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wool", id = 35)
public class ItemWool extends ItemStack implements io.gomint.inventory.item.ItemWool {

    @Override
    public ItemType getItemType() {
        return ItemType.WOOL;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.values()[this.getData()];
    }

    @Override
    public void setColor(BlockColor color) {
        this.setData((short) color.ordinal());
    }

}
