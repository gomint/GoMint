/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Very simple and small future class. Supports all features of the JDKs Future&lt;T&gt; class but provides
 * more status helpers and the possibility to resolve futures whenever the user sees fit instead of having to
 * wait for an executor to finish.
 *
 * @param <T> The type of object we want to resolve with this future
 * @author BlackyPaw
 * @version 1.0
 */
public class Future<T> {

    private static final Logger logger = LoggerFactory.getLogger( Future.class );

    private static final byte UNRESOLVED = (byte) 0;
    private static final byte RESOLVED = (byte) 1;
    private static final byte FAILED = (byte) 2;

    private byte state = UNRESOLVED;
    private Object result;
    private List<FutureListener<Future<T>>> futureListeners = new ArrayList<>();

    /**
     * Attempts to get the future's result only if it is available. It will return null if the future is yet
     * unresolved or failed.
     *
     * @return The future's result. Only guaranteed to be the actual result if {@link #isSuccess()} is true
     */
    public synchronized T tryGet() {
        switch ( this.state ) {
            case RESOLVED:
                return (T) this.result;
            default:
                return null;
        }
    }

    /**
     * Gets the result of the future operation.
     *
     * @return The future operation's result
     * @throws InterruptedException Thrown in case the future gets interrupted whilst waiting for the operation to complete
     * @throws ExecutionException   Thrown in case the future operation failed
     */
    @SuppressWarnings( "unchecked" )
    public synchronized T get() throws InterruptedException, ExecutionException {
        while ( this.state == UNRESOLVED ) {
            this.wait();
        }

        return getResultOrException();
    }

    /**
     * Gets the result of the future operation by blocking uninterruptibly, i.e. not even waking up if this thread
     * gets interrupted by another thread.
     *
     * @return The future operation's result
     * @throws ExecutionException Thrown in case the future operation failed
     */
    @SuppressWarnings( "unchecked" )
    public synchronized T getUninterruptibly() throws ExecutionException {
        while ( this.state == UNRESOLVED ) {
            try {
                this.wait();
            } catch ( InterruptedException ignored ) {
                // ._.
            }
        }

        return getResultOrException();
    }

    /**
     * Gets the result of the future operation but will only wait for the specified timeout before a TimeoutException
     * will be thrown.
     *
     * @param duration The duration this operation should wait before hitting the timeout
     * @param unit     The TimeUnit which is used to calculate the duration in milliseconds
     * @return The future operation's result
     * @throws InterruptedException Thrown in case the future gets interrupted whilst waiting for the operation to complete
     * @throws ExecutionException   Thrown in case the future operation failed
     * @throws TimeoutException     Thrown in case the specified timeout expires before the future operation completes
     */
    @SuppressWarnings( "unchecked" )
    public synchronized T get( long duration, TimeUnit unit ) throws InterruptedException, ExecutionException, TimeoutException {
        long now = System.currentTimeMillis();
        long end = now + unit.toMillis( duration );

        while ( this.state == UNRESOLVED && now < end ) {
            this.wait( end - now );
            now = System.currentTimeMillis();
        }

        return getResultOrTimeoutException();
    }

    /**
     * Gets the result of the future operation but will only wait for the specified timeout before a TimeoutException
     * will be thrown. This call blocks uninterruptibly, i.e. it will not even wake up if this thread gets interrupted
     * by another thread.
     *
     * @param duration The duration this operation should wait before hitting the timeout
     * @param unit     The TimeUnit which is used to calculate the duration in milliseconds
     * @return The future operation's result
     * @throws ExecutionException Thrown in case the future operation failed
     * @throws TimeoutException   Thrown in case the specified timeout expires before the future operation completes
     */
    @SuppressWarnings( "unchecked" )
    public synchronized T getUninterruptibly( long duration, TimeUnit unit ) throws ExecutionException, TimeoutException {
        long now = System.currentTimeMillis();
        long end = now + unit.toMillis( duration );
        while ( this.state == UNRESOLVED && now < end ) {
            try {
                this.wait( end - now );
            } catch ( InterruptedException ignored ) {
                // ._.
            }

            now = System.currentTimeMillis();
        }

        return getResultOrTimeoutException();
    }

    /**
     * Adds a future listener which will be invoked as soon as the future resolves
     *
     * @param listener The listener to add
     */
    public synchronized void addFutureListener( FutureListener<Future<T>> listener ) {
        if ( this.state == UNRESOLVED ) {
            this.futureListeners.add( listener );
        } else {
            listener.onFutureResolved( this );
        }
    }

    /**
     * Checks whether or not the future operation has completed yet.
     *
     * @return Whether or not the future operation has completed yet
     */
    public synchronized boolean isDone() {
        return this.state != UNRESOLVED;
    }

    /**
     * Checks whether or not the future operation was successful.
     *
     * @return Whether or not the future operation was successful
     */
    public synchronized boolean isSuccess() {
        return this.state == RESOLVED;
    }

    /**
     * Checks whether or not the future operation failed.
     *
     * @return Whether or not the future operation failed
     */
    public synchronized boolean isFailure() {
        return this.state == FAILED;
    }

    /**
     * Resolves the future given the result it should return to any waiting consumers.
     *
     * @param result The result of the future operation
     */
    public synchronized void resolve( T result ) {
        setWhenUnresolved( result, RESOLVED );
    }

    /**
     * Resolves the future indicating that the future operation failed for the given cause.
     *
     * @param cause The cause of the future operation's failure
     */
    public synchronized void fail( Throwable cause ) {
        setWhenUnresolved( cause, FAILED );
    }

    private T getResultOrException() throws ExecutionException {
        switch ( this.state ) {
            case RESOLVED:
                return (T) this.result;
            case FAILED:
            default:
                throw new ExecutionException( "Future operation failed to execute", (Throwable) this.result );
        }
    }

    private T getResultOrTimeoutException() throws TimeoutException, ExecutionException {
        switch ( this.state ) {
            case UNRESOLVED:
                throw new TimeoutException( "Future operation did not complete within timeout" );
            case RESOLVED:
                return (T) this.result;
            case FAILED:
            default:
                throw new ExecutionException( "Future operation failed to execute", (Throwable) this.result );
        }
    }

    private synchronized void setWhenUnresolved( Object result, byte resolved ) {
        if ( this.state == UNRESOLVED ) {
            this.state = resolved;
            this.result = result;
            this.notifyAll();

            for ( FutureListener<Future<T>> listener : this.futureListeners ) {
                try {
                    listener.onFutureResolved( this );
                } catch ( Throwable rethrown ) {
                    logger.error( "Failed to invoke future listener", rethrown );
                }
            }

            // Release all references to listeners:
            this.futureListeners = null;
        }
    }

}
