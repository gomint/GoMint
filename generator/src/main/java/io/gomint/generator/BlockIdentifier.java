/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.generator;

import io.gomint.taglib.NBTTagCompound;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode
@Getter
public class BlockIdentifier {

    private final String blockId;
    private SortedMap<String, Object> states;
    private final int runtimeId;
    private NBTTagCompound nbt;

    public BlockIdentifier(String blockId, int runtimeId, NBTTagCompound states)  {
        this.blockId = blockId;
        this.runtimeId = runtimeId;

        if (states != null && states.size() > 0) {
            this.states = new TreeMap<>();
            for (Map.Entry<String, Object> entry : states.entrySet()) {
                this.states.put(entry.getKey(), entry.getValue());
            }
        }

        this.nbt = states;
    }

}
