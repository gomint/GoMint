/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.tcp;

import io.gomint.server.network.tcp.protocol.SendPlayerToServerPacket;
import io.gomint.server.network.tcp.protocol.UpdatePingPacket;
import io.gomint.server.network.tcp.protocol.WrappedMCPEPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author geNAZt
 * @version 2.0
 */
public class Decoder extends ByteToMessageDecoder {

    @Override
    protected void decode( ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> objects ) throws Exception {
        if ( buf instanceof EmptyByteBuf ) {
            // The Channel has disconnected and this is the last message we got. R.I.P. connection
            return;
        }

        byte packetId = buf.readByte();
        switch ( packetId ) {
            case 1:
                WrappedMCPEPacket wrappedMCPEPacket = new WrappedMCPEPacket();
                wrappedMCPEPacket.read( buf );
                objects.add( wrappedMCPEPacket );
                break;
            case 2:
                UpdatePingPacket updatePingPacket = new UpdatePingPacket();
                updatePingPacket.read( buf );
                objects.add( updatePingPacket );
                break;
            case 3:
                SendPlayerToServerPacket sendPlayerToServerPacket = new SendPlayerToServerPacket();
                sendPlayerToServerPacket.read( buf );
                objects.add( sendPlayerToServerPacket );
                break;
            default:
                break;
        }
    }

}
