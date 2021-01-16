/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory.item;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemFood<I> extends ItemStack<I> {

    /**
     * Get the amount of saturation this item regenerates
     *
     * @return amount of saturation regeneration
     */
    float getSaturation();

    /**
     * Get the amount of hunger this item regenerates
     *
     * @return amount of hunger regeneration
     */
    float getHunger();

}
