package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.StoneBrickType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:stonebrick")
public class ItemStoneBrick extends ItemStack< io.gomint.inventory.item.ItemStoneBrick> implements io.gomint.inventory.item.ItemStoneBrick {

    @Override
    public ItemType itemType() {
        return ItemType.STONE_BRICK;
    }

    @Override
    public io.gomint.inventory.item.ItemStoneBrick type(StoneBrickType type) {
        return this.data((short) type.ordinal());
    }

    @Override
    public StoneBrickType type() {
        return StoneBrickType.values()[this.data()];
    }

}
