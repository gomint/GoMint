/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import com.google.common.base.Joiner;
import io.gomint.GoMint;
import io.gomint.event.world.SignChangeTextEvent;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.plugin.EventCaller;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.block.Sign;
import io.gomint.taglib.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "Sign")
public class SignTileEntity extends TileEntity {

    private static final Joiner CONTENT_JOINER = Joiner.on("\n").skipNulls();

    private final List<String> lines = new ArrayList<>(4);

    /**
     * Construct new tile entity from position and world data
     *
     * @param block which created this tile
     */
    public SignTileEntity(Block block, Items items) {
        super(block, items);
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);

        if (compound.containsKey("Text")) {
            String text = compound.getString("Text", "");
            this.lines.addAll(Arrays.asList(text.split("\n")));
        }
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);

        compound.addValue("id", "Sign");
        compound.addValue("Text", CONTENT_JOINER.join(this.lines));
    }

    @Override
    public Sign<?> getBlock() {
        return (Sign<?>) super.getBlock();
    }

    /**
     * Get the lines of this sign
     *
     * @return the lines of this sign
     */
    public List<String> getLines() {
        return this.lines;
    }

    @Override
    public void applyClientData(EntityPlayer player, NBTTagCompound compound) throws Exception {
        // We only care about the text attribute
        String text = compound.getString("Text", "");

        // Sanity check for newlines
        int foundNewlines = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n' && ++foundNewlines > 3) {
                throw new IllegalArgumentException("Text contained more than 4 lines");
            }
        }

        // We can split now since we have checked that we don't blow away our heap :D
        String[] lines = text.split("\n");

        // Sanity checks on all lines
        for (String line : lines) {
            if (line.length() > 16) {
                throw new IllegalArgumentException("Line is longer than 16 chars");
            }
        }

        if (GoMint.instance().pluginManager() != null) {
            // Fire sign change event
            List<String> lineList = new ArrayList<>();
            Collections.addAll(lineList, lines);

            SignChangeTextEvent event = new SignChangeTextEvent(player, this.getBlock(), lineList);
            GoMint.instance().pluginManager().callEvent(event);

            if (event.cancelled()) {
                return;
            }

            for (int i = 0; i < 4; i++) {
                String line = event.line(i);
                if (line != null) {
                    this.lines.set(i, line);
                }
            }
        }
    }

}
