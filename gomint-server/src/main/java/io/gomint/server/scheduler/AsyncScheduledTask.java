/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.scheduler;

import io.gomint.scheduler.Task;
import io.gomint.util.CompleteHandler;
import io.gomint.util.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AsyncScheduledTask implements Task, Runnable {

    private final Runnable task;

    private final long delay;   // -1 means no execution
    private final long period;  // <= 0 means no reschedule
    private final AtomicBoolean running = new AtomicBoolean( true );

    private ExceptionHandler exceptionHandler;
    private List<CompleteHandler> completeHandlerList;
    private Thread executingThread;

    /**
     * Constructs a new AsyncScheduledTask. It needs to be executed via a normal {@link java.util.concurrent.ExecutorService}
     *
     * @param task   runnable which should be executed
     * @param delay  of this execution
     * @param period delay after execution to run the runnable again
     * @param unit   of time
     */
    public AsyncScheduledTask( Runnable task, long delay, long period, TimeUnit unit ) {
        this.task = task;
        this.delay = ( delay >= 0 ) ? unit.toMillis( delay ) : -1;
        this.period = ( period >= 0 ) ? unit.toMillis( period ) : -1;
    }

    @Override
    public void cancel() {
        this.running.set( false );
        this.executingThread.interrupt();
    }

    @Override
    public void onException( ExceptionHandler exceptionHandler ) {
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void onComplete( CompleteHandler completeHandler ) {
        if ( this.completeHandlerList == null ) {
            this.completeHandlerList = new ArrayList<>();
        }

        this.completeHandlerList.add( completeHandler );
    }

    @Override
    public void run() {
        // Fast path to failout
        if ( this.delay == -1 ) {
            this.fireCompleteHandlers();
            return;
        }

        this.executingThread = Thread.currentThread();

        // Check if we need to wait for the first execution
        if ( this.delay > 0 ) {
            try {
                Thread.sleep( this.delay );
            } catch ( InterruptedException ex ) {
                this.executingThread.interrupt();
            }
        }

        while ( this.running.get() ) {
            // CHECKSTYLE:OFF
            try {
                this.task.run();
            } catch ( Exception e ) {
                if ( this.exceptionHandler != null ) {
                    if ( !this.exceptionHandler.onException( e ) ) {
                        this.fireCompleteHandlers();
                        return;
                    }
                } else {
                    e.printStackTrace();
                }
            }
            // CHECKSTYLE:ON

            // If we have a period of 0 or less, only run once
            if ( this.period <= 0 ) {
                this.fireCompleteHandlers();
                break;
            }

            try {
                Thread.sleep( this.period );
            } catch ( InterruptedException ex ) {
                this.executingThread.interrupt();
            }
        }
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
