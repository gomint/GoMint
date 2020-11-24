/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.CancellableEvent;

import java.util.Objects;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class CancellablePlayerEvent extends CancellableEvent {

    private final EntityPlayer player;

    public CancellablePlayerEvent( EntityPlayer player ) {
        this.player = player;
    }

    /**
     * Get the player which is affected by this event
     *
     * @return the player which is affected by this event
     */
    public EntityPlayer getPlayer() {
        return this.player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CancellablePlayerEvent that = (CancellablePlayerEvent) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player);
    }

    @Override
    public String toString() {
        return "CancellablePlayerEvent{" +
            "player=" + player +
            '}';
    }

}
