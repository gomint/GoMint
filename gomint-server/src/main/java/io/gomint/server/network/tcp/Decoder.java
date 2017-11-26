/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.tcp;

import io.gomint.server.network.tcp.protocol.WrappedMCPEPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Decoder extends ByteToMessageDecoder {

    @Override
    protected void decode( ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> objects ) throws Exception {
        if ( buf instanceof EmptyByteBuf ) {
            // The Channel has disconnected and this is the last message we got. R.I.P. connection
            return;
        }

        WrappedMCPEPacket wrappedMCPEPacket = new WrappedMCPEPacket();
        wrappedMCPEPacket.read( buf );
        objects.add( wrappedMCPEPacket );
    }

}
