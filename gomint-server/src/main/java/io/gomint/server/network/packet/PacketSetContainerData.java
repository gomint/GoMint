package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketSetContainerData extends Packet {

    private byte windowId;
    private int key;
    private int value;

    public PacketSetContainerData() {
        super( Protocol.PACKET_SET_CONTAINER_DATA );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeByte( this.windowId );
        buffer.writeSignedVarInt( this.key );
        buffer.writeSignedVarInt( this.value );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public byte getWindowId() {
        return windowId;
    }

    public void setWindowId(byte windowId) {
        this.windowId = windowId;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
