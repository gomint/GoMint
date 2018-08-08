package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ItemDriedKelp extends ItemStack implements io.gomint.inventory.item.ItemDriedKelp {

    @Override
    public ItemType getType() {
        return ItemType.DRIED_KELP;
    }
}
