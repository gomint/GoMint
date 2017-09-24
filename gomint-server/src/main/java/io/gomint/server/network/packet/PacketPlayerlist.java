package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.player.PlayerSkin;
import io.gomint.server.network.Protocol;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketPlayerlist extends Packet {

    private byte mode;
    private List<Entry> entries;

    public PacketPlayerlist() {
        super( Protocol.PACKET_PLAYER_LIST );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {
        buffer.writeByte( this.mode );
        buffer.writeUnsignedVarInt( this.entries.size() );

        if ( this.mode == 0 ) {
            for ( Entry entry : this.entries ) {
                buffer.writeUUID( entry.getUuid() );
                buffer.writeSignedVarLong( entry.getEntityId() );
                buffer.writeString( entry.getName() );

                buffer.writeString( entry.getSkin().getName() );

                // Raw skin data
                buffer.writeUnsignedVarInt( entry.getSkin().getRawData().length );
                buffer.writeBytes( entry.getSkin().getRawData() );

                // Cape data
                if ( entry.skin.getCapeData() != null ) {
                    buffer.writeUnsignedVarInt( entry.skin.getCapeData().length );
                    buffer.writeBytes( entry.skin.getCapeData() );
                } else {
                    buffer.writeUnsignedVarInt( 0 );
                }

                // Geometry name
                buffer.writeString( entry.skin.getGeometryName() );

                // Geometry data
                buffer.writeUnsignedVarInt( entry.skin.getGeometryData().length );
                buffer.writeBytes( entry.skin.getGeometryData() );

                // xbox user id
                buffer.writeString( entry.xboxId );
            }
        } else {
            for ( Entry entry : this.entries ) {
                buffer.writeUUID( entry.getUuid() );
            }
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer ) {

    }

    @Data
    @AllArgsConstructor
    public static class Entry {
        private final UUID uuid;
        private long entityId = 0;
        private String name = "";
        private String xboxId = "";
        private PlayerSkin skin;
    }

}
