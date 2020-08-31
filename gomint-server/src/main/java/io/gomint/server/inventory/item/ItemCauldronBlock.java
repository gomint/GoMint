package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:item.cauldron", id = 118)
public class ItemCauldronBlock extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.CAULDRON;
    }

}
