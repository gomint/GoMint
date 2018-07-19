package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.inventory.item.annotation.UseDataAsDamage;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@UseDataAsDamage
@RegisterInfo( id = 259 )
public class ItemFlintAndSteel extends ItemStack implements io.gomint.inventory.item.ItemFlintAndSteel {

    // CHECKSTYLE:OFF
    public ItemFlintAndSteel( short data, int amount ) {
        super( 259, data, amount );
    }

    public ItemFlintAndSteel( short data, int amount, NBTTagCompound nbt ) {
        super( 259, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public short getMaxDamage() {
        return 64;
    }

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public int getBlockId() {
        return 51;
    }

    @Override
    public ItemType getType() {
        return ItemType.FLINT_AND_STEEL;
    }

}
