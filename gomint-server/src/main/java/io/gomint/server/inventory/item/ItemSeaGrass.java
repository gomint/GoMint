package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:seagrass")
public class ItemSeaGrass extends ItemStack< io.gomint.inventory.item.ItemSeaGrass> implements io.gomint.inventory.item.ItemSeaGrass {

    @Override
    public ItemType itemType() {
        return ItemType.SEA_GRASS;
    }

}
