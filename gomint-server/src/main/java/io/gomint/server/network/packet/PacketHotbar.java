package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketHotbar extends Packet {

    private int selectedHotbarSlot;
    private byte windowId;
    private boolean selectHotbarSlot;

    /**
     * Construct a new packet
     */
    public PacketHotbar() {
        super( Protocol.PACKET_HOTBAR );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarInt(this.selectedHotbarSlot);
        buffer.writeByte(this.windowId);
        buffer.writeBoolean(this.selectHotbarSlot);
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.selectedHotbarSlot = buffer.readUnsignedVarInt();
        this.windowId = buffer.readByte();
        this.selectHotbarSlot = buffer.readBoolean();
    }

    public int getSelectedHotbarSlot() {
        return selectedHotbarSlot;
    }

    public void setSelectedHotbarSlot(int selectedHotbarSlot) {
        this.selectedHotbarSlot = selectedHotbarSlot;
    }

    public byte getWindowId() {
        return windowId;
    }

    public void setWindowId(byte windowId) {
        this.windowId = windowId;
    }

    public boolean isSelectHotbarSlot() {
        return selectHotbarSlot;
    }

    public void setSelectHotbarSlot(boolean selectHotbarSlot) {
        this.selectHotbarSlot = selectHotbarSlot;
    }
}
