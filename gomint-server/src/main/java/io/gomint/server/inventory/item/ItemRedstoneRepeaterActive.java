package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 94 )
public class ItemRedstoneRepeaterActive extends ItemStack {



    @Override
    public String getBlockId() {
        return "minecraft:powered_repeater";
    }

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_REPEATER_ACTIVE;
    }

}
