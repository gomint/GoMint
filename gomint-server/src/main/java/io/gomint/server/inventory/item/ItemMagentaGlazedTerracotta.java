package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:magenta_glazed_terracotta", id = 222 )
public class ItemMagentaGlazedTerracotta extends ItemStack implements io.gomint.inventory.item.ItemMagentaGlazedTerracotta {

    @Override
    public ItemType getType() {
        return ItemType.MAGENTA_GLAZED_TERRACOTTA;
    }

}
