/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.server.util;

import io.gomint.server.world.WorldAdapter;
import io.gomint.world.World;

import javax.annotation.Nullable;

public class Precondition {

    private Precondition() {
        throw new UnsupportedOperationException();
    }

    /**
     * Throws {@linkplain UnsafeWorldAsyncAccessException} when given {@code world} is not null and its
     * {@linkplain World#mainThread()} method returns false.
     * @param world the world to enforce sync access to
     */
    public static void safeWorldAccess(@Nullable World world) {
        if (world != null && !world.mainThread()) {
            throw new UnsafeWorldAsyncAccessException(world.folder());
        }
    }

    /**
     * Throws {@linkplain UnsafeWorldAsyncAccessException} when given {@code world} is not null and its
     * {@linkplain WorldAdapter#hardMainThreadCheck()} method returns false.
     * @param world the world to enforce sync access to
     */
    public static void safeWorldAccessHard(@Nullable WorldAdapter world) {
        if (world != null && !world.hardMainThreadCheck()) {
            throw new UnsafeWorldAsyncAccessException(world.folder());
        }
    }

}
