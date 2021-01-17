package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:unpowered_comparator" )
public class ItemRedstoneComparatorUnpowered extends ItemStack<ItemRedstoneComparatorUnpowered> {

    @Override
    public ItemType itemType() {
        return ItemType.REDSTONE_COMPARATOR_UNPOWERED;
    }

}
