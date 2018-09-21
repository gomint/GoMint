/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.tcp;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.socket.SocketChannel;

import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Initializer {

    private static final int LOW_MARK = Integer.getInteger( "io.gomint.low_mark", 2 << 18 );   // 0.5 mb
    private static final int HIGH_MARK = Integer.getInteger( "io.gomint.high_mark", 2 << 20 ); // 2 mb
    private static final WriteBufferWaterMark MARK = new WriteBufferWaterMark( LOW_MARK, HIGH_MARK );

    private static final EventLoopGroup EVENT_LOOP_GROUP;

    static {
        EVENT_LOOP_GROUP = Pipeline.newEventLoopGroup( 0, new ThreadFactoryBuilder().setNameFormat( "TCP Threads" ).build() );
    }

    /**
     * Build up a new netty bootstrap
     *
     * @param newConnection consumer which gets called when a new connection arrived
     * @return bootstrap ready to be bound to a ip / port
     */
    public static ServerBootstrap buildServerBootstrap( final Consumer<ConnectionHandler> newConnection ) {
        return new ServerBootstrap()
            .group( EVENT_LOOP_GROUP )
            .channel( Pipeline.getServerChannel() )
            .childOption( ChannelOption.TCP_NODELAY, true )
            .childOption( ChannelOption.SO_REUSEADDR, true )
            .childHandler( new ChannelInitializer<SocketChannel>() {
                               @Override
                               public void initChannel( SocketChannel ch ) throws Exception {
                                   final ConnectionHandler connectionHandler = new ConnectionHandler();

                                   try {
                                       ch.config().setOption( ChannelOption.IP_TOS, 0x18 );
                                   } catch ( ChannelException ex ) {
                                       // Ignored
                                   }

                                   ch.config().setAllocator( PooledByteBufAllocator.DEFAULT );
                                   ch.config().setWriteBufferWaterMark( MARK );

                                   Pipeline.prepare( ch.pipeline(), connectionHandler );

                                   connectionHandler.whenConnected( aVoid -> newConnection.accept( connectionHandler ) );
                               }
                           }
            );
    }

    /**
     * Close the underlying event loop and its threads
     */
    public static void close() {
        EVENT_LOOP_GROUP.shutdownGracefully().syncUninterruptibly();
    }

}
