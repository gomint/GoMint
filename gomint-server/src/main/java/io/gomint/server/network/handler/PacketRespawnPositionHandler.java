package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketRespawnPosition;
import lombok.Data;

/**
 * @author HerryYT
 * @version 1.0
 */
@Data
public class PacketRespawnPositionHandler implements PacketHandler<PacketRespawnPosition> {

    @Override
    public void handle(PacketRespawnPosition packet, long currentTimeMillis, PlayerConnection connection) {
        // Client ready
        if ( packet.getState() == 2 ) {
            PacketRespawnPosition packetRespawnPosition = new PacketRespawnPosition();
            packetRespawnPosition.setPosition(connection.getEntity().getSpawnLocation().add(0, connection.getEntity().getEyeHeight(), 0));
            packetRespawnPosition.setEntityId(connection.getEntity().getEntityId());
            packetRespawnPosition.setState( (byte) 1 );  // TODO
            connection.addToSendQueue(packetRespawnPosition);
        }
    }
}
