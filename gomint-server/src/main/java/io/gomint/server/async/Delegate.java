/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.async;

/**
 * This delegate can be used if you need one argument for the invocation and you not interested in the result.
 *
 * @param <T> The type of the argument for the invocation
 * @author BlackyPaw
 * @version 1.0
 */
public interface Delegate<T> {

    /**
     * Invoke the calculation with one argument. There is no result of this calculation
     *
     * @param arg The argument for the invocation
     */
    void invoke( T arg );

}
