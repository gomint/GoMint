/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.world;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.world.block.BlockSign;

import java.util.List;

/**
 * @author Kevims
 * @version 1.0
 */
public class SignChangeEvent extends CancellablePlayerEvent {

    private final BlockSign sign;
    private List<String> lines;

    /**
     * Created when a player wants to change the lines of a sign
     *
     * @param player which wants to change the lines
     * @param sign   the sign that is being updated
     * @param lines  the lines that are updated by the player
     */
    public SignChangeEvent( EntityPlayer player, BlockSign sign, List<String> lines ) {
        super( player );
        this.sign = sign;
        this.lines = lines;
    }

    /**
     * Get the lines being updated for the sign
     *
     * @return new ArrayList with the lines as content
     */
    public List<String> getLines() {
        return this.lines;
    }

    /**
     * Updates the lines of this event
     *
     * @param lines the event updated lines
     */
    public void setLines( List<String> lines ) {
        this.lines = lines;
    }

    /**
     * Gets the sign of this event
     *
     * @return the sign which gets the updated lines
     */
    public BlockSign getSign() {
        return this.sign;
    }

}
