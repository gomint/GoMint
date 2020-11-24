/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketServerSettingsResponse extends Packet {

    private int formId;
    private String json;

    /**
     * Construct a new packet
     */
    public PacketServerSettingsResponse() {
        super( Protocol.PACKET_SERVER_SETTINGS_RESPONSE );
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
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
