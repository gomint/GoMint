package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 5 )
public class ItemWood extends ItemStack implements io.gomint.inventory.item.ItemWood {



    @Override
    public String getBlockId() {
        return "minecraft:planks";
    }

    @Override
    public ItemType getType() {
        return ItemType.WOOD;
    }

}
