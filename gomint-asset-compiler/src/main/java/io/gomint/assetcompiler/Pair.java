/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.assetcompiler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@AllArgsConstructor
@Data
public class Pair<A, B> {
    private A first;
    private B second;
}
