package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:clay_ball" )
 public class ItemClayBall extends ItemStack< io.gomint.inventory.item.ItemClayBall> implements io.gomint.inventory.item.ItemClayBall {



    @Override
    public ItemType itemType() {
        return ItemType.CLAY_BALL;
    }

}
