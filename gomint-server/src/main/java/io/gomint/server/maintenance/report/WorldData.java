/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.maintenance.report;

/**
 * @author geNAZt
 * @version 1.0
 */
public class WorldData {

    private final int chunkAmount;

    public WorldData(int chunkAmount) {
        this.chunkAmount = chunkAmount;
    }

    @Override
    public String toString() {
        return "WorldData{" +
            "chunkAmount=" + chunkAmount +
            '}';
    }

}
