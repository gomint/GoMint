package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketContainerOpen;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketContainerOpenHandler implements PacketHandler<PacketContainerOpen> {

    @Override
    public void handle( PacketContainerOpen packet, long currentTimeMillis, PlayerConnection connection ) {
        System.out.println( packet );
    }

}
