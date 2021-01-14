/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.scheduler;

import io.gomint.util.CompleteHandler;
import io.gomint.util.ExceptionHandler;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface Task {

    /**
     * Cancel the Task. This interrupts the Thread which is executing the Task
     */
    void cancel();

    /**
     * Register a new exceptionHandler to fetch Exceptions
     *
     * @param exceptionHandler which should be used to handle Exceptions
     */
    Task onException( ExceptionHandler exceptionHandler );

    /**
     * Register a new complete handler to fetch completion of tasks
     *
     * @param completeHandler which should be added to the completion execution list
     */
    Task onComplete( CompleteHandler completeHandler );

}
