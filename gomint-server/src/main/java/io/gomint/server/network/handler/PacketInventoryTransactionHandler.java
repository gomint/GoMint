package io.gomint.server.network.handler;

import io.gomint.inventory.item.ItemAir;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.passive.EntityItem;
import io.gomint.server.inventory.ContainerInventory;
import io.gomint.server.inventory.Inventory;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.inventory.transaction.DropItemTransaction;
import io.gomint.server.inventory.transaction.InventoryTransaction;
import io.gomint.server.inventory.transaction.TransactionGroup;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInventoryTransaction;
import io.gomint.world.Gamemode;
import io.gomint.world.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketInventoryTransactionHandler implements PacketHandler<PacketInventoryTransaction> {

    private static final Logger LOGGER = LoggerFactory.getLogger( PacketInventoryTransactionHandler.class );

    @Override
    public void handle( PacketInventoryTransaction packet, long currentTimeMillis, PlayerConnection connection ) {
        LOGGER.debug( packet.toString() );

        switch ( packet.getType() ) {
            case PacketInventoryTransaction.TYPE_NORMAL:
                TransactionGroup transactionGroup;
                connection.getEntity().setTransactions( transactionGroup = new TransactionGroup() );
                for ( PacketInventoryTransaction.NetworkTransaction transaction : packet.getActions() ) {
                    Inventory inventory = null;

                    switch ( transaction.getWindowId() ) {
                        // TODO: Please look away, crafting hacks at work
                        case -2:    // Add to crafting
                        case -3:    // Removed from crafting
                            inventory = connection.getEntity().getCraftingInventory();
                            break;
                        case -4:    // Set output slot
                            inventory = connection.getEntity().getCraftingResultInventory();
                            break;
                        case -5:    // Crafting result input
                            inventory = connection.getEntity().getCraftingInputInventory();
                            break;
                        case -100:  // Crafting container dropped contents
                            inventory = connection.getEntity().getCraftingInventory();
                            break;
                        case 0:     // Player window id
                            inventory = connection.getEntity().getInventory();
                            break;
                        case 120:   // Armor window id
                            inventory = connection.getEntity().getArmorInventory();
                            break;
                        case 124:   // Cursor window id
                            inventory = connection.getEntity().getCursorInventory();
                            break;

                        default:
                            // Check for container windows
                            ContainerInventory containerInventory = connection.getEntity().getContainerId( (byte) transaction.getWindowId() );
                            if ( containerInventory != null ) {
                                inventory = containerInventory;
                            } else {
                                LOGGER.warn( "Unknown window id: " + transaction.getWindowId() );
                            }
                    }

                    switch ( transaction.getSourceType() ) {
                        case 99999:
                        case 0:
                            // Normal inventory stuff
                            InventoryTransaction inventoryTransaction = new InventoryTransaction( connection.getEntity(), inventory, transaction.getSlot(), transaction.getOldItem(), transaction.getNewItem(), currentTimeMillis );
                            transactionGroup.addTransaction( inventoryTransaction );
                            break;
                        case 2:
                            // Drop item
                            DropItemTransaction dropItemTransaction = new DropItemTransaction(
                                    connection.getEntity().getLocation().add( 0, 1.3f, 0 ),
                                    connection.getEntity().getDirection().normalize().multiply( 0.4f ),
                                    transaction.getNewItem() );
                            transactionGroup.addTransaction( dropItemTransaction );
                            break;
                    }
                }

                transactionGroup.execute();

                break;

            case PacketInventoryTransaction.TYPE_USE_ITEM:  // Interact / Build
                // Sanity checks before we interact with worlds and their chunk loading
                Vector packetPosition = packet.getPlayerPosition().add( 0, -connection.getEntity().getEyeHeight(), 0 );
                Vector playerPosition = connection.getEntity().getPosition();

                double distance = packetPosition.distanceSquared( playerPosition );
                if ( distance > 0.0001 ) {
                    LOGGER.warn( "Invalid interact. Player position differs to much" );
                    return;
                }

                if ( packet.getActionType() == 0 ) {
                    // Can we safely interact?
                    if ( connection.getEntity().canInteract( packet.getBlockPosition().toVector().add( .5f, .5f, .5f ), 13 ) && connection.getEntity().getGamemode() != Gamemode.SPECTATOR ) {
                        ItemStack itemInHand = connection.getEntity().getInventory().getItemInHand();
                        ItemStack packetItemInHand = packet.getItemInHand();
                        if ( !itemInHand.equals( packetItemInHand ) || itemInHand.getAmount() != packetItemInHand.getAmount() ) {
                            if ( packet.getActions().length > 0 ) {
                                resetBlocks( packet, connection );
                                connection.getEntity().getInventory().sendContents( connection );
                            }

                            return;
                        }

                        if ( !connection.getEntity().getWorld().useItemOn( itemInHand, packet.getBlockPosition(), packet.getFace(), packet.getClickPosition(), connection.getEntity() ) ) {
                            if ( packet.getActions().length > 0 ) {
                                resetBlocks( packet, connection );
                                connection.getEntity().getInventory().sendContents( connection );
                            }

                            return;
                        }

                        itemInHand.setAmount( itemInHand.getAmount() - 1 );
                        if ( itemInHand.getAmount() <= 0 ) {
                            connection.getEntity().getInventory().setItem( connection.getEntity().getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
                        }
                    } else {
                        resetBlocks( packet, connection );
                        connection.getEntity().getInventory().sendContents( connection );
                    }
                }

                break;
        }
    }

    private void resetBlocks( PacketInventoryTransaction packet, PlayerConnection connection ) {
        Block blockClicked = connection.getEntity().getWorld().getBlockAt( packet.getBlockPosition() );
        io.gomint.server.world.block.Block clickedBlock = (io.gomint.server.world.block.Block) blockClicked;
        io.gomint.server.world.block.Block replacedBlock = (io.gomint.server.world.block.Block) clickedBlock.getSide( packet.getFace() );

        clickedBlock.send( connection );
        replacedBlock.send( connection );
    }

}
