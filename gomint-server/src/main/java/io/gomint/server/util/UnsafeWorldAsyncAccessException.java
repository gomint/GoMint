/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.util;

public class UnsafeWorldAsyncAccessException extends RuntimeException {

    public UnsafeWorldAsyncAccessException(String world) {
        super("Accessing or editing world data not from world's thread. Affected world: " + world);
    }

}
