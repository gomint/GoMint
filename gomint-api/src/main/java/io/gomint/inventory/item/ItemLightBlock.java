/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory.item;

import io.gomint.GoMint;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 1
 */
public interface ItemLightBlock extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemLightBlock create( int amount ) {
        return GoMint.instance().createItemStack( ItemLightBlock.class, amount );
    }

    /**
     * Get light intensity, 0 for off, 1 for 100%
     *
     * @return 0 to 1
     */
    float getIntensity();

    /**
     * Set the intensity of light
     *
     * @param intensity ranging from 0 to 1
     */
    void setIntensity(float intensity);

}
