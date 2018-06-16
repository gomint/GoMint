/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketEntityEvent extends Packet {

    private long entityId;
    private byte eventId;
    private int eventData;

    /**
     * Create new packet entity event
     */
    public PacketEntityEvent() {
        super( Protocol.PACKET_ENTITY_EVENT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.entityId );
        buffer.writeByte( this.eventId );
        buffer.writeSignedVarInt( this.eventData );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readUnsignedVarLong();
        this.eventId = buffer.readByte();
        this.eventData = buffer.readSignedVarInt();
    }

}
