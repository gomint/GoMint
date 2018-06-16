package io.gomint.server.network.tcp.protocol;

import io.gomint.jraknet.PacketBuffer;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.util.Arrays;

/**
 * @author geNAZt
 * @version 2.0
 */
@Data
public class WrappedMCPEPacket extends Packet {

    private PacketBuffer[] buffer;

    public WrappedMCPEPacket() {
        super( (byte) 0x01 );
    }

    @Override
    public void read( ByteBuf buf ) {
        // First is a short showing how many packets are there
        short amountOfPackets = buf.readShort();
        this.buffer = new PacketBuffer[amountOfPackets];
        for ( short i = 0; i < amountOfPackets; i++ ) {
            int bufferLength = buf.readInt();
            byte[] data = new byte[bufferLength];
            buf.readBytes( data );
            this.buffer[i] = new PacketBuffer( data, 0 );
        }
    }

    @Override
    public void write( ByteBuf buf ) {
        buf.writeShort( this.buffer.length );
        for ( PacketBuffer buffer : this.buffer ) {
            byte[] data = Arrays.copyOf( buffer.getBuffer(), buffer.getPosition() );
            buf.writeInt( data.length );
            buf.writeBytes( data );
        }
    }

}
