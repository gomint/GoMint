package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:lit_pumpkin", id = 91)
public class ItemJackOLantern extends ItemStack implements io.gomint.inventory.item.ItemJackOLantern {

    @Override
    public ItemType getItemType() {
        return ItemType.JACK_O_LANTERN;
    }

}
