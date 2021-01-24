package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockStoneBrickSlab;
import io.gomint.world.block.data.StoneBrickSlabType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:double_stone_slab[5]", def = true)
@RegisterInfo(sId = "minecraft:double_stone_slab4[0]")
public class ItemStoneBrickSlab extends ItemSlab<io.gomint.inventory.item.ItemStoneBrickSlab> implements io.gomint.inventory.item.ItemStoneBrickSlab {

    @Override
    public ItemType itemType() {
        return ItemType.STONE_BRICK_SLAB;
    }

    @Override
    public io.gomint.inventory.item.ItemStoneBrickSlab type(StoneBrickSlabType type) {
        if (type == StoneBrickSlabType.NORMAL) {
            this.material("minecraft:double_stone_slab");
            return this.data((short) 5);
        }

        this.material("minecraft:double_stone_slab4");
        return this.data((short) 0);
    }

    @Override
    public StoneBrickSlabType type() {
        return this.data() == 5 ? StoneBrickSlabType.NORMAL : StoneBrickSlabType.MOSSY;
    }

    @Override
    public Block block() {
        return this.blocks
            .get(BlockStoneBrickSlab.class)
            .type(this.type());
    }

}
