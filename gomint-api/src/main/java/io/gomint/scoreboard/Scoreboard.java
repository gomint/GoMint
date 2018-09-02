/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.scoreboard;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Scoreboard {

    /**
     * Add a new display slot to this scoreboard
     *
     * @param slot          which should be shown
     * @param objectiveName which should be used
     * @param displayName   of the slot
     */
    ScoreboardDisplay addDisplay( DisplaySlot slot, String objectiveName, String displayName );

    /**
     * Remove a display
     *
     * @param slot which should be removed
     */
    void removeDisplay( DisplaySlot slot );

}
