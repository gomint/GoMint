package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketRemoveBlock;
import io.gomint.server.network.packet.PacketUpdateBlock;
import io.gomint.world.Gamemode;
import io.gomint.world.block.Air;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketRemoveBlockHandler implements PacketHandler<PacketRemoveBlock> {
    
    @Override
    public void handle( PacketRemoveBlock packet, long currentTimeMillis, PlayerConnection connection ) {
        io.gomint.server.world.block.Block block = connection.getEntity().getWorld().getBlockAt( packet.getPosition() );
        if ( block != null ) {
            // Check for special break rights (creative)
            if ( connection.getEntity().getGamemode() == Gamemode.CREATIVE ) {
                block.setType( Air.class, (byte) 0 );
                return;
            }

            if ( connection.getEntity().getBreakTime() < block.getBreakTime() - 50 ) { // The client can lag one tick behind (yes the client has 20 TPS)
                // Reset block
                PacketUpdateBlock updateBlock = new PacketUpdateBlock();
                updateBlock.setBlockId( block.getBlockId() );
                updateBlock.setPosition( packet.getPosition() );
                updateBlock.setPrioAndMetadata( (byte) ( 0xb << 4 | ( block.getBlockData() & 0xf ) ) );
                connection.send( updateBlock );
            } else {
                // TODO: Add drops

                block.setType( Air.class, (byte) 0 );
            }
        }
    }

}
