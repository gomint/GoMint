/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.generator;

import io.gomint.taglib.NBTTagCompound;

import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author geNAZt
 * @version 1.0
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockIdentifier that = (BlockIdentifier) o;
        return this.runtimeId == that.runtimeId &&
            Objects.equals(this.blockId, that.blockId) &&
            Objects.equals(this.states, that.states) &&
            Objects.equals(this.nbt, that.nbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.blockId, this.states, this.runtimeId, this.nbt);
    }

    @Override
    public String toString() {
        return "BlockIdentifier{" +
            "blockId='" + this.blockId + '\'' +
            ", states=" + this.states +
            ", runtimeId=" + this.runtimeId +
            ", nbt=" + this.nbt +
            '}';
    }

    public String getBlockId() {
        return this.blockId;
    }

    public SortedMap<String, Object> getStates() {
        return this.states;
    }

    public int getRuntimeId() {
        return this.runtimeId;
    }

    public NBTTagCompound getNbt() {
        return this.nbt;
    }

}
