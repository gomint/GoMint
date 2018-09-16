package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 235 )
public class ItemBlackGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemBlackGlazedTerracotta {



    @Override
    public String getBlockId() {
        return "minecraft:black_glazed_terracotta";
    }

    @Override
    public ItemType getType() {
        return ItemType.BLACK_GLAZED_TERRACOTTA;
    }

}
