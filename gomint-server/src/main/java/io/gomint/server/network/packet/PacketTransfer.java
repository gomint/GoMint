package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

public class PacketTransfer extends Packet {

    public String address;
    public int port = 19132;

    public PacketTransfer() {
        super( Protocol.PACKET_TRANSFER );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {
        buffer.writeString( address );
        buffer.writeLShort( (byte) port );
    }

    @Override
    public void deserialize( PacketBuffer buffer ) {
        this.address = buffer.readString();
        this.port = buffer.readLShort();
    }
}
