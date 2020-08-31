package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:nautilus_shell", id = 465 )
public class ItemNautilusShell extends ItemStack implements io.gomint.inventory.item.ItemNautilusShell {

    @Override
    public ItemType getItemType() {
        return ItemType.NAUTILUS_SHELL;
    }

}