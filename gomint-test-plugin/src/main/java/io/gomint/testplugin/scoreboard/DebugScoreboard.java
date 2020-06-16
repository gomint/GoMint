/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.testplugin.scoreboard;

import io.gomint.ChatColor;
import io.gomint.GoMint;
import io.gomint.entity.EntityPlayer;
import io.gomint.testplugin.TestPlugin;
import io.gomint.scoreboard.DisplayEntry;
import io.gomint.scoreboard.DisplaySlot;
import io.gomint.scoreboard.Scoreboard;
import io.gomint.scoreboard.ScoreboardDisplay;
import io.gomint.world.Chunk;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public class DebugScoreboard {

    private final ScoreboardDisplay display;
    private final EntityPlayer player;

    private DisplayEntry tpsEntry;
    private DisplayEntry chunkEntry;

    // Performance caches
    private final NumberFormat format;
    private double oldTPS = 0;
    private int chunkX;
    private int chunkZ;

    public DebugScoreboard( TestPlugin plugin, EntityPlayer player ) {
        this.player = player;

        Scoreboard scoreboard = GoMint.instance().createScoreboard();
        this.display = scoreboard.addDisplay( DisplaySlot.SIDEBAR, "debug", ChatColor.GREEN + "Go" + ChatColor.GRAY + "Mint" );

        this.display.addLine( " ", 0 );
        this.display.addLine( ChatColor.GOLD + "TPS     ", 1 );
        this.tpsEntry = this.display.addLine( ChatColor.RED + "0.00", 2 );

        this.display.addLine( "  ", 3 );
        this.chunkEntry = this.display.addLine( "0 / 0", 4 );

        // Performance things
        this.format = NumberFormat.getNumberInstance();
        this.format.setMaximumFractionDigits( 2 );
        this.format.setMinimumFractionDigits( 2 );

        // Schedule updated for this scoreboard
        plugin.getScheduler().schedule( this::update, 1, 1, TimeUnit.MILLISECONDS );

        // Add player to scoreboard
        player.setScoreboard( scoreboard );
    }

    private void update() {
        this.updateTPS();
        this.updateChunk();
    }

    private void updateChunk() {
        Chunk chunk = this.player.getChunk();
        if ( chunk.getX() != this.chunkX || chunk.getZ() != this.chunkZ ) {
            // Remove the old entry
            this.display.removeEntry( this.chunkEntry );
            this.chunkEntry = this.display.addLine( chunk.getX() + " / " + chunk.getZ(), 4 );

            // Update cache
            this.chunkZ = chunk.getZ();
            this.chunkX = chunk.getX();
        }
    }

    private void updateTPS() {
        if ( this.oldTPS != GoMint.instance().getTPS() ) {
            // Remove the old entry
            this.display.removeEntry( this.tpsEntry );
            this.tpsEntry = this.display.addLine( ChatColor.GREEN + this.format.format( GoMint.instance().getTPS() ), 2 );

            // Update cache
            this.oldTPS = GoMint.instance().getTPS();
        }
    }

}
