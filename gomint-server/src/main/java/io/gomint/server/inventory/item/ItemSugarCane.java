package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:reeds", id = 338)
public class ItemSugarCane extends ItemStack implements io.gomint.inventory.item.ItemSugarCane {

    @Override
    public ItemType getItemType() {
        return ItemType.SUGAR_CANE;
    }

}
