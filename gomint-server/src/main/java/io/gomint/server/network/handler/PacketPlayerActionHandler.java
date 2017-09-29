package io.gomint.server.network.handler;

import io.gomint.event.player.PlayerInteractEvent;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketPlayerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketPlayerActionHandler implements PacketHandler<PacketPlayerAction> {

    private static final Logger LOGGER = LoggerFactory.getLogger( PacketPlayerActionHandler.class );

    @Override
    public void handle( PacketPlayerAction packet, long currentTimeMillis, PlayerConnection connection ) {
        switch ( packet.getAction() ) {
            case START_BREAK:
                // Sanity checks (against crashes)
                if ( connection.getEntity().canInteract( packet.getPosition().toVector().add( .5f, .5f, .5f ), 13 ) ) {
                    PlayerInteractEvent event = connection.getServer()
                            .getPluginManager().callEvent( new PlayerInteractEvent( connection.getEntity(),
                                    PlayerInteractEvent.ClickType.LEFT, connection.getEntity().getWorld().getBlockAt( packet.getPosition() ) ) );

                    if ( !event.isCancelled() ) {
                        if ( connection.getEntity().getStartBreak() == 0 ) {
                            connection.getEntity().setBreakVector( packet.getPosition() );
                            connection.getEntity().setStartBreak( currentTimeMillis );
                        }
                    }
                }

                break;

            case ABORT_BREAK:
                connection.getEntity().setBreakVector( null );

            case STOP_BREAK:
                if ( connection.getEntity().getBreakVector() == null ) {
                    // This happens when instant break is enabled
                    connection.getEntity().setBreakTime( 0 );
                    connection.getEntity().setStartBreak( 0 );
                    return;
                }

                connection.getEntity().setBreakTime( ( currentTimeMillis - connection.getEntity().getStartBreak() ) );
                connection.getEntity().setStartBreak( 0 );
                break;
            case START_SNEAK:
                connection.getEntity().setSneaking( true );
                break;
            case STOP_SNEAK:
                connection.getEntity().setSneaking( false );
                break;
            case START_SPRINT:
                connection.getEntity().setSprinting( true );
                break;
            case STOP_SPRINT:
                connection.getEntity().setSprinting( false );
                break;
            case JUMP:
                // TODO: Decide what todo with this information
                break;
            default:
                LOGGER.warn( "Unhandled action: " + packet );
                break;
        }
    }

}
