package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketWorldSoundEvent;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketWorldSoundEventHandler implements PacketHandler<PacketWorldSoundEvent> {

    @Override
    public void handle( PacketWorldSoundEvent packet, long currentTimeMillis, PlayerConnection connection ) {
        switch ( packet.getType() ) {
            case STOP_JUKEBOX:
                return;
        }

        // Relay to all other players which can see this entity
        connection.entity().world().sendToVisible( packet.getPosition().toBlockPosition(), packet, entity -> true );
    }

}
