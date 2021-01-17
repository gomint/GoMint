package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stonecutter")
@RegisterInfo(sId = "minecraft:stonecutter_block")
public class ItemStonecutter extends ItemStack< io.gomint.inventory.item.ItemStonecutter> implements io.gomint.inventory.item.ItemStonecutter {

    @Override
    public ItemType itemType() {
        return ItemType.STONECUTTER;
    }

}
