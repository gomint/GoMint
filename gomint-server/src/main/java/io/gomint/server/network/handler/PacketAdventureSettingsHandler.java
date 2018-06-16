package io.gomint.server.network.handler;

import io.gomint.event.player.PlayerToggleFlyEvent;
import io.gomint.server.entity.AdventureSettings;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketAdventureSettings;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketAdventureSettingsHandler implements PacketHandler<PacketAdventureSettings> {
    
    @Override
    public void handle( PacketAdventureSettings packet, long currentTimeMillis, PlayerConnection connection ) {
        // This is sent when the client wants a change to its flying status
        AdventureSettings adventureSettings = new AdventureSettings( packet.getFlags(), packet.getFlags2() );

        if ( connection.getEntity().getAdventureSettings().isFlying() != adventureSettings.isFlying() ) {
            // Fire event
            PlayerToggleFlyEvent playerToggleFlyEvent = new PlayerToggleFlyEvent( connection.getEntity(), adventureSettings.isFlying() );
            playerToggleFlyEvent.setCancelled( !connection.getEntity().getAdventureSettings().isCanFly() );
            connection.getEntity().getWorld().getServer().getPluginManager().callEvent( playerToggleFlyEvent );

            connection.getEntity().getAdventureSettings().setFlying( playerToggleFlyEvent.isCancelled() ? connection.getEntity().getAdventureSettings().isFlying() : adventureSettings.isFlying() );
            connection.getEntity().getAdventureSettings().update();
        }
    }
    
}
