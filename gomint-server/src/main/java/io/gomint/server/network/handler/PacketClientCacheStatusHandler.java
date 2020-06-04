package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketClientCacheStatus;

/**
 * @author HerryYT
 * @version 1.0
 */
public class PacketClientCacheStatusHandler implements PacketHandler<PacketClientCacheStatus> {

    @Override
    public void handle(PacketClientCacheStatus packet, long currentTimeMillis, PlayerConnection connection) {
        // No documentation, added to stop Sentry spam.
    }
}
