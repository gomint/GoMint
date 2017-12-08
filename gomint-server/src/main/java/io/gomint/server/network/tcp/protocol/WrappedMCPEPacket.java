package io.gomint.server.network.tcp.protocol;

import io.gomint.jraknet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import java.util.Arrays;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class WrappedMCPEPacket extends Packet {

    private PacketBuffer buffer;

    public WrappedMCPEPacket() {
        super( (byte) 0x01 );
    }

    @Override
    public void read( ByteBuf buf ) {
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes( data );
        this.buffer = new PacketBuffer( data, 0 );
    }

    @Override
    public void write( ByteBuf buf ) {
        buf.writeBytes( Arrays.copyOf( this.buffer.getBuffer(), this.buffer.getPosition() ) );
    }

}
