package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:lit_pumpkin")
public class ItemJackOLantern extends ItemStack< io.gomint.inventory.item.ItemJackOLantern> implements io.gomint.inventory.item.ItemJackOLantern {

    @Override
    public ItemType itemType() {
        return ItemType.JACK_O_LANTERN;
    }

}
