package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketTransfer extends Packet {

    private String address;
    private int port = 19132;

    public PacketTransfer() {
        super( Protocol.PACKET_TRANSFER );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeString( this.address );
        buffer.writeLShort( (short) this.port );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.address = buffer.readString();
        this.port = buffer.readLShort();
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
