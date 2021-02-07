/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.util;

public class UnsafeWorldAsyncAccessWarning extends Exception {

    public UnsafeWorldAsyncAccessWarning() {
        super("Accessing or editing world data not from world's thread. This is not safe");
    }

}
