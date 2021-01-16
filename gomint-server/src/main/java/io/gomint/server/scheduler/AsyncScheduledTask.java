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
import java.util.concurrent.Future;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AsyncScheduledTask implements Task, Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger( AsyncScheduledTask.class );
    private final Runnable runnable;

    private ExceptionHandler exceptionHandler;
    private List<CompleteHandler> completeHandlerList;

    private Future<?> future;

    /**
     * Constructs a new AsyncScheduledTask. It needs to be executed via a normal {@link java.util.concurrent.ExecutorService}
     *
     * @param runnable runnable which should be executed
     */
    public AsyncScheduledTask( Runnable runnable ) {
        this.runnable = runnable;
    }

    @Override
    public void cancel() {
        this.future.cancel( true );
    }

    @Override
    public AsyncScheduledTask onException( ExceptionHandler exceptionHandler ) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    @Override
    public AsyncScheduledTask onComplete( CompleteHandler completeHandler ) {
        if ( this.completeHandlerList == null ) {
            this.completeHandlerList = new ArrayList<>();
        }

        this.completeHandlerList.add( completeHandler );
        return this;
    }

    @Override
    public void run() {
        // CHECKSTYLE:OFF
        try {
            this.runnable.run();
        } catch ( Exception e ) {
            if ( this.exceptionHandler != null ) {
                if ( !this.exceptionHandler.onException( e ) ) {
                    this.fireCompleteHandlers();
                    this.cancel();
                }
            } else {
                LOGGER.error( "No exception handler given", e );
            }
        }
        // CHECKSTYLE:ON
    }

    private void fireCompleteHandlers() {
        if ( this.completeHandlerList != null ) {
            for ( CompleteHandler completeHandler : this.completeHandlerList ) {
                completeHandler.onComplete();
            }

            this.completeHandlerList = null;
        }
    }

    /**
     * Set the future of this task
     *
     * @param future of this task
     */
    void assignFuture( Future<?> future ) {
        this.future = future;
    }

    public Runnable runnable() {
        return runnable;
    }

}
