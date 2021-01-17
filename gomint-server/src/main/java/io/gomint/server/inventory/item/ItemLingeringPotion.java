package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:lingering_potion" )
public class ItemLingeringPotion extends ItemStack< io.gomint.inventory.item.ItemLingeringPotion> implements io.gomint.inventory.item.ItemLingeringPotion {



    @Override
    public ItemType itemType() {
        return ItemType.LINGERING_POTION;
    }

}
