package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketEncryptionResponse extends Packet {

    public PacketEncryptionResponse() {
        super( Protocol.PACKET_ENCRYPTION_RESPONSE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
