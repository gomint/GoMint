package io.gomint.server.network.handler;

import io.gomint.server.entity.Entity;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketWorldSoundEvent;

import java.util.function.Predicate;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketWorldSoundEventHandler implements PacketHandler<PacketWorldSoundEvent> {

    @Override
    public void handle( PacketWorldSoundEvent packet, long currentTimeMillis, PlayerConnection connection ) {
        // Relay to all other players which can see this entity
        connection.getEntity().getWorld().sendToVisible( packet.getPosition(), packet, new Predicate<Entity>() {
            @Override
            public boolean test( Entity entity ) {
                return !entity.equals( connection.getEntity() );
            }
        } );
    }

}
