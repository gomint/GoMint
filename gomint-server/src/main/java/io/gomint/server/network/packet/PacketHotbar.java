package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketHotbar extends Packet {

    private int selectedHotbarSlot;
    private byte windowId;
    private int[] slots;
    private boolean selectHotbarSlot;

    /**
     * Construct a new packet
     */
    public PacketHotbar() {
        super( Protocol.PACKET_HOTBAR );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer ) {
        this.selectedHotbarSlot = buffer.readUnsignedVarInt();
        this.windowId = buffer.readByte();

        this.slots = new int[buffer.readUnsignedVarInt()];
        for ( int i = 0; i < this.slots.length; i++ ) {
            this.slots[i] = buffer.readUnsignedVarInt();
        }

        this.selectHotbarSlot = buffer.readBoolean();
    }

}
