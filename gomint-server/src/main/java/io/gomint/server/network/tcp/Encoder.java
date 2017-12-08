/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.tcp;

import io.gomint.server.network.tcp.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * @author geNAZt
 * @version 2.0
 */
@AllArgsConstructor
public class Encoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode( ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf buf ) throws Exception {
        buf.writeByte( packet.getId() );
        packet.write( buf );
    }

}
