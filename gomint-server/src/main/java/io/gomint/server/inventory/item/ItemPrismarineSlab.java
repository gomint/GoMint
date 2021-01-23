/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockPrismarineSlab;
import io.gomint.world.block.data.PrismarineType;

@RegisterInfo(sId = "minecraft:double_stone_slab2[2]", def = true)
@RegisterInfo(sId = "minecraft:double_stone_slab2[3]")
@RegisterInfo(sId = "minecraft:double_stone_slab2[4]")
public class ItemPrismarineSlab extends ItemStack<io.gomint.inventory.item.ItemPrismarineSlab> implements io.gomint.inventory.item.ItemPrismarineSlab {

    @Override
    public PrismarineType type() {
        switch (this.data()) {
            case 2:
                return PrismarineType.NORMAL;
            case 3:
                return PrismarineType.DARK;
            case 4:
                return PrismarineType.BRICK;
        }

        return PrismarineType.NORMAL;
    }

    @Override
    public io.gomint.inventory.item.ItemPrismarineSlab type(PrismarineType type) {
        switch (type) {
            case NORMAL:
                return this.data((short) 2);
            case DARK:
                return this.data((short) 3);
            case BRICK:
                return this.data((short) 4);
        }

        return this;
    }

    @Override
    public ItemType itemType() {
        return ItemType.PRISMARINE_SLAB;
    }

    @Override
    public Block block() {
        return this.blocks
            .get(BlockPrismarineSlab.class)
            .type(this.type());
    }

}
