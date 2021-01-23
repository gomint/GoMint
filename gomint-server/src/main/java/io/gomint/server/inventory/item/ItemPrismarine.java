package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockPrismarine;
import io.gomint.world.block.data.PrismarineType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:prismarine")
public class ItemPrismarine extends ItemStack< io.gomint.inventory.item.ItemPrismarine> implements io.gomint.inventory.item.ItemPrismarine {

    @Override
    public ItemType itemType() {
        return ItemType.PRISMARINE;
    }

    @Override
    public io.gomint.inventory.item.ItemPrismarine type(PrismarineType type) {
        switch (type) {
            case NORMAL:
                return this.data((short) 0);
            case DARK:
                return this.data((short) 1);
        }

        return this.data((short) 2);
    }

    @Override
    public PrismarineType type() {
        switch (this.data()) {
            case 1:
                return PrismarineType.DARK;
            case 2:
                return PrismarineType.BRICK;
            default:
                return PrismarineType.NORMAL;
        }
    }

    @Override
    public Block block() {
        BlockPrismarine block = (BlockPrismarine) super.block();
        return block.type(this.type());
    }

}
