package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 203 )
public class ItemPurpurStairs extends ItemStack implements io.gomint.inventory.item.ItemPurpurStairs {



    @Override
    public String getBlockId() {
        return "minecraft:purpur_stairs";
    }

    @Override
    public ItemType getType() {
        return ItemType.PURPUR_STAIRS;
    }

}
