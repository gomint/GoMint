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

    private byte raknetVersion;
    private PacketBuffer[] buffer;

    public WrappedMCPEPacket() {
        super( (byte) 0x01 );
    }

    @Override
    public void read( ByteBuf buf ) {
        this.raknetVersion = buf.readByte();

        // First is a short showing how many packets are there
        short amountOfPackets = buf.readShort();
        this.buffer = new PacketBuffer[amountOfPackets];
        for ( short i = 0; i < amountOfPackets; i++ ) {
            int bufferLength = buf.readInt();
            ByteBuf data = buf.retainedSlice(buf.readerIndex(), bufferLength);
            buf.readerIndex(buf.readerIndex() + bufferLength);
            this.buffer[i] = new PacketBuffer( data );
        }
    }

    @Override
    public void write( ByteBuf buf ) {
        buf.writeByte( this.raknetVersion );
        buf.writeShort( this.buffer.length );
        for ( PacketBuffer buffer : this.buffer ) {
            buf.writeInt( buffer.getWritePosition() );
            buf.writeBytes( buffer.getBuffer() );
        }
    }

}
