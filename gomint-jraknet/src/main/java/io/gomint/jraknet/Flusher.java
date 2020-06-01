package io.gomint.jraknet;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Flusher implements Runnable {

    private final WeakReference<EventLoop> eventLoopRef;
    final Queue<FlushItem> queued = new ConcurrentLinkedQueue<>();
    private final AtomicBoolean running = new AtomicBoolean( false );
    private final HashSet<Channel> channels = new HashSet<>();
    private int runsWithNoWork = 0;

    Flusher( EventLoop eventLoop ) {
        this.eventLoopRef = new WeakReference<>( eventLoop );
    }

    void start() {
        if ( !this.running.get() && this.running.compareAndSet( false, true ) ) {
            EventLoop eventLoop = this.eventLoopRef.get();
            if ( eventLoop != null ) {
                eventLoop.execute( this );
            }
        }
    }

    @Override
    public void run() {
        boolean doneWork = false;
        FlushItem flush;

        while ( null != ( flush = this.queued.poll() ) ) {
            Channel channel = flush.channel;
            if ( channel.isActive() ) {
                this.channels.add( channel );
                channel.write( flush.request );
                doneWork = true;
            }
        }

        // Always flush what we have (don't artificially delay to try to coalesce more messages)
        for ( Channel channel : this.channels ) {
            channel.flush();
        }

        this.channels.clear();

        if ( doneWork ) {
            this.runsWithNoWork = 0;
        } else {
            // either reschedule or cancel
            if ( ++this.runsWithNoWork > 5 ) {
                this.running.set( false );

                if ( this.queued.isEmpty() || !this.running.compareAndSet( false, true ) ) {
                    return;
                }
            }
        }

        EventLoop eventLoop = this.eventLoopRef.get();
        if ( eventLoop != null && !eventLoop.isShuttingDown() ) {
            eventLoop.schedule( this, 10000, TimeUnit.NANOSECONDS );
        }
    }

    public static class FlushItem {
        final Channel channel;
        final Object request;

        public FlushItem( Channel channel, Object request ) {
            this.channel = channel;
            this.request = request;
        }
    }

}