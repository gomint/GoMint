package io.gomint.server.network.handler;

import io.gomint.event.player.PlayerInteractEvent;
import io.gomint.event.world.BlockBreakEvent;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketPlayerAction;
import io.gomint.server.world.LevelEvent;
import io.gomint.server.world.block.Air;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.block.Fire;
import io.gomint.world.Gamemode;
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

                            io.gomint.server.world.block.Block block = connection.getEntity().getWorld().getBlockAt( packet.getPosition() );

                            long breakTime = block.getFinalBreakTime( connection.getEntity().getInventory().getItemInHand(), connection.getEntity() );
                            LOGGER.debug( "Sending break time: " + breakTime );

                            // Tell the client which break time we want
                            if ( breakTime > 0 ) {
                                connection.getEntity().getWorld().playLevelEvent( packet.getPosition().toVector(),
                                    LevelEvent.BLOCK_START_BREAK, (int) ( 65536 / ( breakTime / 50 ) ) );
                            }
                        }
                    }

                    // Nasty hack for fire
                    io.gomint.server.world.block.Block block = connection.getEntity().getWorld().getBlockAt( packet.getPosition() );
                    Block faced = (Block) block.getSide( packet.getFace() );
                    if ( faced instanceof Fire ) {
                        BlockBreakEvent event1 = new BlockBreakEvent( connection.getEntity(), faced );
                        connection.getServer().getPluginManager().callEvent( event1 );
                        if ( !event1.isCancelled() ) {
                            faced.setType( Air.class );
                        }
                    }
                }

                break;

            case ABORT_BREAK:
            case STOP_BREAK:
                // Send abort break animation
                if ( connection.getEntity().getBreakVector() != null ) {
                    connection.getEntity().getWorld().playLevelEvent( connection.getEntity().getBreakVector().toVector(), LevelEvent.BLOCK_STOP_BREAK, 0 );
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

            case CONTINUE_BREAK:
                // When the player is in creative this is the only way to get needed data since it doesn't send a
                // START_BREAK
                if ( connection.getEntity().getGamemode() == Gamemode.CREATIVE && connection.getEntity().getBreakVector() == null ) {
                    connection.getEntity().setBreakVector( packet.getPosition() );
                }

                // Broadcast break effects
                if ( connection.getEntity().getBreakVector() != null ) {
                    Block block = connection.getEntity().getWorld().getBlockAt( connection.getEntity().getBreakVector() );
                    connection.getEntity().getWorld().playLevelEvent(
                        connection.getEntity().getBreakVector().toVector(),
                        LevelEvent.PARTICLE_PUNCH_BLOCK,
                        block.getBlockId() | ( block.getBlockData() << 8 ) | ( packet.getFace() << 16 ) );
                }

                break;

            case JUMP:
                connection.getEntity().jump();
                break;

            case RESPAWN:
                connection.getEntity().respawn();

                break;

            default:
                LOGGER.warn( "Unhandled action: " + packet );
                break;
        }
    }

}
