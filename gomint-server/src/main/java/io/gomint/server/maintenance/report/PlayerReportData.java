/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.maintenance.report;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
@ToString
public class PlayerReportData {

    private String world;
    private float x;
    private float y;
    private float z;

}
