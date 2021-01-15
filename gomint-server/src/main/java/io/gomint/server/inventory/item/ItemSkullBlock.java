package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:item.skull", id = 144)
public class ItemSkullBlock extends ItemStack<ItemSkullBlock> {

    @Override
    public ItemType itemType() {
        return ItemType.SKULL;
    }

}
