package io.gomint.server.network.handler;

import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketBlockPickRequest;
import io.gomint.server.world.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketBlockPickRequestHandler implements PacketHandler<PacketBlockPickRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketBlockPickRequestHandler.class);

    @Override
    public void handle(PacketBlockPickRequest packet, long currentTimeMillis, PlayerConnection connection) {
        EntityPlayer player = connection.entity();

        // Crash check
        float dist = player.location().distance(packet.getLocation().toVector());
        if (dist > 100) {
            LOGGER.warn("Player {} tried to reach {} blocks wide", player.name(), dist);
            return;
        }

        Block block = player.world().blockAt(packet.getLocation());
        switch (player.gamemode()) {
            case CREATIVE:
                // When in creative give this player the block in the inventory
                for (io.gomint.inventory.item.ItemStack<?> drop : block.drops(null)) {
                    player.inventory().addItem(drop);
                }

                break;
            case SURVIVAL:
                // Check current player inventory
                byte freeSlot = -1;
                ItemStack<?>[] items = player.inventory().contents();
                for (byte i = 0; i < items.length; i++) {
                    ItemStack<?> itemStack = items[i];
                    if (freeSlot == -1 && i < 9) {
                        if (itemStack instanceof ItemAir) {
                            freeSlot = i;
                        }
                    }

                    if (block.blockId().equals(((io.gomint.server.inventory.item.ItemStack<?>) itemStack).material())) { // TODO: Fix this for slabs.....
                        if (i < 9) {
                            player.inventory().setItemInHand(i);
                            return;
                        } else if (freeSlot > -1) {
                            // Set item into free slot
                            player.inventory().item(freeSlot, itemStack);
                            player.inventory().item(i, ItemAir.create(1));
                            player.inventory().setItemInHand(freeSlot);

                            return;
                        }
                    }
                }
        }
    }

}
