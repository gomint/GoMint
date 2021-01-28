package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketModalResponse;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketModalResponseHandler implements PacketHandler<PacketModalResponse> {

    @Override
    public void handle( PacketModalResponse packet, long currentTimeMillis, PlayerConnection connection ) {
        connection.entity().parseGUIResponse( packet.getFormId(), packet.getJson() );
    }

}
