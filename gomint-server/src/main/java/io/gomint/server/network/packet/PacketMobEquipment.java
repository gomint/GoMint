package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketMobEquipment extends Packet {

    private long entityId;

    private ItemStack stack;
    private byte slot;
    private byte selectedSlot;
    private byte windowId;

    /**
     * Create new packet
     */
    public PacketMobEquipment() {
        super( Protocol.PACKET_MOB_EQUIPMENT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.entityId );
        writeItemStack( this.stack, buffer );
        buffer.writeByte( this.slot );
        buffer.writeByte( this.selectedSlot );
        buffer.writeByte( this.windowId );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readUnsignedVarLong();
        this.stack = readItemStack( buffer );
        this.slot = buffer.readByte();
        this.selectedSlot = buffer.readByte();
        this.windowId = buffer.readByte();
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public byte getSlot() {
        return slot;
    }

    public void setSlot(byte slot) {
        this.slot = slot;
    }

    public byte getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(byte selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public byte getWindowId() {
        return windowId;
    }

    public void setWindowId(byte windowId) {
        this.windowId = windowId;
    }
}
