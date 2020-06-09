package io.gomint.server.network.handler;

import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketBlockPickRequest;
import io.gomint.server.world.block.Block;
import io.gomint.world.Gamemode;

public class PacketBlockPickRequestHandler implements PacketHandler<PacketBlockPickRequest> {

    @Override
    public void handle( PacketBlockPickRequest packet, long currentTimeMillis, PlayerConnection connection ) {
        EntityPlayer player = connection.getEntity();
        if( player.getGamemode() != Gamemode.CREATIVE ) {
            return;
        }

        Block block = player.getWorld().getBlockAt( packet.getLocation() );
        // TODO: State to item data
        ItemStack item = connection.getServer().getItems().create( block.getBlockId(), (short) 0, (byte) 1, null );
        player.getInventory().setItem( player.getInventory().getItemInHandSlot(), item );
    }

}
