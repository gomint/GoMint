package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:kelp", def = true )
@RegisterInfo( sId = "minecraft:item.kelp" )
public class ItemKelp extends ItemStack< io.gomint.inventory.item.ItemKelp> implements io.gomint.inventory.item.ItemKelp {

    @Override
    public ItemType itemType() {
        return ItemType.KELP;
    }

}
