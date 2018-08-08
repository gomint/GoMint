package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ItemKelp extends ItemStack implements io.gomint.inventory.item.ItemKelp {

    @Override
    public ItemType getType() {
        return ItemType.KELP;
    }
}
