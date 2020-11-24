/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.Entity;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityXPOrb extends Entity {

    /**
     * Create a new entity xp orb with no config
     *
     * @return empty, fresh xp orb
     */
    static EntityXPOrb create() {
        return GoMint.instance().createEntity( EntityXPOrb.class );
    }

    /**
     * Set a new pickup delay
     *
     * @param duration the amount of timeUnit to wait
     * @param timeUnit the unit of time to wait
     */
    void setPickupDelay( long duration, TimeUnit timeUnit );

    /**
     * Get the time when the item drop is allowed to be picked up
     *
     * @return the unix timestamp in millis when the item drop can be picked up
     */
    long getPickupTime();

    /**
     * Set new xp amount
     *
     * @param xpAmount which should be used when collected
     */
    void setXpAmount( int xpAmount );

    /**
     * Amount of XP currently in this orb
     *
     * @return xp in this orb
     */
    int getXpAmount();

}
