/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity;

import io.gomint.inventory.Inventory;
import io.gomint.math.AxisAlignedBB;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.world.World;

/**
 * @author BlackyPaw
 * @author geNAZt
 * @version 1.0
 */
public interface Entity {

    /**
     * Gets the entity's unqiue identifier.
     *
     * @return The entity's unique identifier
     */
    long getEntityId();

    /**
     * Gets the world the entity resides in.
     *
     * @return The world the entity resides in
     */
    World getWorld();

    /**
     * Gets the location of the entity.
     *
     * @return The entity's location
     */
    Location getLocation();

    void setVelocity( Vector velocity );

    void setNameTagAlwaysVisible( boolean value );

    boolean isNameTagAlwaysVisible();

    void setNameTagVisible( boolean value );

    boolean isNameTagVisible();

    AxisAlignedBB getBoundingBox();

    boolean isOnGround();

}
