package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 93 )
public class ItemRedstoneRepeaterInactive extends ItemStack {



    @Override
    public String getBlockId() {
        return "minecraft:unpowered_repeater";
    }

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_REPEATER_INACTIVE;
    }

}
