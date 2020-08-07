package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.inventory.item.annotation.CanBeDamaged;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@CanBeDamaged
@RegisterInfo( sId = "minecraft:fire", id = 259 )
public class ItemFlintAndSteel extends ItemStack implements io.gomint.inventory.item.ItemFlintAndSteel {

    @Override
    public short getMaxDamage() {
        return 64;
    }

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.FLINT_AND_STEEL;
    }

}
