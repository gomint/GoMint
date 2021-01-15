/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.util.Pair;
import io.gomint.server.util.random.WeightedRandom;
import io.gomint.util.random.FastRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EnchantmentSelector {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnchantmentSelector.class);

    public static Pair<int[], List<List<Enchantment>>> determineAvailable(Enchantments enchantments, FastRandom random, Location blockLocation, ItemStack<?> toEnchant) {
        // Check if the item can be enchanted at all
        if (toEnchant.enchantAbility() == 0 || toEnchant.isEnchanted()) {
            return null;
        }

        // Check for bookshelves
        int bookShelvesAround = EnchantmentHelper.findBookshelves(blockLocation);

        LOGGER.debug("Bookshelves found: {}", bookShelvesAround);

        int base = random.nextInt(8) + 1 +
            (bookShelvesAround >> 1) +
            random.nextInt(bookShelvesAround + 1);

        LOGGER.debug("Base: {}", base);

        int upperSlot = Math.max(base / 3, 1);
        int middleSlot = base * 2 / 3 + 1;
        int lowerSlot = Math.max(base, bookShelvesAround * 2);

        LOGGER.debug("Upper, middle and lower slots: {}, {}, {}", upperSlot, middleSlot, lowerSlot);

        // Build together enchantment lists per slot
        List<List<Enchantment>> selectedEnchantments = new ArrayList<>();
        selectedEnchantments.add(buildEnchantments(enchantments, random, toEnchant, upperSlot));
        selectedEnchantments.add(buildEnchantments(enchantments, random, toEnchant, middleSlot));
        selectedEnchantments.add(buildEnchantments(enchantments, random, toEnchant, lowerSlot));
        return new Pair<>(new int[]{upperSlot, middleSlot, lowerSlot}, selectedEnchantments);
    }

    private static List<Enchantment> buildEnchantments(Enchantments enchantments, FastRandom random, ItemStack<?> toEnchant, int level) {
        int useLevel = level + 1 +
            random.nextInt(toEnchant.enchantAbility() / 4 + 1) +
            random.nextInt(toEnchant.enchantAbility() / 4 + 1);

        float f = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;

        useLevel = MathUtils.clamp(Math.round((float) useLevel + (float) useLevel * f), 1, Integer.MAX_VALUE);

        WeightedRandom<Enchantment> weightedRandom = new WeightedRandom<>(random);
        for (Enchantment enchantment : enchantments.all()) {
            if (enchantment.canBeApplied(toEnchant)) {
                for (short i = enchantment.maxLevel(); i > enchantment.minLevel() - 1; --i) {
                    if (useLevel >= enchantment.minEnchantAbility(i) && useLevel <= enchantment.maxEnchantAbility(i)) {
                        weightedRandom.add(enchantment.rarity().weight(), enchantments.create(enchantment.getClass(), i));
                        break;
                    }
                }
            }
        }

        List<Enchantment> selected = new ArrayList<>();
        if (!weightedRandom.isEmpty()) {
            selected.add(weightedRandom.next());

            while (random.nextInt(50) <= useLevel) {
                Enchantment lastItem = selected.get(selected.size() - 1);
                removeIncompatible(weightedRandom.iterator(), lastItem);

                if (weightedRandom.isEmpty()) {
                    break;
                }

                selected.add(weightedRandom.next());
                useLevel /= 2;
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Selected enchants: ");
                for (Enchantment enchantment : selected) {
                    LOGGER.debug(" > {}: {}", enchantment.getClass().getSimpleName(), enchantment.level() );
                }
            }
        }

        return selected;
    }

    private static void removeIncompatible(Iterator<Enchantment> iterator, Enchantment lastItem) {
        while (iterator.hasNext()) {
            Enchantment enchantment = iterator.next();
            if (lastItem.collidesWith(enchantment) || enchantment.collidesWith(lastItem)) {
                iterator.remove();
            }
        }
    }

}
