/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.async;

/**
 * Listener for Futures. It invokes when a Future this has been appended to has been resolved.
 *
 * @param <T> The type of Future which resolves
 * @author BlackyPaw
 * @version 1.0
 */
public interface FutureListener<T extends Future> {

    /**
     * Invoked when the Future which this is appended to has been resolved.
     *
     * @param future The future which has been resolved
     */
    void onFutureResolved( T future );

}
