package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketAddItemEntity extends Packet {

    private long entityId;
    private ItemStack<?> itemStack;
    private float x;
    private float y;
    private float z;
    private float motionX;
    private float motionY;
    private float motionZ;
    private MetadataContainer metadata;
    private boolean fromFishing;

    public PacketAddItemEntity() {
        super( Protocol.PACKET_ADD_ITEM_ENTITY );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        // PE has a system of runtime entity ids
        buffer.writeSignedVarLong( this.entityId );
        buffer.writeUnsignedVarLong( this.entityId );

        // Write the item for the drop
        writeItemStack( this.itemStack, buffer );

        // Write position
        buffer.writeLFloat( this.x );
        buffer.writeLFloat( this.y );
        buffer.writeLFloat( this.z );

        // Write motion
        buffer.writeLFloat( this.motionX );
        buffer.writeLFloat( this.motionY );
        buffer.writeLFloat( this.motionZ );

        this.metadata.serialize( buffer );

        // Write fromFishing
        buffer.writeBoolean( this.fromFishing );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public ItemStack<?> getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack<?> itemStack) {
        this.itemStack = itemStack;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getMotionX() {
        return motionX;
    }

    public void setMotionX(float motionX) {
        this.motionX = motionX;
    }

    public float getMotionY() {
        return motionY;
    }

    public void setMotionY(float motionY) {
        this.motionY = motionY;
    }

    public float getMotionZ() {
        return motionZ;
    }

    public void setMotionZ(float motionZ) {
        this.motionZ = motionZ;
    }

    public MetadataContainer getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataContainer metadata) {
        this.metadata = metadata;
    }

    public boolean isFromFishing() {
        return fromFishing;
    }

    public void setFromFishing(boolean fromFishing) {
        this.fromFishing = fromFishing;
    }
}
