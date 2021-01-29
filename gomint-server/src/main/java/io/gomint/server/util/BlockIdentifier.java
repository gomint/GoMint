/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import io.gomint.server.util.collection.FixedReadOnlyMap;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockIdentifier {

    private final String blockId;
    private final int blockNumericId;
    private FixedReadOnlyMap states;
    private final short runtimeId;

    public BlockIdentifier(String blockId, int blockNumericId, short runtimeId, NBTTagCompound states) {
        this.blockId = blockId.intern();
        this.blockNumericId = blockNumericId;
        this.runtimeId = runtimeId;

        if (states != null && states.size() > 0) {
            this.states = new FixedReadOnlyMap(states.entrySet());
        }
    }

    public String blockId() {
        return this.blockId;
    }

    public int numericId() {
        return this.blockNumericId;
    }

    public FixedReadOnlyMap states() {
        return this.states;
    }

    public short runtimeId() {
        return this.runtimeId;
    }

    public NBTTagCompound nbt() {
        if (this.states == null) {
            return new NBTTagCompound("states");
        }

        return this.states.toNBT("states");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockIdentifier that = (BlockIdentifier) o;
        return this.runtimeId == that.runtimeId;
    }

    @Override
    public int hashCode() {
        return this.runtimeId;
    }

    @Override
    public String toString() {
        return "BlockIdentifier{" +
            "blockId='" + this.blockId + '\'' +
            ", blockNumericId=" + this.blockNumericId +
            ", states=" + this.states +
            ", runtimeId=" + this.runtimeId +
            '}';
    }

}
