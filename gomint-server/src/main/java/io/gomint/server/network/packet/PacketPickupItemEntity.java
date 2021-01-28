package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
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

    public long getItemEntityId() {
        return this.itemEntityId;
    }

    public void setItemEntityId(long itemEntityId) {
        this.itemEntityId = itemEntityId;
    }

    public long getPlayerEntityId() {
        return this.playerEntityId;
    }

    public void setPlayerEntityId(long playerEntityId) {
        this.playerEntityId = playerEntityId;
    }
}
