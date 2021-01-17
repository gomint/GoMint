package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:chorus_plant")
public class ItemChorusPlant extends ItemStack< io.gomint.inventory.item.ItemChorusPlant> implements io.gomint.inventory.item.ItemChorusPlant {

    @Override
    public ItemType itemType() {
        return ItemType.CHORUS_PLANT;
    }

}
