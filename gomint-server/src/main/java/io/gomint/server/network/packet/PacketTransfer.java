package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Getter;
import lombok.Setter;

public class PacketTransfer extends Packet {

    @Getter@Setter
    private String address;
    @Getter@Setter
    private int port = 19132;

    public PacketTransfer() {
        super( Protocol.PACKET_TRANSFER );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {
        buffer.writeString( address );
        buffer.writeLShort( (short) port );
    }

    @Override
    public void deserialize( PacketBuffer buffer ) {
        this.address = buffer.readString();
        this.port = buffer.readLShort();
    }
}
