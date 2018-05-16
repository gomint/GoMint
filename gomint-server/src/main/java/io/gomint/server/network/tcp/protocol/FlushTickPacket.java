package io.gomint.server.network.tcp.protocol;

import io.netty.buffer.ByteBuf;

/**
 * @author geNAZt
 * @version 1.0
 */
public class FlushTickPacket extends Packet {

    public FlushTickPacket() {
        super( (byte) 0x04 );
    }

    @Override
    public void read( ByteBuf buf ) {

    }

    @Override
    public void write( ByteBuf buf ) {

    }

}
