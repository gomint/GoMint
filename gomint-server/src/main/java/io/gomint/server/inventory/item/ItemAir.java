package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:air", id = 0)
public class ItemAir extends ItemStack implements io.gomint.inventory.item.ItemAir {

    @Override
    public ItemType getItemType() {
        return ItemType.AIR;
    }

}
