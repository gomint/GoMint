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
@RegisterInfo( id = 373 )
public class ItemPotion extends ItemStack implements io.gomint.inventory.item.ItemPotion, ItemConsumable {

    // CHECKSTYLE:OFF
    public ItemPotion( short data, int amount ) {
        super( 373, data, amount );
    }

    public ItemPotion( short data, int amount, NBTTagCompound nbt ) {
        super( 373, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.POTION;
    }

    @Override
    public void onConsume( EntityPlayer player ) {
        // Apply effects of the potion
    }

}
