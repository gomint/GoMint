package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:seagrass", id = -130)
public class ItemSeaGrass extends ItemStack implements io.gomint.inventory.item.ItemSeaGrass {

    @Override
    public ItemType getItemType() {
        return ItemType.SEA_GRASS;
    }

}
