package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketContainerClose;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketContainerCloseHandler implements PacketHandler<PacketContainerClose> {

    @Override
    public void handle(PacketContainerClose packet, long currentTimeMillis, PlayerConnection connection) {
        connection.entity().closeInventory(packet.getWindowId(), packet.isServerSided());
    }

}
