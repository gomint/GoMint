package io.gomint.server.network.handler;

import io.gomint.event.player.*;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketPlayerAction;
import io.gomint.server.util.Values;
import io.gomint.server.world.LevelEvent;
import io.gomint.server.world.block.Block;
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
            case START_SWIMMING:
                if ( !connection.entity().swimming() ) {
                    PlayerSwimEvent playerSwimEvent = new PlayerSwimEvent( connection.entity(), true );
                    connection.server().pluginManager().callEvent( playerSwimEvent );
                    if ( playerSwimEvent.cancelled() ) {
                        connection.entity().sendData( connection.entity() );
                    } else {
                        connection.entity().swimming( playerSwimEvent.newStatus() );
                    }
                }

                break;

            case STOP_SWIMMING:
                if ( connection.entity().swimming() ) {
                    PlayerSwimEvent playerSwimEvent = new PlayerSwimEvent( connection.entity(), false );
                    connection.server().pluginManager().callEvent( playerSwimEvent );
                    if ( playerSwimEvent.cancelled() ) {
                        connection.entity().sendData( connection.entity() );
                    } else {
                        connection.entity().swimming( playerSwimEvent.newStatus() );
                    }
                }

                break;

            case START_SPIN_ATTACK:
                if ( !connection.entity().spinning() ) {
                    PlayerSpinEvent playerSpinEvent = new PlayerSpinEvent( connection.entity(), true );
                    connection.server().pluginManager().callEvent( playerSpinEvent );
                    if ( playerSpinEvent.cancelled() ) {
                        connection.entity().sendData( connection.entity() );
                    } else {
                        connection.entity().spinning( playerSpinEvent.newStatus() );
                    }
                }

                break;

            case STOP_SPIN_ATTACK:
                if ( connection.entity().spinning() ) {
                    PlayerSpinEvent playerSpinEvent = new PlayerSpinEvent( connection.entity(), false );
                    connection.server().pluginManager().callEvent( playerSpinEvent );
                    if ( playerSpinEvent.cancelled() ) {
                        connection.entity().sendData( connection.entity() );
                    } else {
                        connection.entity().spinning( playerSpinEvent.newStatus() );
                    }
                }

                break;

            case START_BREAK:
                // TODO: MJ BUG / 1.2.13 / Client sends multiple START_BREAK -> ABORT_BREAK -> START_BREAK in the same tick
                if ( alreadyFired( connection ) ) {
                    if ( connection.startBreakResult() ) {
                        handleBreakStart( connection, currentTimeMillis, packet );
                    }

                    return;
                }

                // Sanity checks (against crashes)
                if ( connection.entity().canInteract( packet.getPosition().toVector().add( .5f, .5f, .5f ), 13 ) ) {
                    PlayerInteractEvent event = connection.server()
                        .pluginManager().callEvent( new PlayerInteractEvent( connection.entity(),
                            PlayerInteractEvent.ClickType.LEFT, connection.entity().world().blockAt( packet.getPosition() ) ) );

                    connection.startBreakResult( !event.cancelled() && connection.entity().startBreak() == 0 );

                    if ( !event.cancelled() && connection.entity().startBreak() == 0 ) {
                        handleBreakStart( connection, currentTimeMillis, packet );
                    }

                } else {
                    connection.startBreakResult( false );
                }

                break;

            case ABORT_BREAK:
            case STOP_BREAK:
                // Send abort break animation
                if ( connection.entity().breakVector() != null ) {
                    connection.entity().world().sendLevelEvent( connection.entity().breakVector().toVector(), LevelEvent.BLOCK_STOP_BREAK, 0 );
                }

                // Reset when abort
                if ( packet.getAction() == PacketPlayerAction.PlayerAction.ABORT_BREAK ) {
                    connection.entity().breakVector( null );
                }

                if ( connection.entity().breakVector() == null ) {
                    // This happens when instant break is enabled
                    connection.entity().setBreakTime( 0 );
                    connection.entity().setStartBreak( 0 );
                    return;
                }

                connection.entity().setBreakTime( ( currentTimeMillis - connection.entity().startBreak() ) );
                connection.entity().setStartBreak( 0 );
                break;

            case START_SNEAK:
                if ( !connection.entity().sneaking() ) {
                    PlayerToggleSneakEvent playerToggleSneakEvent = new PlayerToggleSneakEvent( connection.entity(), true );
                    connection.server().pluginManager().callEvent( playerToggleSneakEvent );
                    if ( playerToggleSneakEvent.cancelled() ) {
                        connection.entity().sendData( connection.entity() );
                    } else {
                        connection.entity().sneaking( true );
                    }
                }

                break;

            case STOP_SNEAK:
                if ( connection.entity().sneaking() ) {
                    PlayerToggleSneakEvent playerToggleSneakEvent = new PlayerToggleSneakEvent( connection.entity(), false );
                    connection.server().pluginManager().callEvent( playerToggleSneakEvent );
                    if ( playerToggleSneakEvent.cancelled() ) {
                        connection.entity().sendData( connection.entity() );
                    } else {
                        connection.entity().sneaking( false );
                    }
                }

                break;

            case START_SPRINT:
                if ( !connection.entity().sprinting() ) {
                    PlayerToggleSprintEvent playerToggleSprintEvent = new PlayerToggleSprintEvent( connection.entity(), true );
                    connection.server().pluginManager().callEvent( playerToggleSprintEvent );
                    if ( playerToggleSprintEvent.cancelled() ) {
                        connection.entity().sendData( connection.entity() );
                    } else {
                        connection.entity().sprinting( true );
                    }
                }

                break;

            case STOP_SPRINT:
                if ( connection.entity().sprinting() ) {
                    PlayerToggleSprintEvent playerToggleSprintEvent = new PlayerToggleSprintEvent( connection.entity(), false );
                    connection.server().pluginManager().callEvent( playerToggleSprintEvent );
                    if ( playerToggleSprintEvent.cancelled() ) {
                        connection.entity().sendData( connection.entity() );
                    } else {
                        connection.entity().sprinting( false );
                    }
                }

                break;

            case CONTINUE_BREAK:
                // Broadcast break effects
                if ( connection.entity().breakVector() != null ) {
                    Block block = connection.entity().world().blockAt( connection.entity().breakVector() );
                    int runtimeId = block.runtimeId();

                    connection.entity().world().sendLevelEvent(
                        connection.entity().breakVector().toVector(),
                        LevelEvent.PARTICLE_PUNCH_BLOCK,
                        runtimeId | ( packet.getFace().ordinal() << 24 ) );
                }

                break;

            case JUMP:
                connection.entity().jump();
                break;

            case RESPAWN:
                connection.entity().respawn();
                break;

            case START_GLIDE:
                // Accept client value (to get the dirty state in the metadata)
                if ( !connection.entity().gliding() ) {
                    PlayerToggleGlideEvent playerToggleGlideEvent = new PlayerToggleGlideEvent( connection.entity(), true );
                    connection.entity().world().server().pluginManager().callEvent( playerToggleGlideEvent );
                    if ( playerToggleGlideEvent.cancelled() ) {
                        connection.entity().sendData( connection.entity() );
                    } else {
                        connection.entity().gliding( true );
                    }
                }

                break;

            case STOP_GLIDE:
                if ( connection.entity().gliding() ) {
                    PlayerToggleGlideEvent playerToggleGlideEvent = new PlayerToggleGlideEvent( connection.entity(), false );
                    connection.entity().world().server().pluginManager().callEvent( playerToggleGlideEvent );
                    if ( playerToggleGlideEvent.cancelled() ) {
                        connection.entity().sendData( connection.entity() );
                    } else {
                        connection.entity().gliding( false );
                    }
                }

                break;

            default:
                LOGGER.warn( "Unhandled action: " + packet );
                break;
        }
    }

    private void handleBreakStart( PlayerConnection connection, long currentTimeMillis, PacketPlayerAction packet ) {
        connection.entity().breakVector( packet.getPosition() );
        connection.entity().setStartBreak( currentTimeMillis );

        Block block = connection.entity().world().blockAt( packet.getPosition() );

        if ( !block.side(packet.getFace()).punch( connection.entity() ) ) {
            long breakTime = block.finalBreakTime( connection.entity().inventory().itemInHand(), connection.entity() );
            LOGGER.debug( "Sending break time {} ms", breakTime );

            // Tell the client which break time we want
            if ( breakTime > 0 ) {
                connection.entity().world().sendLevelEvent( packet.getPosition().toVector(),
                    LevelEvent.BLOCK_START_BREAK, (int) ( 65536 / ( breakTime / Values.CLIENT_TICK_MS ) ) );
            }
        }
    }

    private boolean alreadyFired( PlayerConnection connection ) {
        if ( connection.hadStartBreak() ) {
            return true;
        }

        connection.hadStartBreak( true );
        return false;
    }

}
