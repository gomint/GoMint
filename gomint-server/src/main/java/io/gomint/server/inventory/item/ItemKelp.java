package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:kelp", id = 335 )
public class ItemKelp extends ItemStack implements io.gomint.inventory.item.ItemKelp {

    @Override
    public ItemType getItemType() {
        return ItemType.KELP;
    }

}
