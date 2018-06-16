package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketSetGamemode extends Packet {

    private int gameMode;

    public PacketSetGamemode() {
        super( Protocol.PACKET_SET_GAMEMODE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarInt( this.gameMode );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
