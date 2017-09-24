package io.gomint.server.network.handler;

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

        if ( connection.getEntity().getAdventureSettings().isCanFly() ) {
            if ( connection.getEntity().getAdventureSettings().isFlying() != adventureSettings.isFlying() ) {
                // Just accept what the client tells
                connection.getEntity().getAdventureSettings().setFlying( adventureSettings.isFlying() );
                connection.getEntity().getAdventureSettings().update();
            }
        }
    }
    
}
