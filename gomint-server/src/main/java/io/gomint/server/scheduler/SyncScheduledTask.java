/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.scheduler;

import io.gomint.scheduler.Task;
import io.gomint.util.CompleteHandler;
import io.gomint.util.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SyncScheduledTask implements Task, Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger( SyncScheduledTask.class );
    private final Runnable runnable;
    private long period;          // -1 means no reschedule
    private long nextExecution; // -1 is cancelled
    private ExceptionHandler exceptionHandler;
    private List<CompleteHandler> completeHandlerList;
    private SyncTaskManager manager;

    /**
     * Constructs a new SyncScheduledTask. It needs to be executed via a normal {@link java.util.concurrent.ExecutorService}
     *
     * @param manager which schedules this task
     * @param runnable The runnable which should be executed
     * @param delay   Amount of time units to wait until the invocation of this execution
     * @param period  Amount of time units for the delay after execution to run the runnable again
     * @param unit    of time
     */
    public SyncScheduledTask( SyncTaskManager manager, Runnable runnable, long delay, long period, TimeUnit unit ) {
        this.runnable = runnable;
        this.period = ( period >= 0 ) ? unit.toMillis( period ) : -1;
        this.nextExecution = ( delay >= 0 ) ? System.currentTimeMillis() + unit.toMillis( delay ) : -1;
        this.manager = manager;
    }

    public Runnable runnable() {
        return this.runnable;
    }

    public long getNextExecution() {
        return this.nextExecution;
    }

    @Override
    public String toString() {
        return "SyncScheduledTask{" +
            "runnable=" + this.runnable +
            ", period=" + this.period +
            ", nextExecution=" + this.nextExecution +
            '}';
    }

    @Override
    public void run() {
        // CHECKSTYLE:OFF
        try {
            this.runnable.run();
        } catch ( Exception e ) {
            if ( this.exceptionHandler != null ) {
                if ( !this.exceptionHandler.onException( e ) ) {
                    this.cancel();
                }
            } else {
                LOGGER.warn( "Error in executing task: ", e );
            }
        }
        // CHECKSTYLE:ON

        if ( this.period > 0 ) {
            this.nextExecution = System.currentTimeMillis() + this.period;
        } else {
            this.cancel();
        }
    }

    @Override
    public void cancel() {
        this.period = -1;
        this.nextExecution = -1;
        this.fireCompleteHandlers();
        this.manager.removeTask( this );
    }

    @Override
    public SyncScheduledTask onException( ExceptionHandler exceptionHandler ) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    @Override
    public SyncScheduledTask onComplete( CompleteHandler completeHandler ) {
        if ( this.completeHandlerList == null ) {
            this.completeHandlerList = new ArrayList<>();
        }

        this.completeHandlerList.add( completeHandler );
        return this;
    }

    private void fireCompleteHandlers() {
        if ( this.completeHandlerList != null ) {
            for ( CompleteHandler completeHandler : this.completeHandlerList ) {
                completeHandler.onComplete();
            }

            this.completeHandlerList = null;
        }
    }

}
