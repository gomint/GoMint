package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 196 )
public class ItemAcaciaDoorBlock extends ItemStack {


    @Override
    public String getBlockId() {
        return "minecraft:acacia_door";
    }

    @Override
    public ItemType getType() {
        return ItemType.ACACIA_DOOR_BLOCK;
    }

}
