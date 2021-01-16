package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 323, sId = "minecraft:sign", def = true)
@RegisterInfo(id = 472, sId = "minecraft:spruce_sign")
@RegisterInfo(id = 473, sId = "minecraft:birch_sign")
@RegisterInfo(id = 474, sId = "minecraft:jungle_sign")
@RegisterInfo(id = 475, sId = "minecraft:acacia_sign")
@RegisterInfo(id = 476, sId = "minecraft:darkoak_sign")
public class ItemSign extends ItemStack< io.gomint.inventory.item.ItemSign> implements io.gomint.inventory.item.ItemSign {

    @Override
    public ItemType itemType() {
        return ItemType.SIGN;
    }

}
