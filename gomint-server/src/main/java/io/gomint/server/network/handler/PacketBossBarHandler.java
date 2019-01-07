package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketBossBar;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketBossBarHandler implements PacketHandler<PacketBossBar> {
    @Override
    public void handle(PacketBossBar packet, long currentTimeMillis, PlayerConnection connection) throws Exception {
        // No OP
    }
}
