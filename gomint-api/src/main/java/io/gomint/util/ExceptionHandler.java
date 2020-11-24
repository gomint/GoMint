/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.util;

/**
 * @author geNAZt
 * @version 1.0
 *          <p>
 *          Interface which should be used to catch Exceptions thrown. This is mainly used in the Scheduler for Exception handling
 * @stability 3
 */
public interface ExceptionHandler {

    /**
     * Fired when a exception has been thrown. The return value of the call decides if the Code gets executed further
     * or not
     *
     * @param e the thrown Exception
     * @return true when the code should be executed further, false if not
     */
    boolean onException( Exception e );

}
