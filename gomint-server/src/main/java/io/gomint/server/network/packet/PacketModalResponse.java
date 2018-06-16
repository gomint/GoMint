package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketModalResponse extends Packet {

    private int formId;
    private String json;

    public PacketModalResponse() {
        super( Protocol.PACKET_MODAL_RESPONSE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.formId = buffer.readSignedVarInt();
        this.json = buffer.readString();
    }

}
