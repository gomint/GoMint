package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = -208, sId = "minecraft:lantern" )
public class ItemLantern extends ItemStack implements io.gomint.inventory.item.ItemLantern {

    @Override
    public String getBlockId() {
        return "minecraft:lantern";
    }

    @Override
    public ItemType getType() {
        return ItemType.LANTERN;
    }

}
