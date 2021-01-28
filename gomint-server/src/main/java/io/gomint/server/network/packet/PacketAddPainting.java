package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author HerryYT
 * @version 1.0
 */
public class PacketAddPainting extends Packet {

    private long entityId;
    private float x;
    private float y;
    private float z;
    private int direction;
    private String title;

    public PacketAddPainting() {
        super( Protocol.PACKET_ADD_PAINTING );
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        // Write runtime & entity id
        buffer.writeSignedVarLong( this.entityId );
        buffer.writeUnsignedVarLong( this.entityId );

        // Write painting position
        buffer.writeLFloat( this.x );
        buffer.writeLFloat( this.y );
        buffer.writeLFloat( this.z );

        // Write painting direction
        buffer.writeSignedVarInt( this.direction );

        // Write painting title
        buffer.writeString( this.title );
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {

    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
