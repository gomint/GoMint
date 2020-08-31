package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:lingering_potion", id = 441 )
public class ItemLingeringPotion extends ItemStack implements io.gomint.inventory.item.ItemLingeringPotion {



    @Override
    public ItemType getItemType() {
        return ItemType.LINGERING_POTION;
    }

}