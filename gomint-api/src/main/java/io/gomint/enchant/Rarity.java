/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.enchant;

/**
 * @author geNAZt
 * <p>
 * Rarity values for the weighted random in enchantment selection
 */
public enum Rarity {

    COMMON(10),
    UNCOMMON(5),
    RARE(2),
    VERY_RARE(1);

    private final int weight;

    Rarity(int rarityWeight) {
        this.weight = rarityWeight;
    }

    public int weight() {
        return this.weight;
    }

}
