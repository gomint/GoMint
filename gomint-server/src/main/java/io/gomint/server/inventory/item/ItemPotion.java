package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.category.ItemConsumable;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:potion", id = 373 )
public class ItemPotion extends ItemStack implements io.gomint.inventory.item.ItemPotion, ItemConsumable {



    @Override
    public ItemType getItemType() {
        return ItemType.POTION;
    }

    @Override
    public void onConsume( EntityPlayer player ) {
        // Apply effects of the potion
    }

}