package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
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

    public int getFormId() {
        return this.formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getJson() {
        return this.json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
