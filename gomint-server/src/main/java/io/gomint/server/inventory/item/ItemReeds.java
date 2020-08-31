package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:reeds", id = 338 )
public class ItemReeds extends ItemStack implements io.gomint.inventory.item.ItemReeds {



    @Override
    public ItemType getItemType() {
        return ItemType.REEDS;
    }

}