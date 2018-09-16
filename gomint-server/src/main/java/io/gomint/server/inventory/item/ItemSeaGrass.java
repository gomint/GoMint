package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = -130 )
public class ItemSeaGrass extends ItemStack implements io.gomint.inventory.item.ItemSeaGrass {

    @Override
    public String getBlockId() {
        return "minecraft:seagrass";
    }

    @Override
    public ItemType getType() {
        return ItemType.SEA_GRASS;
    }

}
