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
import io.gomint.world.block.BlockType;

public class EnchantmentHelper {

    public static boolean canBeAppliedArmor(ItemStack<?> itemStack) {
        return itemStack.itemType() == ItemType.CHAIN_HELMET ||
            itemStack.itemType() == ItemType.DIAMOND_HELMET ||
            itemStack.itemType() == ItemType.GOLDEN_HELMET ||
            itemStack.itemType() == ItemType.IRON_HELMET ||
            itemStack.itemType() == ItemType.LEATHER_HELMET ||
            itemStack.itemType() == ItemType.NETHERITE_HELMET ||
            itemStack.itemType() == ItemType.CHAIN_LEGGINGS ||
            itemStack.itemType() == ItemType.DIAMOND_LEGGINGS ||
            itemStack.itemType() == ItemType.GOLDEN_LEGGINGS ||
            itemStack.itemType() == ItemType.IRON_LEGGINGS ||
            itemStack.itemType() == ItemType.LEATHER_LEGGINGS ||
            itemStack.itemType() == ItemType.NETHERITE_LEGGINGS ||
            itemStack.itemType() == ItemType.CHAIN_CHESTPLATE ||
            itemStack.itemType() == ItemType.DIAMOND_CHESTPLATE ||
            itemStack.itemType() == ItemType.GOLDEN_CHESTPLATE ||
            itemStack.itemType() == ItemType.IRON_CHESTPLATE ||
            itemStack.itemType() == ItemType.LEATHER_CHESTPLATE ||
            itemStack.itemType() == ItemType.NETHERITE_CHESTPLATE ||
            itemStack.itemType() == ItemType.CHAIN_BOOTS ||
            itemStack.itemType() == ItemType.DIAMOND_BOOTS ||
            itemStack.itemType() == ItemType.GOLDEN_BOOTS ||
            itemStack.itemType() == ItemType.IRON_BOOTS ||
            itemStack.itemType() == ItemType.LEATHER_BOOTS ||
            itemStack.itemType() == ItemType.NETHERITE_BOOTS;
    }

    public static boolean canBeAppliedToAxe(ItemStack<?> itemStack) {
        return itemStack.itemType() == ItemType.DIAMOND_AXE ||
            itemStack.itemType() == ItemType.STONE_AXE ||
            itemStack.itemType() == ItemType.GOLDEN_AXE ||
            itemStack.itemType() == ItemType.IRON_AXE ||
            itemStack.itemType() == ItemType.WOODEN_AXE ||
            itemStack.itemType() == ItemType.NETHERITE_AXE;
    }

    public static boolean canBeAppliedToTools(ItemStack<?> itemStack) {
        return canBeAppliedToAxe(itemStack) ||
            itemStack.itemType() == ItemType.DIAMOND_PICKAXE ||
            itemStack.itemType() == ItemType.STONE_PICKAXE ||
            itemStack.itemType() == ItemType.GOLDEN_PICKAXE ||
            itemStack.itemType() == ItemType.IRON_PICKAXE ||
            itemStack.itemType() == ItemType.WOODEN_PICKAXE ||
            itemStack.itemType() == ItemType.NETHERITE_PICKAXE ||
            itemStack.itemType() == ItemType.DIAMOND_SHOVEL ||
            itemStack.itemType() == ItemType.STONE_SHOVEL ||
            itemStack.itemType() == ItemType.GOLDEN_SHOVEL ||
            itemStack.itemType() == ItemType.IRON_SHOVEL ||
            itemStack.itemType() == ItemType.WOODEN_SHOVEL ||
            itemStack.itemType() == ItemType.NETHERITE_SHOVEL;
    }

    public static boolean canBeAppliedToBoots(ItemStack<?> itemStack) {
        return itemStack.itemType() == ItemType.CHAIN_BOOTS ||
            itemStack.itemType() == ItemType.DIAMOND_BOOTS ||
            itemStack.itemType() == ItemType.GOLDEN_BOOTS ||
            itemStack.itemType() == ItemType.IRON_BOOTS ||
            itemStack.itemType() == ItemType.LEATHER_BOOTS ||
            itemStack.itemType() == ItemType.NETHERITE_BOOTS;
    }

    public static int findBookshelves(Location location) {
        int foundShelves = 0;

        for (int z = -1; z <= 1; ++z) {
            for (int x = -1; x <= 1; ++x) {
                if ((z != 0 || x != 0) &&
                    location.add(x, 0, z).block().blockType() == BlockType.AIR &&
                    location.add(x, 1, z).block().blockType() == BlockType.AIR) {

                    if (location.add(x * 2, 0, z * 2).block().blockType() == BlockType.BOOKSHELF) {
                        foundShelves++;
                    }

                    if (location.add(x * 2, 1, z * 2).block().blockType() == BlockType.BOOKSHELF) {
                        foundShelves++;
                    }

                    if (x != 0 && z != 0) {
                        if (location.add(x * 2, 0, z).block().blockType() == BlockType.BOOKSHELF) {
                            foundShelves++;
                        }

                        if (location.add(x * 2, 1, z).block().blockType() == BlockType.BOOKSHELF) {
                            foundShelves++;
                        }

                        if (location.add(x, 0, z * 2).block().blockType() == BlockType.BOOKSHELF) {
                            foundShelves++;
                        }

                        if (location.add(x, 1, z * 2).block().blockType() == BlockType.BOOKSHELF) {
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

    public static boolean canBeAppliedToSwords(ItemStack<?> itemStack) {
        return itemStack.itemType() == ItemType.DIAMOND_SWORD ||
            itemStack.itemType() == ItemType.STONE_SWORD ||
            itemStack.itemType() == ItemType.GOLDEN_SWORD ||
            itemStack.itemType() == ItemType.IRON_SWORD ||
            itemStack.itemType() == ItemType.WOODEN_SWORD ||
            itemStack.itemType() == ItemType.NETHERITE_SWORD;
    }

}
