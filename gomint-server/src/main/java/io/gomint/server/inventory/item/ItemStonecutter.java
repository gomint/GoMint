package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 245, sId = "minecraft:stonecutter")
@RegisterInfo(id = -197, sId = "minecraft:stonecutter_block")
public class ItemStonecutter extends ItemStack implements io.gomint.inventory.item.ItemStonecutter {

    @Override
    public ItemType getItemType() {
        return ItemType.STONECUTTER;
    }

}
