package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketEncryptionRequest extends Packet {

    private String jwt;

    public PacketEncryptionRequest() {
        super( Protocol.PACKET_ENCRYPTION_REQUEST );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeString( this.jwt );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.jwt = buffer.readString();
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
