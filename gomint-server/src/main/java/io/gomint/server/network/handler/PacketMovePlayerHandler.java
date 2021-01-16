package io.gomint.server.network.handler;

import io.gomint.math.Location;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketMovePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketMovePlayerHandler implements PacketHandler<PacketMovePlayer> {

    private static final Logger LOGGER = LoggerFactory.getLogger( PacketMovePlayer.class );

    @Override
    public void handle( PacketMovePlayer packet, long currentTimeMillis, PlayerConnection connection ) {
        EntityPlayer entity = connection.getEntity();
        Location to = entity.location();
        to.x( packet.getX() );
        to.y( packet.getY() - entity.eyeHeight() ); // Subtract eye height since client sends it at the eyes
        to.z( packet.getZ() );
        to.headYaw( packet.getHeadYaw() );
        to.yaw( packet.getYaw() );
        to.pitch( packet.getPitch() );

        // Does the entity have a teleport open?
        if ( connection.getEntity().teleportPosition() != null ) {
            if ( connection.getEntity().teleportPosition().distanceSquared( to ) > 0.2 ) {
                LOGGER.warn( "Player {} did not teleport to {}", connection.getEntity().name(), connection.getEntity().teleportPosition(), to );
                connection.sendMovePlayer( connection.getEntity().teleportPosition() );
                return;
            } else {
                connection.getEntity().setTeleportPosition( null );
            }
        }

        Location from = entity.location();

        // The packet did not contain any movement? skip it
        if ( from.x() - to.x() == 0 &&
            from.y() - to.y() == 0 &&
            from.z() - to.z() == 0 &&
            from.headYaw() - to.headYaw() == 0 &&
            from.yaw() - to.yaw() == 0 &&
            from.pitch() - to.pitch() == 0 ) {
            return;
        }

        connection.getEntity().setNextMovement( to );
    }

}
