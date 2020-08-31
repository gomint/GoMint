package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:clay_ball", id = 337 )
 public class ItemClayBall extends ItemStack implements io.gomint.inventory.item.ItemClayBall {



    @Override
    public ItemType getItemType() {
        return ItemType.CLAY_BALL;
    }

}