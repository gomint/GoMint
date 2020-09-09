package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.inventory.item.data.SandType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockSand;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:sand", id = 12)
public class ItemSand extends ItemStack implements io.gomint.inventory.item.ItemSand {

    @Override
    public ItemType getItemType() {
        return ItemType.SAND;
    }

    @Override
    public void setType(SandType type) {
        this.setData((short) type.ordinal());
    }

    @Override
    public SandType getType() {
        return SandType.values()[this.getData()];
    }

    @Override
    public Block getBlock() {
        BlockSand block = (BlockSand) super.getBlock();
        block.setType(this.getType());
        return block;
    }

}
