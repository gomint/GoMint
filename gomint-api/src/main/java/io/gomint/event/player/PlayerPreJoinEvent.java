/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.player;

import io.gomint.entity.EntityPlayer;

import java.util.Objects;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 *
 * This event gets called when the login has been completed and the server decides to start loading world data
 * into the client. This event can be used when you need to be sure the play status has been reached (like redirecting
 * a player to another server without ever touching this one)
 */
public class PlayerPreJoinEvent extends CancellablePlayerEvent<PlayerPreJoinEvent> {

    private String kickReason;

    public PlayerPreJoinEvent( EntityPlayer player ) {
        super( player );
    }

    /**
     * Set the reason which will be used to disconnect the player when this event has been cancelled
     *
     * @param kickReason which is used to kick the player
     */
    public PlayerPreJoinEvent kickReason(String kickReason ) {
        this.kickReason = kickReason;
        return this;
    }

    /**
     * Get the reason which the player will get then this event has been cancelled
     *
     * @return reason for kick
     */
    public String kickReason() {
        return kickReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayerPreJoinEvent that = (PlayerPreJoinEvent) o;
        return Objects.equals(kickReason, that.kickReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), kickReason);
    }

    @Override
    public String toString() {
        return "PlayerPreJoinEvent{" +
            "kickReason='" + kickReason + '\'' +
            '}';
    }

}
