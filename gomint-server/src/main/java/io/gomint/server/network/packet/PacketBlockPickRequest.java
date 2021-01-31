package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.network.Protocol;

public class PacketBlockPickRequest extends Packet {

    private BlockPosition location;
    private boolean addUserData;
    private byte hotbarSlot;

    public PacketBlockPickRequest() {
        super( Protocol.PACKET_BLOCK_PICK_REQUEST );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        writeSignedBlockPosition( this.location, buffer );
        buffer.writeBoolean( this.addUserData );
        buffer.writeByte( this.hotbarSlot );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.location = readSignedBlockPosition( buffer );
        this.addUserData = buffer.readBoolean();
        this.hotbarSlot = buffer.readByte();
    }

    public BlockPosition getLocation() {
        return this.location;
    }

    public void setLocation(BlockPosition location) {
        this.location = location;
    }

    public boolean isAddUserData() {
        return this.addUserData;
    }

    public void setAddUserData(boolean addUserData) {
        this.addUserData = addUserData;
    }

    public byte getHotbarSlot() {
        return this.hotbarSlot;
    }

    public void setHotbarSlot(byte hotbarSlot) {
        this.hotbarSlot = hotbarSlot;
    }
}
