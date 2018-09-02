/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.scoreboard;

import io.gomint.entity.Entity;
import io.gomint.scoreboard.DisplayEntry;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
@Data
public class ScoreboardDisplay implements io.gomint.scoreboard.ScoreboardDisplay {

    private final Scoreboard scoreboard;
    private final String objectiveName;
    private String displayName;

    @Override
    public DisplayEntry addEntity( Entity entity, int score ) {
        long scoreId = this.scoreboard.addOrUpdateEntity( (io.gomint.server.entity.Entity) entity, this.objectiveName, score );
        return new io.gomint.server.scoreboard.DisplayEntry( this.scoreboard, scoreId );
    }

    @Override
    public DisplayEntry addLine( String line, int score ) {
        long scoreId = this.scoreboard.addOrUpdateLine( line, this.objectiveName, score );
        return new io.gomint.server.scoreboard.DisplayEntry( this.scoreboard, scoreId );
    }

    @Override
    public void removeEntry( DisplayEntry entry ) {
        this.scoreboard.removeScoreEntry( ( (io.gomint.server.scoreboard.DisplayEntry) entry ).getScoreId() );
    }

}
