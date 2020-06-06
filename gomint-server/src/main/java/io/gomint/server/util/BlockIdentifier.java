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
@Getter
public class BlockIdentifier {

    private final String blockId;
    private final SortedMap<String, Object> states = new Object2ObjectLinkedOpenHashMap<>();
    private final short data;

    public BlockIdentifier(String blockId, NBTTagCompound states, Short data)  {
        this.blockId = blockId;
        this.data = data;

        if (states != null) {
            for (Map.Entry<String, Object> entry : states.entrySet()) {
                this.states.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public int hashCode() {
        return this.blockId.hashCode() + this.data;
    }

    public long longHashCode() {
        return (long) this.blockId.hashCode() << 32 | this.data;
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
