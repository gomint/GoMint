package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 428 )
public class ItemBirchWoodDoor extends ItemStack implements io.gomint.inventory.item.ItemBirchWoodDoor {

    // CHECKSTYLE:OFF
    public ItemBirchWoodDoor( short data, int amount ) {
        super( 428, data, amount );
    }

    public ItemBirchWoodDoor( short data, int amount, NBTTagCompound nbt ) {
        super( 428, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BIRCH_DOOR;
    }

}
