package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketHotbar;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketHotbarHandler implements PacketHandler<PacketHotbar> {

    @Override
    public void handle( PacketHotbar packet, long currentTimeMillis, PlayerConnection connection ) {
        // I don't know what the fuck this packet does. So i simply don't care about this packet.
    }

}
