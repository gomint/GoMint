package io.gomint.server.network.handler;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.PlayerChatEvent;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketText;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketTextHandler implements PacketHandler<PacketText> {

    @Override
    public void handle( PacketText packet, long currentTimeMillis, PlayerConnection connection ) {
        switch ( packet.getType() ) {
            case PLAYER_CHAT:
                // Simply relay for now
                List<EntityPlayer> playerList = new ArrayList<>( connection.server().onlinePlayers() );
                PlayerChatEvent event = new PlayerChatEvent( connection.entity(), connection.entity().displayName(), packet.getMessage(), playerList );
                connection.server().pluginManager().callEvent( event );

                if ( !event.cancelled() ) {
                    packet.setSender( event.sender() );
                    packet.setMessage( event.text() );

                    for ( EntityPlayer player : playerList ) {
                        if ( player instanceof io.gomint.server.entity.EntityPlayer ) {
                            ( (io.gomint.server.entity.EntityPlayer) player ).connection().addToSendQueue( packet );
                        }
                    }
                }

                break;
        }
    }

}
