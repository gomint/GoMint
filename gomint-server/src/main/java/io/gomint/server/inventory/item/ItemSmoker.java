package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = -198, sId = "minecraft:smoker")
public class ItemSmoker extends ItemStack implements io.gomint.inventory.item.ItemSmoker {

    @Override
    public ItemType getItemType() {
        return ItemType.SMOKER;
    }

}
