package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:bell", id = 138)
public class ItemBell extends ItemStack implements io.gomint.inventory.item.ItemBell {

    @Override
    public ItemType getType() {
        return ItemType.BELL;
    }

}
