/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.PrismarineType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:prismarine_stairs")
@RegisterInfo(sId = "minecraft:prismarine_bricks_stairs")
@RegisterInfo(sId = "minecraft:dark_prismarine_stairs")
public class ItemPrismarineStairs extends ItemStack< io.gomint.inventory.item.ItemPrismarineStairs> implements io.gomint.inventory.item.ItemPrismarineStairs {

    @Override
    public ItemType itemType() {
        return ItemType.PRISMARINE_STAIRS;
    }

    @Override
    public PrismarineType type() {
        switch (this.material()) {
            case "minecraft:prismarine_stairs":
                return PrismarineType.NORMAL;
            case "minecraft:prismarine_bricks_stairs":
                return PrismarineType.BRICK;
            case "minecraft:dark_prismarine_stairs":
                return PrismarineType.DARK;
        }

        return PrismarineType.NORMAL;
    }

    @Override
    public io.gomint.inventory.item.ItemPrismarineStairs type(PrismarineType type) {
        switch (type) {
            case NORMAL:
                return this.material("minecraft:prismarine_stairs");
            case DARK:
                return this.material("minecraft:dark_prismarine_stairs");
            case BRICK:
                return this.material("minecraft:prismarine_bricks_stairs");
        }

        return this;
    }

}
