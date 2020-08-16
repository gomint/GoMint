package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketAdventureSettings extends Packet {

    private int flags;
    private int commandPermission;
    private int flags2;
    private int playerPermission;
    private int customFlags;
    private long entityId;

    public PacketAdventureSettings() {
        super( Protocol.PACKET_ADVENTURE_SETTINGS );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarInt( this.flags );
        buffer.writeUnsignedVarInt( this.commandPermission );
        buffer.writeUnsignedVarInt( this.flags2 );
        buffer.writeUnsignedVarInt( this.playerPermission );
        buffer.writeUnsignedVarInt( this.customFlags );
        buffer.writeLLong( this.entityId );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.flags = buffer.readUnsignedVarInt();
        this.commandPermission = buffer.readUnsignedVarInt();
        this.flags2 = buffer.readUnsignedVarInt();
        this.playerPermission = buffer.readUnsignedVarInt();
        this.customFlags = buffer.readUnsignedVarInt();
        this.entityId = buffer.readLLong();
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getCommandPermission() {
        return commandPermission;
    }

    public void setCommandPermission(int commandPermission) {
        this.commandPermission = commandPermission;
    }

    public int getFlags2() {
        return flags2;
    }

    public void setFlags2(int flags2) {
        this.flags2 = flags2;
    }

    public int getPlayerPermission() {
        return playerPermission;
    }

    public void setPlayerPermission(int playerPermission) {
        this.playerPermission = playerPermission;
    }

    public int getCustomFlags() {
        return customFlags;
    }

    public void setCustomFlags(int customFlags) {
        this.customFlags = customFlags;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

}
