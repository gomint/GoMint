/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.world;

import com.google.common.base.Preconditions;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockSign;
import io.gomint.world.block.BlockStandingSign;
import io.gomint.world.block.BlockWallSign;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class SignChangeTextEvent extends CancellablePlayerEvent {

    private final BlockSign sign;
    private List<String> lines;

    public SignChangeTextEvent(EntityPlayer player, BlockSign sign, List<String> lines) {
        super(player);

        this.sign = sign;
        this.lines = lines;
    }

    /**
     * Get the sign block which should get its text changed. This returned block can be of {@link BlockStandingSign}
     * or {@link BlockWallSign} type.
     *
     * @return the block which should be changed
     * @deprecated Use {@link #getSign()} instead
     */
    @Deprecated
    public Block getBlock() {
        return this.sign;
    }

    /**
     * Get the sign block which should get its text changed. This returned block can be of {@link BlockStandingSign}
     * or {@link BlockWallSign} type.
     *
     * @return the sign block which should be changed
     */
    public BlockSign getSign() {
        return this.sign;
    }

    /**
     * Set a specific line to new content. When you set line 2 and there is no other line
     * line 1 will be empty string
     *
     * @param line    which should be set
     * @param content which should be set on that line
     */
    public void setLine(int line, String content) {
        // Silently fail when line is incorrect
        if (line > 4 || line < 1) {
            return;
        }

        if (this.lines.size() < line) {
            for (int i = 0; i < line - this.lines.size(); i++) {
                this.lines.add("");
            }
        }

        this.lines.set(line - 1, content);
    }

    /**
     * Get a specific line of the sign content
     *
     * @param line which you want to get
     * @return string or null when not set
     */
    public String getLine(int line) {
        // Silently fail when line is incorrect
        if (line > 4 || line < 1) {
            return null;
        }

        if (this.lines.size() < line) {
            return null;
        }

        return this.lines.get(line - 1);
    }

    /**
     * Get all lines which the sign has after the event call
     *
     * @return all lines which the sign has after the event call
     */
    public List<String> getLines() {
        return this.lines;
    }

    /**
     * Set the lines the sign should have after the event call
     *
     * @param lines the lines the sign should have after the event call
     */
    public void setLines(List<String> lines) {
        Preconditions.checkArgument(lines.size() == 4, "Sign must have 4 lines of text");

        this.lines.clear();
        this.lines.addAll(lines);
    }
}
