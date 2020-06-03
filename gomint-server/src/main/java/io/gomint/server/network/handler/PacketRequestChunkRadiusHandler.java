package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketConfirmChunkRadius;
import io.gomint.server.network.packet.PacketRequestChunkRadius;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketRequestChunkRadiusHandler implements PacketHandler<PacketRequestChunkRadius> {

    @Override
    public void handle(PacketRequestChunkRadius packet, long currentTimeMillis, PlayerConnection connection ) {
        // Check if the wanted View distance is under the servers setting
        connection.getEntity().setViewDistance( packet.getChunkRadius() );
    }
}
