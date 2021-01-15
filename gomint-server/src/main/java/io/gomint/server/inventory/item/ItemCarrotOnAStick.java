package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:carrot_on_a_stick", id = 398)
public class ItemCarrotOnAStick extends ItemStack< io.gomint.inventory.item.ItemCarrotOnAStick> implements io.gomint.inventory.item.ItemCarrotOnAStick {

    @Override
    public ItemType itemType() {
        return ItemType.CARROT_ON_A_STICK;
    }

}
