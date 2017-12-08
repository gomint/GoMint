/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.tcp;

import com.google.common.collect.MapMaker;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.tcp.protocol.Packet;
import io.gomint.server.network.tcp.protocol.UpdatePingPacket;
import io.gomint.server.network.tcp.protocol.WrappedMCPEPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ConnectionHandler extends SimpleChannelInboundHandler<Packet> {

    private ChannelHandlerContext ctx;

    private Consumer<Void> whenConnected;
    @Getter private LinkedBlockingQueue<PacketBuffer> data = new LinkedBlockingQueue<>();
    private Consumer<Throwable> exceptionCallback;
    private Consumer<Void> disconnectCallback;
    private Consumer<Integer> pingCallback;

    ConnectionHandler() {
        super( true );
    }

    public Channel getChannel() {
        return ctx.channel();
    }

    @Override
    public void channelActive( ChannelHandlerContext ctx ) throws Exception {
        this.ctx = ctx;

        if ( this.whenConnected != null ) {
            this.whenConnected.accept( null );
        }
    }

    public void send( Packet packet ) {
        flush( new FlushItem( ctx.channel(), packet ) );
    }

    private void flush( FlushItem item ) {
        EventLoop loop = item.channel.eventLoop();
        Flusher flusher = flusherLookup.get( loop );
        if ( flusher == null ) {
            Flusher alt = flusherLookup.putIfAbsent( loop, flusher = new Flusher( loop ) );
            if ( alt != null ) {
                flusher = alt;
            }
        }

        flusher.queued.add( item );
        flusher.start();
    }

    @Override
    public void channelInactive( ChannelHandlerContext ctx ) throws Exception {
        if ( this.disconnectCallback != null ) {
            this.disconnectCallback.accept( null );
        }
    }

    @Override
    protected void channelRead0( ChannelHandlerContext channelHandlerContext, final Packet packet ) throws Exception {
        if ( packet instanceof WrappedMCPEPacket ) {
            this.data.add( ( (WrappedMCPEPacket) packet ).getBuffer() );
        } else if ( packet instanceof UpdatePingPacket ) {
            this.pingCallback.accept( ( (UpdatePingPacket) packet ).getPing() );
        }
    }

    @Override
    public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) throws Exception {
        if ( this.exceptionCallback != null ) {
            this.exceptionCallback.accept( cause );
        }
    }

    public void onPing( Consumer<Integer> callback ) {
        this.pingCallback = callback;
    }

    public void whenConnected( Consumer<Void> callback ) {
        this.whenConnected = callback;
    }

    public void onException( Consumer<Throwable> callback ) {
        this.exceptionCallback = callback;
    }

    public void whenDisconnected( Consumer<Void> callback ) {
        this.disconnectCallback = callback;
    }

    public void disconnect() {
        this.ctx.disconnect().syncUninterruptibly();
    }

    private static final class Flusher implements Runnable {
        final WeakReference<EventLoop> eventLoopRef;
        final Queue<FlushItem> queued = new ConcurrentLinkedQueue<>();
        final AtomicBoolean running = new AtomicBoolean( false );
        final HashSet<Channel> channels = new HashSet<>();
        int runsWithNoWork = 0;

        private Flusher( EventLoop eventLoop ) {
            this.eventLoopRef = new WeakReference<>( eventLoop );
        }

        void start() {
            if ( !running.get() && running.compareAndSet( false, true ) ) {
                EventLoop eventLoop = eventLoopRef.get();
                if ( eventLoop != null ) {
                    eventLoop.execute( this );
                }
            }
        }

        @Override
        public void run() {
            boolean doneWork = false;
            FlushItem flush;
            while ( null != ( flush = queued.poll() ) ) {
                Channel channel = flush.channel;
                if ( channel.isActive() ) {
                    channels.add( channel );
                    channel.write( flush.request );
                    doneWork = true;
                }
            }

            // Always flush what we have (don't artificially delay to try to coalesce more messages)
            for ( Channel channel : channels ) {
                channel.flush();
            }

            channels.clear();

            if ( doneWork ) {
                runsWithNoWork = 0;
            } else {
                // either reschedule or cancel
                if ( ++runsWithNoWork > 5 ) {
                    running.set( false );
                    if ( queued.isEmpty() || !running.compareAndSet( false, true ) ) {
                        return;
                    }
                }
            }

            EventLoop eventLoop = eventLoopRef.get();
            if ( eventLoop != null && !eventLoop.isShuttingDown() ) {
                eventLoop.schedule( this, 10000, TimeUnit.NANOSECONDS );
            }
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + Integer.toHexString( this.hashCode() );
    }

    private static final ConcurrentMap<EventLoop, Flusher> flusherLookup = new MapMaker()
        .concurrencyLevel( 16 )
        .weakKeys()
        .makeMap();

    private static class FlushItem {
        final Channel channel;
        final Object request;

        private FlushItem( Channel channel, Object request ) {
            this.channel = channel;
            this.request = request;
        }
    }
}
