package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:item.hopper", id = 154)
public class ItemHopperBlock extends ItemStack {

    @Override
    public ItemType getItemType() {
        return ItemType.HOPPER;
    }

}
