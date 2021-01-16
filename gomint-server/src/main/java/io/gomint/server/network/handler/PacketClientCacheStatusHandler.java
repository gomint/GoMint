package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketClientCacheStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author HerryYT
 * @version 1.0
 */
public class PacketClientCacheStatusHandler implements PacketHandler<PacketClientCacheStatus> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketClientCacheStatusHandler.class);

    @Override
    public void handle(PacketClientCacheStatus packet, long currentTimeMillis, PlayerConnection connection) {
        LOGGER.debug("Setting client caching status to {}", packet.isEnabled());
        connection.setCachingSupported(packet.isEnabled() && connection.getServer().serverConfig().enableClientCache());
    }

}
