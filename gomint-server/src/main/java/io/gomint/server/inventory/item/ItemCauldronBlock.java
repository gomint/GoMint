package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:item.cauldron")
public class ItemCauldronBlock extends ItemStack<ItemCauldronBlock> {

    @Override
    public ItemType itemType() {
        return ItemType.CAULDRON;
    }

}
