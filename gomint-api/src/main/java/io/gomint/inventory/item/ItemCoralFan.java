/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.CoralType;
import io.gomint.world.block.data.RotationDirection;

/**
 * @author Kaooot
 * @version 1.0
 * @stability 3
 */
public interface ItemCoralFan extends ItemStack<ItemCoralFan> {

    /**
     * Creates a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemCoralFan create( int amount ) {
        return GoMint.instance().createItemStack( ItemCoralFan.class, amount );
    }

    /**
     * Get the direction of this coral fan
     *
     * @return direction of the coral
     */
    RotationDirection direction();

    /**
     * Set the direction of this coral fan
     *
     * @param direction in which this coral fan should face
     */
    ItemCoralFan direction(RotationDirection direction);

    /**
     * Set coral type
     *
     * @param type of coral
     */
    ItemCoralFan coralType(CoralType type);

    /**
     * Get type of coral
     *
     * @return type of coral
     */
    CoralType coralType();

    /**
     * Is this coral fan dead?
     *
     * @return true when dead, false otherwise
     */
    boolean dead();

    /**
     * Set if this coral fan is dead or not
     *
     * @param dead true when it should be dead, false otherwise
     */
    ItemCoralFan dead(boolean dead);

}
