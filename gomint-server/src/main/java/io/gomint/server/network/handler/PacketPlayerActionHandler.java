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
                if ( !connection.getEntity().swimming() ) {
                    PlayerSwimEvent playerSwimEvent = new PlayerSwimEvent( connection.getEntity(), true );
                    connection.getServer().pluginManager().callEvent( playerSwimEvent );
                    if ( playerSwimEvent.cancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().swimming( playerSwimEvent.newStatus() );
                    }
                }

                break;

            case STOP_SWIMMING:
                if ( connection.getEntity().swimming() ) {
                    PlayerSwimEvent playerSwimEvent = new PlayerSwimEvent( connection.getEntity(), false );
                    connection.getServer().pluginManager().callEvent( playerSwimEvent );
                    if ( playerSwimEvent.cancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().swimming( playerSwimEvent.newStatus() );
                    }
                }

                break;

            case START_SPIN_ATTACK:
                if ( !connection.getEntity().spinning() ) {
                    PlayerSpinEvent playerSpinEvent = new PlayerSpinEvent( connection.getEntity(), true );
                    connection.getServer().pluginManager().callEvent( playerSpinEvent );
                    if ( playerSpinEvent.cancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().spinning( playerSpinEvent.newStatus() );
                    }
                }

                break;

            case STOP_SPIN_ATTACK:
                if ( connection.getEntity().spinning() ) {
                    PlayerSpinEvent playerSpinEvent = new PlayerSpinEvent( connection.getEntity(), false );
                    connection.getServer().pluginManager().callEvent( playerSpinEvent );
                    if ( playerSpinEvent.cancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().spinning( playerSpinEvent.newStatus() );
                    }
                }

                break;

            case START_BREAK:
                // TODO: MJ BUG / 1.2.13 / Client sends multiple START_BREAK -> ABORT_BREAK -> START_BREAK in the same tick
                if ( alreadyFired( connection ) ) {
                    if ( connection.isStartBreakResult() ) {
                        handleBreakStart( connection, currentTimeMillis, packet );
                    }

                    return;
                }

                // Sanity checks (against crashes)
                if ( connection.getEntity().canInteract( packet.getPosition().toVector().add( .5f, .5f, .5f ), 13 ) ) {
                    PlayerInteractEvent event = connection.getServer()
                        .pluginManager().callEvent( new PlayerInteractEvent( connection.getEntity(),
                            PlayerInteractEvent.ClickType.LEFT, connection.getEntity().world().blockAt( packet.getPosition() ) ) );

                    connection.setStartBreakResult( !event.cancelled() && connection.getEntity().startBreak() == 0 );

                    if ( !event.cancelled() && connection.getEntity().startBreak() == 0 ) {
                        handleBreakStart( connection, currentTimeMillis, packet );
                    }

                } else {
                    connection.setStartBreakResult( false );
                }

                break;

            case ABORT_BREAK:
            case STOP_BREAK:
                // Send abort break animation
                if ( connection.getEntity().breakVector() != null ) {
                    connection.getEntity().world().sendLevelEvent( connection.getEntity().breakVector().toVector(), LevelEvent.BLOCK_STOP_BREAK, 0 );
                }

                // Reset when abort
                if ( packet.getAction() == PacketPlayerAction.PlayerAction.ABORT_BREAK ) {
                    connection.getEntity().breakVector( null );
                }

                if ( connection.getEntity().breakVector() == null ) {
                    // This happens when instant break is enabled
                    connection.getEntity().setBreakTime( 0 );
                    connection.getEntity().setStartBreak( 0 );
                    return;
                }

                connection.getEntity().setBreakTime( ( currentTimeMillis - connection.getEntity().startBreak() ) );
                connection.getEntity().setStartBreak( 0 );
                break;

            case START_SNEAK:
                if ( !connection.getEntity().sneaking() ) {
                    PlayerToggleSneakEvent playerToggleSneakEvent = new PlayerToggleSneakEvent( connection.getEntity(), true );
                    connection.getServer().pluginManager().callEvent( playerToggleSneakEvent );
                    if ( playerToggleSneakEvent.cancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().sneaking( true );
                    }
                }

                break;

            case STOP_SNEAK:
                if ( connection.getEntity().sneaking() ) {
                    PlayerToggleSneakEvent playerToggleSneakEvent = new PlayerToggleSneakEvent( connection.getEntity(), false );
                    connection.getServer().pluginManager().callEvent( playerToggleSneakEvent );
                    if ( playerToggleSneakEvent.cancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().sneaking( false );
                    }
                }

                break;

            case START_SPRINT:
                if ( !connection.getEntity().sprinting() ) {
                    PlayerToggleSprintEvent playerToggleSprintEvent = new PlayerToggleSprintEvent( connection.getEntity(), true );
                    connection.getServer().pluginManager().callEvent( playerToggleSprintEvent );
                    if ( playerToggleSprintEvent.cancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().sprinting( true );
                    }
                }

                break;

            case STOP_SPRINT:
                if ( connection.getEntity().sprinting() ) {
                    PlayerToggleSprintEvent playerToggleSprintEvent = new PlayerToggleSprintEvent( connection.getEntity(), false );
                    connection.getServer().pluginManager().callEvent( playerToggleSprintEvent );
                    if ( playerToggleSprintEvent.cancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().sprinting( false );
                    }
                }

                break;

            case CONTINUE_BREAK:
                // Broadcast break effects
                if ( connection.getEntity().breakVector() != null ) {
                    Block block = connection.getEntity().world().blockAt( connection.getEntity().breakVector() );
                    int runtimeId = block.runtimeId();

                    connection.getEntity().world().sendLevelEvent(
                        connection.getEntity().breakVector().toVector(),
                        LevelEvent.PARTICLE_PUNCH_BLOCK,
                        runtimeId | ( packet.getFace().ordinal() << 24 ) );
                }

                break;

            case JUMP:
                connection.getEntity().jump();
                break;

            case RESPAWN:
                connection.getEntity().respawn();
                break;

            case START_GLIDE:
                // Accept client value (to get the dirty state in the metadata)
                if ( !connection.getEntity().gliding() ) {
                    PlayerToggleGlideEvent playerToggleGlideEvent = new PlayerToggleGlideEvent( connection.getEntity(), true );
                    connection.getEntity().world().getServer().pluginManager().callEvent( playerToggleGlideEvent );
                    if ( playerToggleGlideEvent.cancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().gliding( true );
                    }
                }

                break;

            case STOP_GLIDE:
                if ( connection.getEntity().gliding() ) {
                    PlayerToggleGlideEvent playerToggleGlideEvent = new PlayerToggleGlideEvent( connection.getEntity(), false );
                    connection.getEntity().world().getServer().pluginManager().callEvent( playerToggleGlideEvent );
                    if ( playerToggleGlideEvent.cancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().gliding( false );
                    }
                }

                break;

            default:
                LOGGER.warn( "Unhandled action: " + packet );
                break;
        }
    }

    private void handleBreakStart( PlayerConnection connection, long currentTimeMillis, PacketPlayerAction packet ) {
        connection.getEntity().breakVector( packet.getPosition() );
        connection.getEntity().setStartBreak( currentTimeMillis );

        Block block = connection.getEntity().world().blockAt( packet.getPosition() );

        if ( !block.side(packet.getFace()).punch( connection.getEntity() ) ) {
            long breakTime = block.finalBreakTime( connection.getEntity().inventory().itemInHand(), connection.getEntity() );
            LOGGER.debug( "Sending break time {} ms", breakTime );

            // Tell the client which break time we want
            if ( breakTime > 0 ) {
                connection.getEntity().world().sendLevelEvent( packet.getPosition().toVector(),
                    LevelEvent.BLOCK_START_BREAK, (int) ( 65536 / ( breakTime / Values.CLIENT_TICK_MS ) ) );
            }
        }
    }

    private boolean alreadyFired( PlayerConnection connection ) {
        if ( connection.isHadStartBreak() ) {
            return true;
        }

        connection.setHadStartBreak( true );
        return false;
    }

}
