package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:birch_door", id = 428 )
public class ItemBirchWoodDoor extends ItemStack implements io.gomint.inventory.item.ItemBirchWoodDoor {

    @Override
    public long getBurnTime() {
        return 10000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.BIRCH_DOOR;
    }

}