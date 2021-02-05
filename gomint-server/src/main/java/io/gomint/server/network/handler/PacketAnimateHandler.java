package io.gomint.server.network.handler;

import io.gomint.entity.Entity;
import io.gomint.event.player.PlayerAnimationEvent;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketAnimate;


/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketAnimateHandler implements PacketHandler<PacketAnimate> {

    @Override
    public void handle( PacketAnimate packet, long currentTimeMillis, PlayerConnection connection ) {
        PlayerAnimationEvent playerAnimationEvent = null;


        switch ( packet.getPlayerAnimation() ) {
            case SWING:
                playerAnimationEvent = new PlayerAnimationEvent( connection.entity(), PlayerAnimationEvent.Animation.SWING );
                break;
            default:
                return;
        }

        connection.server().pluginManager().callEvent( playerAnimationEvent );
        if ( !playerAnimationEvent.cancelled() ) {
            for ( Entity<?> entity : connection.entity().getAttachedEntities() ) {
                if ( entity instanceof EntityPlayer ) {
                    ( (EntityPlayer) entity ).connection().addToSendQueue( packet );
                }
            }
        }
    }

}
