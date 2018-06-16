package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketPickupItemEntity extends Packet {

    private long itemEntityId;
    private long playerEntityId;

    public PacketPickupItemEntity() {
        super( Protocol.PACKET_PICKUP_ITEM_ENTITY );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.itemEntityId );
        buffer.writeUnsignedVarLong( this.playerEntityId );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
