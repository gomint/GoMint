package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:lapis_block")
public class ItemBlockOfLapisLazuli extends ItemStack< io.gomint.inventory.item.ItemBlockOfLapisLazuli> implements io.gomint.inventory.item.ItemBlockOfLapisLazuli {

    @Override
    public ItemType itemType() {
        return ItemType.LAPIS_LAZULI_BLOCK;
    }

}
