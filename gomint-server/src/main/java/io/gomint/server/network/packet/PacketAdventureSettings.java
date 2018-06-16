package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
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

}
