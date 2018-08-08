package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class ItemNautilusShell extends ItemStack implements io.gomint.inventory.item.ItemNautilusShell {

    @Override
    public ItemType getType() {
        return ItemType.NAUTILUS_SHELL;
    }
}
