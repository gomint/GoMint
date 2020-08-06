/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import io.gomint.server.util.collection.FreezableSortedMap;
import io.gomint.taglib.NBTTagCompound;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode
@Getter
@ToString
public class BlockIdentifier {

    private final String blockId;
    private final int blockNumericId;
    private FreezableSortedMap<String, Object> states;
    private final int runtimeId;
    private NBTTagCompound nbt;

    public BlockIdentifier(String blockId, int blockNumericId, int runtimeId, NBTTagCompound states)  {
        this.blockId = blockId;
        this.blockNumericId = blockNumericId;
        this.runtimeId = runtimeId;

        if (states != null && states.size() > 0) {
            this.states = new FreezableSortedMap<>();
            for (Map.Entry<String, Object> entry : states.entrySet()) {
                this.states.put(entry.getKey(), entry.getValue());
            }

            this.states.setFrozen(true);
        }

        this.nbt = states;
    }

}
