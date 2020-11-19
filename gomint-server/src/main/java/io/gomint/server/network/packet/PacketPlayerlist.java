package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.player.DeviceInfo;
import io.gomint.player.PlayerSkin;
import io.gomint.server.entity.passive.EntityHuman;
import io.gomint.server.network.Protocol;

import java.util.List;
import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketPlayerlist extends Packet {

    private byte mode;
    private List<Entry> entries;

    public PacketPlayerlist() {
        super( Protocol.PACKET_PLAYER_LIST );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeByte( this.mode );
        buffer.writeUnsignedVarInt( this.entries.size() );

        if ( this.mode == 0 ) {
            for ( Entry entry : this.entries ) {
                buffer.writeUUID( entry.getUuid() );
                buffer.writeSignedVarLong( entry.getEntityId() );
                buffer.writeString( entry.getName() );

                // xbox user id
                buffer.writeString( entry.xboxId );

                // Player device UUID
                buffer.writeString( entry.deviceInfo.getDeviceId() );

                // Player device ID
                buffer.writeLInt( entry.deviceInfo.getOs().getId() );

                // Write skin
                writeSerializedSkin( (io.gomint.server.player.PlayerSkin) entry.skin, buffer );

                // Is teacher
                buffer.writeBoolean( false );

                // Is host
                buffer.writeBoolean( false );
            }
        } else {
            for ( Entry entry : this.entries ) {
                buffer.writeUUID( entry.getUuid() );
            }
        }

        if ( this.mode == 0 ) {
            //for (int i = 0; i < this.entries.size(); i++) {
                // Is skin trusted
                buffer.writeBoolean( true );
            //}
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public static class Entry {
        private final UUID uuid;
        private long entityId = 0;
        private String name = "";
        private DeviceInfo deviceInfo;
        private String xboxId = "";
        private PlayerSkin skin;

        public Entry( EntityHuman human ) {
            this( human.getUUID(), human.getEntityId(), human.getPlayerListName(), human.getDeviceInfo() , human.getXboxID(), human.getSkin() );
        }

        public Entry(UUID uuid, long entityId, String playerListName, DeviceInfo deviceInfo, String xboxID, PlayerSkin skin) {
            this.uuid = uuid;
            this.entityId = entityId;
            this.name = playerListName;
            this.deviceInfo = deviceInfo;
            this.xboxId = xboxID;
            this.skin = skin;
        }

        public UUID getUuid() {
            return uuid;
        }

        public long getEntityId() {
            return entityId;
        }

        public void setEntityId(long entityId) {
            this.entityId = entityId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DeviceInfo getDeviceInfo() {
            return deviceInfo;
        }

        public void setDeviceInfo(DeviceInfo deviceInfo) {
            this.deviceInfo = deviceInfo;
        }

        public String getXboxId() {
            return xboxId;
        }

        public void setXboxId(String xboxId) {
            this.xboxId = xboxId;
        }

        public PlayerSkin getSkin() {
            return skin;
        }

        public void setSkin(PlayerSkin skin) {
            this.skin = skin;
        }
    }

}
