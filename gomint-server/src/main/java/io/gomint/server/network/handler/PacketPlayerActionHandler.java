package io.gomint.server.network.handler;

import io.gomint.event.player.*;
import io.gomint.server.enchant.EnchantmentSelector;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketPlayerAction;
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
                if ( !connection.getEntity().isSwimming() ) {
                    PlayerSwimEvent playerSwimEvent = new PlayerSwimEvent( connection.getEntity(), true );
                    connection.getServer().getPluginManager().callEvent( playerSwimEvent );
                    if ( playerSwimEvent.isCancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().setSwimming( playerSwimEvent.getNewStatus() );
                    }
                }

                break;

            case STOP_SWIMMING:
                if ( connection.getEntity().isSwimming() ) {
                    PlayerSwimEvent playerSwimEvent = new PlayerSwimEvent( connection.getEntity(), false );
                    connection.getServer().getPluginManager().callEvent( playerSwimEvent );
                    if ( playerSwimEvent.isCancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().setSwimming( playerSwimEvent.getNewStatus() );
                    }
                }

                break;

            case START_SPIN_ATTACK:
                if ( !connection.getEntity().isSpinning() ) {
                    PlayerSpinEvent playerSpinEvent = new PlayerSpinEvent( connection.getEntity(), true );
                    connection.getServer().getPluginManager().callEvent( playerSpinEvent );
                    if ( playerSpinEvent.isCancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().setSpinning( playerSpinEvent.getNewStatus() );
                    }
                }

                break;

            case STOP_SPIN_ATTACK:
                if ( connection.getEntity().isSpinning() ) {
                    PlayerSpinEvent playerSpinEvent = new PlayerSpinEvent( connection.getEntity(), false );
                    connection.getServer().getPluginManager().callEvent( playerSpinEvent );
                    if ( playerSpinEvent.isCancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().setSpinning( playerSpinEvent.getNewStatus() );
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
                        .getPluginManager().callEvent( new PlayerInteractEvent( connection.getEntity(),
                            PlayerInteractEvent.ClickType.LEFT, connection.getEntity().getWorld().getBlockAt( packet.getPosition() ) ) );

                    connection.setStartBreakResult( !event.isCancelled() && connection.getEntity().getStartBreak() == 0 );

                    if ( !event.isCancelled() && connection.getEntity().getStartBreak() == 0 ) {
                        handleBreakStart( connection, currentTimeMillis, packet );
                    }

                } else {
                    connection.setStartBreakResult( false );
                }

                break;

            case ABORT_BREAK:
            case STOP_BREAK:
                // Send abort break animation
                if ( connection.getEntity().getBreakVector() != null ) {
                    connection.getEntity().getWorld().sendLevelEvent( connection.getEntity().getBreakVector().toVector(), LevelEvent.BLOCK_STOP_BREAK, 0 );
                }

                // Reset when abort
                if ( packet.getAction() == PacketPlayerAction.PlayerAction.ABORT_BREAK ) {
                    connection.getEntity().setBreakVector( null );
                }

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
                if ( !connection.getEntity().isSneaking() ) {
                    PlayerToggleSneakEvent playerToggleSneakEvent = new PlayerToggleSneakEvent( connection.getEntity(), true );
                    connection.getServer().getPluginManager().callEvent( playerToggleSneakEvent );
                    if ( playerToggleSneakEvent.isCancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().setSneaking( true );
                    }
                }

                break;

            case STOP_SNEAK:
                if ( connection.getEntity().isSneaking() ) {
                    PlayerToggleSneakEvent playerToggleSneakEvent = new PlayerToggleSneakEvent( connection.getEntity(), false );
                    connection.getServer().getPluginManager().callEvent( playerToggleSneakEvent );
                    if ( playerToggleSneakEvent.isCancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().setSneaking( false );
                    }
                }

                break;

            case START_SPRINT:
                if ( !connection.getEntity().isSprinting() ) {
                    PlayerToggleSprintEvent playerToggleSprintEvent = new PlayerToggleSprintEvent( connection.getEntity(), true );
                    connection.getServer().getPluginManager().callEvent( playerToggleSprintEvent );
                    if ( playerToggleSprintEvent.isCancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().setSprinting( true );
                    }
                }

                break;

            case STOP_SPRINT:
                if ( connection.getEntity().isSprinting() ) {
                    PlayerToggleSprintEvent playerToggleSprintEvent = new PlayerToggleSprintEvent( connection.getEntity(), false );
                    connection.getServer().getPluginManager().callEvent( playerToggleSprintEvent );
                    if ( playerToggleSprintEvent.isCancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().setSprinting( false );
                    }
                }

                break;

            case CONTINUE_BREAK:
                // Broadcast break effects
                if ( connection.getEntity().getBreakVector() != null ) {
                    Block block = connection.getEntity().getWorld().getBlockAt( connection.getEntity().getBreakVector() );
                    int runtimeId = block.getRuntimeId();

                    connection.getEntity().getWorld().sendLevelEvent(
                        connection.getEntity().getBreakVector().toVector(),
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
                if ( !connection.getEntity().isGliding() ) {
                    PlayerToggleGlideEvent playerToggleGlideEvent = new PlayerToggleGlideEvent( connection.getEntity(), true );
                    connection.getEntity().getWorld().getServer().getPluginManager().callEvent( playerToggleGlideEvent );
                    if ( playerToggleGlideEvent.isCancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().setGliding( true );
                    }
                }

                break;

            case STOP_GLIDE:
                if ( connection.getEntity().isGliding() ) {
                    PlayerToggleGlideEvent playerToggleGlideEvent = new PlayerToggleGlideEvent( connection.getEntity(), false );
                    connection.getEntity().getWorld().getServer().getPluginManager().callEvent( playerToggleGlideEvent );
                    if ( playerToggleGlideEvent.isCancelled() ) {
                        connection.getEntity().sendData( connection.getEntity() );
                    } else {
                        connection.getEntity().setGliding( false );
                    }
                }

                break;

            default:
                LOGGER.warn( "Unhandled action: " + packet );
                break;
        }
    }

    private void handleBreakStart( PlayerConnection connection, long currentTimeMillis, PacketPlayerAction packet ) {
        connection.getEntity().setBreakVector( packet.getPosition() );
        connection.getEntity().setStartBreak( currentTimeMillis );

        Block block = connection.getEntity().getWorld().getBlockAt( packet.getPosition() );

        if ( !block.getSide(packet.getFace()).punch( connection.getEntity() ) ) {
            long breakTime = block.getFinalBreakTime( connection.getEntity().getInventory().getItemInHand(), connection.getEntity() );
            LOGGER.debug( "Sending break time {} ms", breakTime );

            // Tell the client which break time we want
            if ( breakTime > 0 ) {
                connection.getEntity().getWorld().sendLevelEvent( packet.getPosition().toVector(),
                    LevelEvent.BLOCK_START_BREAK, (int) ( 65536 / ( breakTime / 50 ) ) );
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
