package io.gomint.server.network.handler;

import io.gomint.inventory.item.ItemAir;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketBlockPickRequest;
import io.gomint.server.world.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketBlockPickRequestHandler implements PacketHandler<PacketBlockPickRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketBlockPickRequestHandler.class);

    @Override
    public void handle(PacketBlockPickRequest packet, long currentTimeMillis, PlayerConnection connection) {
        EntityPlayer player = connection.getEntity();

        // Crash check
        float dist = player.getLocation().distance(packet.getLocation().toVector());
        if (dist > 100) {
            LOGGER.warn("Player {} tried to reach {} blocks wide", player.getName(), dist);
            return;
        }

        Block block = player.getWorld().getBlockAt(packet.getLocation());
        switch (player.getGamemode()) {
            case CREATIVE:
                // When in creative give this player the block in the inventory
                for (io.gomint.inventory.item.ItemStack drop : block.getDrops(null)) {
                    player.getInventory().addItem(drop);
                }

                break;
            case SURVIVAL:
                // Check current player inventory
                byte freeSlot = -1;
                io.gomint.inventory.item.ItemStack[] items = player.getInventory().getContents();
                for (byte i = 0; i < items.length; i++) {
                    ItemStack itemStack = (ItemStack) items[i];
                    if (freeSlot == -1 && i < 9) {
                        if (itemStack instanceof ItemAir) {
                            freeSlot = i;
                        }
                    }

                    if (block.getBlockId().equals(itemStack.getMaterial())) { // TODO: Fix this for slabs.....
                        if (i < 9) {
                            player.getInventory().setItemInHand(i);
                            return;
                        } else if (freeSlot > -1) {
                            // Set item into free slot
                            player.getInventory().setItem(freeSlot, itemStack);
                            player.getInventory().setItem(i, ItemAir.create(1));
                            player.getInventory().setItemInHand(freeSlot);

                            return;
                        }
                    }
                }
        }
    }

}
