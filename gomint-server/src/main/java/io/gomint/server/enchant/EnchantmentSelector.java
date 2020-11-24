/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

import io.gomint.inventory.item.ItemEnchantable;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.world.block.EnchantmentTable;

public class EnchantmentSelector {

    public void select(EntityPlayer player, EnchantmentTable table, ItemStack itemStack) {
        // Check if item is enchantable
        if (!(itemStack instanceof ItemEnchantable)) {

        }

        // Get bookshelf surrounding

    }

    private int[] getCosts(ItemStack itemStack, int amountOfBookShelves) {
        return new int[0];
    }

}
