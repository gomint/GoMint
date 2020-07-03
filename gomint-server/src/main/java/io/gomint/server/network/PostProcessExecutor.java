/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network;

import io.gomint.server.network.packet.Packet;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
@EqualsAndHashCode
public class PostProcessExecutor implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger( PostProcessExecutor.class );

    @Getter
    private final AtomicInteger connectionsInUse = new AtomicInteger( 0 );
    private Queue<PostProcessWorker> workers = new ConcurrentLinkedQueue<>();
    @Getter
    private float load;
    private Future<?> future;
    private AtomicBoolean running = new AtomicBoolean( true );
    private final Object waiter = new Object();
    private final ExecutorService executorService;

    public PostProcessExecutor( ExecutorService executorService ) {
        this.executorService = executorService;
        this.future = executorService.submit( this );
    }

    public void addWork(ConnectionWithState connection, Packet[] packets, Consumer<Void> callback) {
        this.workers.offer( new PostProcessWorker( connection, packets, callback ) );

        synchronized ( this.waiter ) {
            this.waiter.notifyAll();
        }
    }

    public void stop() {
        this.running.set( false );
        this.future.cancel( true );
    }

    @Override
    public void run() {
        while ( this.running.get() && !this.executorService.isShutdown() ) {
            long start = System.currentTimeMillis();

            while ( !this.workers.isEmpty() ) {
                PostProcessWorker worker = this.workers.poll();
                if ( worker != null ) {
                    try {
                        worker.run();
                    } catch ( Throwable t ) {
                        t.printStackTrace();
                    }
                }
            }

            this.load = ( ( System.currentTimeMillis() - start ) / 50f ) * 100;
            if ( this.load > 60 ) {
                LOGGER.debug( "Post processor load > 60%: {}", this.load );
            }

            // Wait on the next worker
            if ( this.workers.isEmpty() ) {
                synchronized ( this.waiter ) {
                    try {
                        this.waiter.wait( 500 );
                    } catch ( InterruptedException e ) {
                        // Ignored
                    }
                }
            }
        }
    }

}
