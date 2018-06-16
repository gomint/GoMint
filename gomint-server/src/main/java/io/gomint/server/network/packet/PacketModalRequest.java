package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketModalRequest extends Packet {

    private int formId;
    private String json;

    public PacketModalRequest() {
        super( Protocol.PACKET_MODAL_REQUEST );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarInt( this.formId );
        buffer.writeString( this.json );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
