package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 458 )
public class ItemBeetrootSeeds extends ItemStack implements io.gomint.inventory.item.ItemBeetrootSeeds {

    // CHECKSTYLE:OFF
    public ItemBeetrootSeeds( short data, int amount ) {
        super( 458, data, amount );
    }

    public ItemBeetrootSeeds( short data, int amount, NBTTagCompound nbt ) {
        super( 458, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public int getBlockId() {
        return 244;
    }

    @Override
    public ItemType getType() {
        return ItemType.BEETROOT_SEEDS;
    }

}
