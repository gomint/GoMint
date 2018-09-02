/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.scoreboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author geNAZt
 * @version 1.0
 */
@RequiredArgsConstructor
public class DisplayEntry implements io.gomint.scoreboard.DisplayEntry {

    private final Scoreboard scoreboard;
    @Getter private final long scoreId;

    @Override
    public void setScore( int score ) {
        this.scoreboard.updateScore( this.scoreId, score );
    }

    @Override
    public int getScore() {
        return this.scoreboard.getScore( this.scoreId );
    }

}
