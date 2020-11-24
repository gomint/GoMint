/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.scheduler;

import java.util.concurrent.TimeUnit;

/**
 * @author BlackyPaw
 * @version 1.0
 * @stability 3
 */
public interface Scheduler {

    /**
     * Run the runnable in another Thread
     *
     * @param runnable which should be executed
     * @return the created and scheduled Task
     */
    Task executeAsync( Runnable runnable );

    /**
     * Executes a runnable with a delay. It blocks the Thread for the time until the delay arrives
     *
     * @param runnable which should be executed
     * @param delay    amount of timeUnit which should be used for waiting
     * @param timeUnit which should be used to multiply the delay
     * @return the created and scheduled Task
     */
    Task scheduleAsync( Runnable runnable, long delay, TimeUnit timeUnit );

    /**
     * Executes a runnable with a delay. It blocks the Thread for the time until the delay arrives. After the
     * execution of the Runnable it gets rescheduled again in an infinite Loop. You can cancel the returned Task
     * to stop that. It uses the same Thread over and over for rescheduling the Task.
     *
     * @param runnable which should be executed
     * @param delay    amount of timeUnit which should be used for waiting
     * @param period   amount of timeUnit which should be used for rescheduling the runnable
     * @param timeUnit which should be used to multiply the delay / period
     * @return the created and scheduled Task
     */
    Task scheduleAsync( Runnable runnable, long delay, long period, TimeUnit timeUnit );

    /**
     * Execute the given runnable on the next tick
     *
     * @param runnable which should be executed
     * @return the created and scheduled Task
     */
    Task execute( Runnable runnable );

    /**
     * Executes a runnable with a delay. The time given is not exactly taken.
     *
     * @param runnable which should be executed
     * @param delay    amount of timeUnit which should be used for waiting
     * @param timeUnit which should be used to multiply the delay
     * @return the created and scheduled Task
     */
    Task schedule( Runnable runnable, long delay, TimeUnit timeUnit );

    /**
     * Executes a runnable with a delay. It gets scheduled to run on the main thread. The runnable will NOT run at the
     * exact time you give it since running tasks is calculated by the amount of TPS a server runs. The higher the TPS
     * the more exact are the timers in here.
     *
     * @param runnable which should be executed
     * @param delay    amount of timeUnit which should be used for waiting
     * @param period   amount of timeUnit which should be used for rescheduling the runnable
     * @param timeUnit which should be used to multiply the delay / period
     * @return the created and scheduled Task
     */
    Task schedule( Runnable runnable, long delay, long period, TimeUnit timeUnit );

}
