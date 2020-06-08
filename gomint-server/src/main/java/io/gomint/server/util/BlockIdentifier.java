/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.SortedMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockIdentifier {

    @Getter
    private final String blockId;
    private SortedMap<String, Object> states;
    @Getter
    private short data;

    public BlockIdentifier(String blockId, NBTTagCompound states, Short data)  {
        this.blockId = blockId;

        if (states != null) {
            this.states = new Object2ObjectLinkedOpenHashMap<>();
            for (Map.Entry<String, Object> entry : states.entrySet()) {
                this.states.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public BlockIdentifier(String blockId, SortedMap<String, Object> states, Short data) {
        this.blockId = blockId;
        this.states = states;
    }

    public SortedMap<String, Object> getStates(boolean copy) {
        if (this.states == null) {
            return null;
        }

        return copy ? new Object2ObjectLinkedOpenHashMap<>(this.states) : this.states;
    }

    @Override
    public int hashCode() {
        if (this.states != null) {
            return this.blockId.hashCode() + this.states.hashCode();
        }

        return this.blockId.hashCode();
    }

    public long longHashCode() {
        if (this.states != null) {
            return (long) this.blockId.hashCode() << 32 | this.states.hashCode();
        }

        return (long) this.blockId.hashCode() << 32;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj instanceof BlockIdentifier ) {
            BlockIdentifier other = (BlockIdentifier) obj;
            return other.blockId.equals( this.blockId ) && other.data == this.data;
        }

        return false;
    }

}
