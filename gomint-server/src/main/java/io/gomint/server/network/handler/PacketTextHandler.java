package io.gomint.server.network.handler;

import io.gomint.entity.Player;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketText;

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
                for ( Player player : connection.getServer().getPlayers() ) {
                    if ( player instanceof EntityPlayer ) {
                        ( (EntityPlayer) player ).getConnection().addToSendQueue( packet );
                    }
                }
        }

        System.out.println( packet );
    }

}
