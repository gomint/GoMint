package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 294 )
 public class ItemGoldenHoe extends ItemReduceTierGolden implements io.gomint.inventory.item.ItemGoldenHoe {

    // CHECKSTYLE:OFF
    public ItemGoldenHoe( short data, int amount ) {
        super( 294, data, amount );
    }

    public ItemGoldenHoe( short data, int amount, NBTTagCompound nbt ) {
        super( 294, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.GOLDEN_HOE;
    }

}