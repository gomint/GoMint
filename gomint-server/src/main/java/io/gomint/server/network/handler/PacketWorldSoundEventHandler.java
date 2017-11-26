package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketWorldSoundEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketWorldSoundEventHandler implements PacketHandler<PacketWorldSoundEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger( PacketWorldSoundEventHandler.class );

    @Override
    public void handle( PacketWorldSoundEvent packet, long currentTimeMillis, PlayerConnection connection ) {
        LOGGER.info( packet.toString() );

        // Relay to all other players which can see this entity
        connection.getEntity().getWorld().sendToVisible( packet.getPosition().toBlockPosition(), packet, entity -> true );
    }

}
