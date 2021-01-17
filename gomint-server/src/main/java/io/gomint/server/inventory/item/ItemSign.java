package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sign", def = true)
@RegisterInfo(sId = "minecraft:spruce_sign")
@RegisterInfo(sId = "minecraft:birch_sign")
@RegisterInfo(sId = "minecraft:jungle_sign")
@RegisterInfo(sId = "minecraft:acacia_sign")
@RegisterInfo(sId = "minecraft:darkoak_sign")
public class ItemSign extends ItemStack< io.gomint.inventory.item.ItemSign> implements io.gomint.inventory.item.ItemSign {

    @Override
    public ItemType itemType() {
        return ItemType.SIGN;
    }

}
