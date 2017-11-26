package io.gomint.server.network.handler;

import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketMobArmorEquipment;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketMobArmorEquipmentHandler implements PacketHandler<PacketMobArmorEquipment> {

    @Override
    public void handle( PacketMobArmorEquipment packet, long currentTimeMillis, PlayerConnection connection ) {

        // TODO implement checks if the client says something correct
        connection.getEntity().getArmorInventory().setBoots( packet.getBoots() );
        connection.getEntity().getArmorInventory().setChestplate( packet.getChestplate() );
        connection.getEntity().getArmorInventory().setHelmet( packet.getHelmet() );
        connection.getEntity().getArmorInventory().setLeggings( packet.getLeggings() );
    }

}
