package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
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
