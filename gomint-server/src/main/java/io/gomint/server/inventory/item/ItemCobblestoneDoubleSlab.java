package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockCobblestoneDoubleSlab;
import io.gomint.world.block.data.CobblestoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:real_double_stone_slab[3]", def = true)
@RegisterInfo(sId = "minecraft:real_double_stone_slab2[5]")
public class ItemCobblestoneDoubleSlab extends ItemStack<io.gomint.inventory.item.ItemCobblestoneDoubleSlab> implements io.gomint.inventory.item.ItemCobblestoneDoubleSlab {

    @Override
    public ItemType itemType() {
        return ItemType.COBBLESTONE_DOUBLE_SLAB;
    }

    @Override
    public io.gomint.inventory.item.ItemCobblestoneDoubleSlab type(CobblestoneType type) {
        if (type == CobblestoneType.NORMAL) {
            this.material("minecraft:double_stone_slab");
            return this.data((short) 3);
        }

        this.material("minecraft:double_stone_slab2");
        return this.data((short) 5);
    }

    @Override
    public CobblestoneType type() {
        if ("minecraft:double_stone_slab2".equals(this.material()) && this.data() == 5) {
            return CobblestoneType.MOSSY;
        }

        return CobblestoneType.NORMAL;
    }

    @Override
    public Block block() {
        return this.blocks
            .get(BlockCobblestoneDoubleSlab.class)
            .type(this.type());
    }

}
