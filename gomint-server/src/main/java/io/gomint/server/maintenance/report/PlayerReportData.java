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
public class PlayerReportData {

    private final String world;
    private final float x;
    private final float y;
    private final float z;

    public PlayerReportData(String world, float x, float y, float z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "PlayerReportData{" +
            "world='" + world + '\'' +
            ", x=" + x +
            ", y=" + y +
            ", z=" + z +
            '}';
    }

}
