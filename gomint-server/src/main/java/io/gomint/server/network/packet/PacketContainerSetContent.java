package io.gomint.server.network.packet;

import io.gomint.server.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketContainerSetContent extends Packet {

    private byte windowId;
    private long entityId;
    private ItemStack[] slots;
    private int[] hotbar;

    public PacketContainerSetContent() {
        super( Protocol.PACKET_CONTAINER_SET_CONTENT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeByte( this.windowId );
        buffer.writeSignedVarLong( this.entityId );
        writeItemStacks( this.slots, buffer );
        writeIntList( this.hotbar, buffer );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
