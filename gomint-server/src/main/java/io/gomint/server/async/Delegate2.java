/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.async;

/**
 * This delegate can be used if you need two arguments for the invocation and you not interested in the result.
 *
 * @param <T1> The type of first argument for the invocation
 * @param <T2> The type of second argument for the invocation
 * @author geNAZt
 * @version 1.0
 */
public interface Delegate2<T1, T2> {

    /**
     * Invoke the calculation with two arguments. There is no result of this calculation
     *
     * @param arg1 The first argument for the invocation
     * @param arg2 The second argument for the invocation
     */
    void invoke( T1 arg1, T2 arg2 );

}
