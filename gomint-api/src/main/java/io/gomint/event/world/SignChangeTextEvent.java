/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.world;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockSign;
import io.gomint.world.block.BlockWallSign;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SignChangeTextEvent extends CancellablePlayerEvent {

    private final Block block;
    private final List<String> lines;

    public SignChangeTextEvent( EntityPlayer player, Block block, List<String> lines ) {
        super( player );

        this.block = block;
        this.lines = lines;
    }

    /**
     * Get the sign block which should get its text changed. This returned block can be of {@link BlockSign}
     * or {@link BlockWallSign} type.
     *
     * @return the block which should be changed
     */
    public Block getBlock() {
        return this.block;
    }

    /**
     * Set a specific line to new content. When you set line 2 and there is no other line
     * line 1 will be empty string
     *
     * @param line    which should be set
     * @param content which should be set on that line
     */
    public void setLine( int line, String content ) {
        // Silently fail when line is incorrect
        if ( line > 4 || line < 1 ) {
            return;
        }

        if ( this.lines.size() < line ) {
            for ( int i = 0; i < line - this.lines.size(); i++ ) {
                this.lines.add( "" );
            }
        }

        this.lines.set( line - 1, content );
    }

    /**
     * Get a specific line of the sign content
     *
     * @param line which you want to get
     * @return string or null when not setö
     */
    public String getLine( int line ) {
        // Silently fail when line is incorrect
        if ( line > 4 || line < 1 ) {
            return null;
        }

        if ( this.lines.size() < line ) {
            return null;
        }

        return this.lines.get( line - 1 );
    }

}
