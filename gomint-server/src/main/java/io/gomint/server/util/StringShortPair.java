/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

/**
 * @author geNAZt
 * @version 1.0
 */
public class StringShortPair {

    private final String blockId;
    private final short data;

    public StringShortPair(String blockId, short data) {
        this.blockId = blockId;
        this.data = data;
    }

    public String getBlockId() {
        return this.blockId;
    }

    public short getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "StringShortPair{" +
            "blockId='" + this.blockId + '\'' +
            ", data=" + this.data +
            '}';
    }

}
