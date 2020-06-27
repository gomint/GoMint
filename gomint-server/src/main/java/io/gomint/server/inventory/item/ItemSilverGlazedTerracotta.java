package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:silver_glazed_terracotta", id = 228 )
public class ItemSilverGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemSilverGlazedTerracotta {

    @Override
    public ItemType getType() {
        return ItemType.SILVER_GLAZED_TERRACOTTA;
    }

}
