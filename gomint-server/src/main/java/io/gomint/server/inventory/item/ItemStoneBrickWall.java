package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockStoneBrickSlab;
import io.gomint.world.block.BlockStoneBrickWall;
import io.gomint.world.block.data.StoneBrickSlabType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:cobblestone_wall[7]", def = true)
@RegisterInfo(sId = "minecraft:cobblestone_wall[1]")
public class ItemStoneBrickWall extends ItemStack<io.gomint.inventory.item.ItemStoneBrickWall> implements io.gomint.inventory.item.ItemStoneBrickWall {

    @Override
    public ItemType itemType() {
        return ItemType.STONE_BRICK_DOUBLE_SLAB;
    }

    @Override
    public io.gomint.inventory.item.ItemStoneBrickWall type(StoneBrickSlabType type) {
        if (type == StoneBrickSlabType.NORMAL) {
            this.material("minecraft:cobblestone_wall");
            return this.data((short) 7);
        }

        this.material("minecraft:cobblestone_wall");
        return this.data((short) 1);
    }

    @Override
    public StoneBrickSlabType type() {
        return this.data() == 7 ? StoneBrickSlabType.NORMAL : StoneBrickSlabType.MOSSY;
    }

    @Override
    public Block block() {
        return this.blocks
            .get(BlockStoneBrickWall.class)
            .type(this.type());
    }

}
