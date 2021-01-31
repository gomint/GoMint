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

        if ( connection.entity().adventureSettings().isFlying() != adventureSettings.isFlying() ) {
            // Fire event
            PlayerToggleFlyEvent playerToggleFlyEvent = new PlayerToggleFlyEvent( connection.entity(), adventureSettings.isFlying() );
            playerToggleFlyEvent.cancelled( !connection.entity().adventureSettings().isCanFly() );
            connection.entity().world().server().pluginManager().callEvent( playerToggleFlyEvent );

            connection.entity().adventureSettings().setFlying( playerToggleFlyEvent.cancelled() ? connection.entity().adventureSettings().isFlying() : adventureSettings.isFlying() );
            connection.entity().adventureSettings().update();
        }
    }
    
}
