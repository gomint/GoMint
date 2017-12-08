/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.tcp;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.concurrent.ThreadFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
class Pipeline {

    private static final String FRAME_DECODER = "frameDecoder";
    private static final String FRAME_PREPENDER = "framePrepender";
    private static final String CONNECTION_HANDLER = "connectionHandler";
    private static final String PACKET_DECODER = "packetDecoder";
    private static final String PACKET_ENCODER = "packetEncoder";

    /**
     * Create a new event loop group based on the native implementation
     *
     * @param threads which should be created max, 0 for unlimited
     * @param factory which creates the threads
     * @return event loop group
     */
    static EventLoopGroup newEventLoopGroup( int threads, ThreadFactory factory ) {
        return Epoll.isAvailable() ? new EpollEventLoopGroup( threads, factory ) : new NioEventLoopGroup( threads, factory );
    }

    /**
     * Get correct implementation for native supported channels
     *
     * @return epoll or nio implementation of socker channels
     */
    static Class<? extends ServerChannel> getServerChannel() {
        return Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

    /**
     * Prepare the netty pipeline by appending needed encoders, decoders and prependers
     *
     * @param pipeline          which should be configured
     * @param connectionHandler which should be used to configure the handler
     */
    static void prepare( ChannelPipeline pipeline, ConnectionHandler connectionHandler ) {
        pipeline.addLast( FRAME_DECODER, new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE, 0, 4, 0, 4 ) );
        pipeline.addLast( PACKET_DECODER, new Decoder() );
        pipeline.addLast( FRAME_PREPENDER, new LengthFieldPrepender( 4 ) );
        pipeline.addLast( PACKET_ENCODER, new Encoder() );
        pipeline.addLast( CONNECTION_HANDLER, connectionHandler );
    }

}
