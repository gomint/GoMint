package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.entity.EntityLink;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.network.Protocol;

import java.util.List;
import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.1
 */
public class PacketSpawnPlayer extends Packet {

    private UUID uuid;
    private String name;
    private long entityId;
    private long runtimeEntityId;
    private String platformChatId;

    private float x;
    private float y;
    private float z;

    private float velocityX;
    private float velocityY;
    private float velocityZ;

    private float pitch;
    private float headYaw;
    private float yaw;

    private ItemStack<?> itemInHand;
    private MetadataContainer metadataContainer;

    // Some adventure stuff? Yep this is adventure setting stuff
    private int flags;
    private int commandPermission;
    private int flags2;
    private int playerPermission;
    private int customFlags;

    private List<EntityLink> links;
    private String deviceId;

    private int buildPlatform;

    /**
     * Create a new spawn player packet
     */
    public PacketSpawnPlayer() {
        super( Protocol.PACKET_SPAWN_PLAYER );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUUID( this.uuid );
        buffer.writeString( this.name );

        buffer.writeSignedVarLong( this.entityId );
        buffer.writeUnsignedVarLong( this.runtimeEntityId );

        buffer.writeString( this.platformChatId );

        buffer.writeLFloat( this.x );
        buffer.writeLFloat( this.y );
        buffer.writeLFloat( this.z );

        buffer.writeLFloat( this.velocityX );
        buffer.writeLFloat( this.velocityY );
        buffer.writeLFloat( this.velocityZ );

        buffer.writeLFloat( this.pitch );
        buffer.writeLFloat( this.yaw );
        buffer.writeLFloat( this.headYaw );

        writeItemStack( this.itemInHand, buffer );
        this.metadataContainer.serialize( buffer );

        buffer.writeUnsignedVarInt( this.flags );
        buffer.writeUnsignedVarInt( this.commandPermission );
        buffer.writeUnsignedVarInt( this.flags2 );
        buffer.writeUnsignedVarInt( this.playerPermission );
        buffer.writeUnsignedVarInt( this.customFlags );

        buffer.writeLLong( this.entityId );

        writeEntityLinks( this.links, buffer );

        buffer.writeString( this.deviceId );
        buffer.writeLInt(this.buildPlatform);
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        buffer.readUUID();
        System.out.println( buffer.readString() );
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public long getRuntimeEntityId() {
        return this.runtimeEntityId;
    }

    public void setRuntimeEntityId(long runtimeEntityId) {
        this.runtimeEntityId = runtimeEntityId;
    }

    public String getPlatformChatId() {
        return this.platformChatId;
    }

    public void setPlatformChatId(String platformChatId) {
        this.platformChatId = platformChatId;
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

    public float getVelocityX() {
        return this.velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return this.velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getVelocityZ() {
        return this.velocityZ;
    }

    public void setVelocityZ(float velocityZ) {
        this.velocityZ = velocityZ;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getHeadYaw() {
        return this.headYaw;
    }

    public void setHeadYaw(float headYaw) {
        this.headYaw = headYaw;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public ItemStack<?> getItemInHand() {
        return this.itemInHand;
    }

    public void setItemInHand(ItemStack<?> itemInHand) {
        this.itemInHand = itemInHand;
    }

    public MetadataContainer getMetadataContainer() {
        return this.metadataContainer;
    }

    public void setMetadataContainer(MetadataContainer metadataContainer) {
        this.metadataContainer = metadataContainer;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getCommandPermission() {
        return this.commandPermission;
    }

    public void setCommandPermission(int commandPermission) {
        this.commandPermission = commandPermission;
    }

    public int getFlags2() {
        return this.flags2;
    }

    public void setFlags2(int flags2) {
        this.flags2 = flags2;
    }

    public int getPlayerPermission() {
        return this.playerPermission;
    }

    public void setPlayerPermission(int playerPermission) {
        this.playerPermission = playerPermission;
    }

    public int getCustomFlags() {
        return this.customFlags;
    }

    public void setCustomFlags(int customFlags) {
        this.customFlags = customFlags;
    }

    public List<EntityLink> getLinks() {
        return this.links;
    }

    public void setLinks(List<EntityLink> links) {
        this.links = links;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getBuildPlatform() {
        return this.buildPlatform;
    }

    public void setBuildPlatform(int buildPlatform) {
        this.buildPlatform = buildPlatform;
    }
}
