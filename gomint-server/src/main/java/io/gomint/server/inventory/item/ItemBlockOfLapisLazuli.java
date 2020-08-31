package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:lapis_block", id = 22)
public class ItemBlockOfLapisLazuli extends ItemStack implements io.gomint.inventory.item.ItemBlockOfLapisLazuli {

    @Override
    public ItemType getItemType() {
        return ItemType.LAPIS_LAZULI_BLOCK;
    }

}
