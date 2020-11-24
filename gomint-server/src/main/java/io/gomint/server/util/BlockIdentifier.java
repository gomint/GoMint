/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import io.gomint.server.util.collection.FreezableSortedMap;
import io.gomint.taglib.NBTTagCompound;

import java.util.Map;
import java.util.Objects;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockIdentifier {

    private final String blockId;
    private final int blockNumericId;
    private FreezableSortedMap<String, Object> states;
    private final short runtimeId;
    private NBTTagCompound nbt;

    public BlockIdentifier(String blockId, int blockNumericId, short runtimeId, NBTTagCompound states)  {
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

    public String getBlockId() {
        return blockId;
    }

    public int getBlockNumericId() {
        return blockNumericId;
    }

    public FreezableSortedMap<String, Object> getStates() {
        return states;
    }

    public void setStates(FreezableSortedMap<String, Object> states) {
        this.states = states;
    }

    public short getRuntimeId() {
        return runtimeId;
    }

    public NBTTagCompound getNbt() {
        return nbt;
    }

    public void setNbt(NBTTagCompound nbt) {
        this.nbt = nbt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockIdentifier that = (BlockIdentifier) o;
        return blockNumericId == that.blockNumericId &&
            runtimeId == that.runtimeId &&
            Objects.equals(blockId, that.blockId) &&
            Objects.equals(states, that.states) &&
            Objects.equals(nbt, that.nbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId, blockNumericId, states, runtimeId, nbt);
    }

    @Override
    public String toString() {
        return "BlockIdentifier{" +
            "blockId='" + blockId + '\'' +
            ", blockNumericId=" + blockNumericId +
            ", states=" + states +
            ", runtimeId=" + runtimeId +
            ", nbt=" + nbt +
            '}';
    }

}
