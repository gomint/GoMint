package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:netherbrick" )
 public class ItemNetherBrick extends ItemStack< io.gomint.inventory.item.ItemNetherBrick> implements io.gomint.inventory.item.ItemNetherBrick {



    @Override
    public ItemType itemType() {
        return ItemType.NETHER_BRICK;
    }

}
