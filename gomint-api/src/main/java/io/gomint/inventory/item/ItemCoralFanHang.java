/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.CoralType;
import io.gomint.world.block.data.Direction;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemCoralFanHang extends ItemStack<ItemCoralFanHang> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemCoralFanHang create( int amount ) {
        return GoMint.instance().createItemStack( ItemCoralFanHang.class, amount );
    }

    /**
     * Set coral type
     *
     * @param type of coral
     */
    ItemCoralFanHang coralType(CoralType type);

    /**
     * Get type of coral
     *
     * @return type of coral
     */
    CoralType coralType();

    /**
     * Direction of the coral fan
     *
     * @param direction which this rail should be oriented by
     */
    ItemCoralFanHang direction(Direction direction);

    /**
     * Get direction of the coral fan
     *
     * @return direction of this coral fan
     */
    Direction direction();

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
    ItemCoralFanHang dead(boolean dead);

}
