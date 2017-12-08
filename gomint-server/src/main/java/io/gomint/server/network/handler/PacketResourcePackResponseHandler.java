package io.gomint.server.network.handler;

import io.gomint.event.player.PlayerPreJoinEvent;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.PlayerConnectionState;
import io.gomint.server.network.packet.PacketResourcePackResponse;
import io.gomint.server.network.packet.PacketResourcePackStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketResourcePackResponseHandler implements PacketHandler<PacketResourcePackResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger( PacketResourcePackResponseHandler.class );

    @Override
    public void handle( PacketResourcePackResponse packet, long currentTimeMillis, PlayerConnection connection ) {
        // TODO: Implement resource pack sending
        switch ( packet.getStatus() ) {
            case HAVE_ALL_PACKS:
                LOGGER.debug( "Login state: HAVE_ALL_PACKS reached" );

                PacketResourcePackStack packetResourcePackStack = new PacketResourcePackStack();
                connection.send( packetResourcePackStack );
                break;

            case COMPLETED:
                LOGGER.debug( "Login state: COMPLETED reached" );

                // Proceed with login
                connection.setState( PlayerConnectionState.LOGIN );
                LOGGER.info( "Logging in as " + connection.getEntity().getName() );

                PlayerPreJoinEvent playerPreJoinEvent = new PlayerPreJoinEvent( connection.getEntity() );
                connection.getServer().getPluginManager().callEvent( playerPreJoinEvent );
                if ( !playerPreJoinEvent.isCancelled() ) {
                    connection.getEntity().prepareEntity();
                }

                break;
        }
    }
    
}
