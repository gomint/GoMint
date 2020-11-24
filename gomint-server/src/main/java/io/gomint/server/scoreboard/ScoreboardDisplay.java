/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.scoreboard;

import io.gomint.entity.Entity;
import io.gomint.scoreboard.DisplayEntry;
import io.gomint.scoreboard.SortOrder;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ScoreboardDisplay implements io.gomint.scoreboard.ScoreboardDisplay {

    private final Scoreboard scoreboard;
    private final String objectiveName;
    private String displayName;
    private SortOrder sortOrder;

    public ScoreboardDisplay(Scoreboard scoreboard, String objectiveName, String displayName, SortOrder sortOrder) {
        this.scoreboard = scoreboard;
        this.objectiveName = objectiveName;
        this.displayName = displayName;
        this.sortOrder = sortOrder;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public String getObjectiveName() {
        return objectiveName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public SortOrder getSortOrder() {
        return sortOrder;
    }

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
