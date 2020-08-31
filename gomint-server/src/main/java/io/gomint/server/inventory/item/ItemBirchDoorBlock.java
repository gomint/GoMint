package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:item.birch_door", id = 194 )
public class ItemBirchDoorBlock extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.BIRCH_DOOR;
    }

}
