package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.player.ResourcePackInfo;
import io.gomint.server.resource.ResourceResponseStatus;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketResourcePackResponse extends Packet {

    private ResourceResponseStatus status;
    private ResourcePackInfo info;

    public PacketResourcePackResponse() {
        super( Protocol.PACKET_RESOURCEPACK_RESPONSE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeByte( (byte) ( (byte) this.status.ordinal() + 1 ) );
        buffer.writeLShort( (short) 0 );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.status = ResourceResponseStatus.valueOf( buffer.readByte() );
        this.info = new ResourcePackInfo();

        int count = buffer.readLShort();
        for ( int i = 0; i < count; i++ ) {
            this.info.addResourcePack( buffer.readString(), buffer.readString() );
        }
    }

    public ResourceResponseStatus getStatus() {
        return this.status;
    }

    public void setStatus(ResourceResponseStatus status) {
        this.status = status;
    }

    public ResourcePackInfo getInfo() {
        return this.info;
    }

    public void setInfo(ResourcePackInfo info) {
        this.info = info;
    }
}
