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
                playerAnimationEvent = new PlayerAnimationEvent( connection.getEntity(), PlayerAnimationEvent.Animation.SWING );
                break;
            default:
                return;
        }

        connection.getServer().getPluginManager().callEvent( playerAnimationEvent );
        if ( !playerAnimationEvent.isCancelled() ) {
            for ( Entity entity : connection.getEntity().getAttachedEntities() ) {
                if ( entity instanceof EntityPlayer ) {
                    ( (EntityPlayer) entity ).getConnection().addToSendQueue( packet );
                }
            }
        }
    }

}
