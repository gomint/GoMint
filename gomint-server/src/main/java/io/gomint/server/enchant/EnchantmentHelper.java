/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

import io.gomint.inventory.item.ItemType;
import io.gomint.math.Location;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.util.random.FastRandom;
import io.gomint.world.block.BlockType;

public class EnchantmentHelper {

    public static boolean canBeAppliedArmor(ItemStack itemStack) {
        return itemStack.getItemType() == ItemType.CHAIN_HELMET ||
            itemStack.getItemType() == ItemType.DIAMOND_HELMET ||
            itemStack.getItemType() == ItemType.GOLDEN_HELMET ||
            itemStack.getItemType() == ItemType.IRON_HELMET ||
            itemStack.getItemType() == ItemType.LEATHER_HELMET ||
            itemStack.getItemType() == ItemType.NETHERITE_HELMET ||
            itemStack.getItemType() == ItemType.CHAIN_LEGGINGS ||
            itemStack.getItemType() == ItemType.DIAMOND_LEGGINGS ||
            itemStack.getItemType() == ItemType.GOLDEN_LEGGINGS ||
            itemStack.getItemType() == ItemType.IRON_LEGGINGS ||
            itemStack.getItemType() == ItemType.LEATHER_LEGGINGS ||
            itemStack.getItemType() == ItemType.NETHERITE_LEGGINGS ||
            itemStack.getItemType() == ItemType.CHAIN_CHESTPLATE ||
            itemStack.getItemType() == ItemType.DIAMOND_CHESTPLATE ||
            itemStack.getItemType() == ItemType.GOLDEN_CHESTPLATE ||
            itemStack.getItemType() == ItemType.IRON_CHESTPLATE ||
            itemStack.getItemType() == ItemType.LEATHER_CHESTPLATE ||
            itemStack.getItemType() == ItemType.NETHERITE_CHESTPLATE ||
            itemStack.getItemType() == ItemType.CHAIN_BOOTS ||
            itemStack.getItemType() == ItemType.DIAMOND_BOOTS ||
            itemStack.getItemType() == ItemType.GOLDEN_BOOTS ||
            itemStack.getItemType() == ItemType.IRON_BOOTS ||
            itemStack.getItemType() == ItemType.LEATHER_BOOTS ||
            itemStack.getItemType() == ItemType.NETHERITE_BOOTS;
    }

    public static boolean canBeAppliedToAxe(ItemStack itemStack) {
        return itemStack.getItemType() == ItemType.DIAMOND_AXE ||
            itemStack.getItemType() == ItemType.STONE_AXE ||
            itemStack.getItemType() == ItemType.GOLDEN_AXE ||
            itemStack.getItemType() == ItemType.IRON_AXE ||
            itemStack.getItemType() == ItemType.WOODEN_AXE ||
            itemStack.getItemType() == ItemType.NETHERITE_AXE;
    }

    public static boolean canBeAppliedToTools(ItemStack itemStack) {
        return canBeAppliedToAxe(itemStack) ||
            itemStack.getItemType() == ItemType.DIAMOND_PICKAXE ||
            itemStack.getItemType() == ItemType.STONE_PICKAXE ||
            itemStack.getItemType() == ItemType.GOLDEN_PICKAXE ||
            itemStack.getItemType() == ItemType.IRON_PICKAXE ||
            itemStack.getItemType() == ItemType.WOODEN_PICKAXE ||
            itemStack.getItemType() == ItemType.NETHERITE_PICKAXE ||
            itemStack.getItemType() == ItemType.DIAMOND_SHOVEL ||
            itemStack.getItemType() == ItemType.STONE_SHOVEL ||
            itemStack.getItemType() == ItemType.GOLDEN_SHOVEL ||
            itemStack.getItemType() == ItemType.IRON_SHOVEL ||
            itemStack.getItemType() == ItemType.WOODEN_SHOVEL ||
            itemStack.getItemType() == ItemType.NETHERITE_SHOVEL;
    }

    public static boolean canBeAppliedToBoots(ItemStack itemStack) {
        return itemStack.getItemType() == ItemType.CHAIN_BOOTS ||
            itemStack.getItemType() == ItemType.DIAMOND_BOOTS ||
            itemStack.getItemType() == ItemType.GOLDEN_BOOTS ||
            itemStack.getItemType() == ItemType.IRON_BOOTS ||
            itemStack.getItemType() == ItemType.LEATHER_BOOTS ||
            itemStack.getItemType() == ItemType.NETHERITE_BOOTS;
    }

    public static int findBookshelves(Location location) {
        int foundShelves = 0;

        for (int z = -1; z <= 1; ++z) {
            for (int x = -1; x <= 1; ++x) {
                if ((z != 0 || x != 0) &&
                    location.add(x, 0, z).getBlock().getBlockType() == BlockType.AIR &&
                    location.add(x, 1, z).getBlock().getBlockType() == BlockType.AIR) {

                    if (location.add(x * 2, 0, z * 2).getBlock().getBlockType() == BlockType.BOOKSHELF) {
                        foundShelves++;
                    }

                    if (location.add(x * 2, 1, z * 2).getBlock().getBlockType() == BlockType.BOOKSHELF) {
                        foundShelves++;
                    }

                    if (x != 0 && z != 0) {
                        if (location.add(x * 2, 0, z).getBlock().getBlockType() == BlockType.BOOKSHELF) {
                            foundShelves++;
                        }

                        if (location.add(x * 2, 1, z).getBlock().getBlockType() == BlockType.BOOKSHELF) {
                            foundShelves++;
                        }

                        if (location.add(x, 0, z * 2).getBlock().getBlockType() == BlockType.BOOKSHELF) {
                            foundShelves++;
                        }

                        if (location.add(x, 1, z * 2).getBlock().getBlockType() == BlockType.BOOKSHELF) {
                            foundShelves++;
                        }
                    }

                    // We cap at 15 shelves
                    if (foundShelves >= 15) {
                        return foundShelves;
                    }
                }
            }
        }

        return foundShelves;
    }

    public static boolean canBeAppliedToSwords(ItemStack itemStack) {
        return itemStack.getItemType() == ItemType.DIAMOND_SWORD ||
            itemStack.getItemType() == ItemType.STONE_SWORD ||
            itemStack.getItemType() == ItemType.GOLDEN_SWORD ||
            itemStack.getItemType() == ItemType.IRON_SWORD ||
            itemStack.getItemType() == ItemType.WOODEN_SWORD ||
            itemStack.getItemType() == ItemType.NETHERITE_SWORD;
    }

}
