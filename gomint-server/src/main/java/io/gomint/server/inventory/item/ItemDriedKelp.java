package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:dried_kelp" )
public class ItemDriedKelp extends ItemStack< io.gomint.inventory.item.ItemDriedKelp> implements io.gomint.inventory.item.ItemDriedKelp {

    @Override
    public ItemType itemType() {
        return ItemType.DRIED_KELP;
    }

}
