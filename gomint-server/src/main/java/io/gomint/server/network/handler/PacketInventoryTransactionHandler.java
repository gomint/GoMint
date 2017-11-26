package io.gomint.server.network.handler;

import io.gomint.event.player.PlayerConsumeItemEvent;
import io.gomint.event.player.PlayerDropItemEvent;
import io.gomint.event.player.PlayerExhaustEvent;
import io.gomint.event.player.PlayerInteractEvent;
import io.gomint.event.world.BlockBreakEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.ContainerInventory;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.item.ItemBow;
import io.gomint.server.inventory.item.category.ItemConsumable;
import io.gomint.server.inventory.transaction.DropItemTransaction;
import io.gomint.server.inventory.transaction.InventoryTransaction;
import io.gomint.server.inventory.transaction.TransactionGroup;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInventoryTransaction;
import io.gomint.world.Gamemode;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockAir;
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
        switch ( packet.getType() ) {
            case PacketInventoryTransaction.TYPE_NORMAL:
                this.handleTypeNormal( connection, packet );
                break;

            case PacketInventoryTransaction.TYPE_USE_ITEM:  // Interact / Build
            case PacketInventoryTransaction.TYPE_RELEASE_ITEM: // Consume / use item
                // Sanity checks before we interact with worlds and their chunk loading
                Vector packetPosition = packet.getPlayerPosition().add( 0, -connection.getEntity().getEyeHeight(), 0 );
                Vector playerPosition = connection.getEntity().getPosition();

                double distance = packetPosition.distanceSquared( playerPosition );
                double offsetLimit = 0.5;
                if ( distance > offsetLimit ) {
                    LOGGER.warn( "Mismatching position: " + distance + " -> " + playerPosition );
                    reset( packet, connection );
                    return;
                }

                // Special case in air clicks
                if ( packet.getType() == PacketInventoryTransaction.TYPE_USE_ITEM && packet.getActionType() != 1 ) {
                    // Check if we can interact
                    Vector interactCheckVector = packet.getBlockPosition().toVector().add( .5f, .5f, .5f );
                    if ( !connection.getEntity().canInteract( interactCheckVector, 13 ) ||
                        connection.getEntity().getGamemode() == Gamemode.SPECTATOR ) {
                        LOGGER.warn( "Can't interact from position: " + connection.getEntity().getPosition() + " / " + packet.getBlockPosition() );
                        reset( packet, connection );
                        return;
                    }
                }

                // Check if in hand item is in sync
                ItemStack itemInHand = connection.getEntity().getInventory().getItemInHand();
                ItemStack packetItemInHand = packet.getItemInHand();
                if ( !itemInHand.equals( packetItemInHand ) || itemInHand.getAmount() != packetItemInHand.getAmount() ) {
                    LOGGER.warn( connection.getEntity().getName() + " item in hand does not match: " + itemInHand + " / " + packetItemInHand );
                    reset( packet, connection );
                    return;
                }

                switch ( packet.getType() ) {
                    case PacketInventoryTransaction.TYPE_USE_ITEM:
                        this.handleUseItem( itemInHand, connection, packet );
                        break;
                    default:
                        this.handleConsumeItem( itemInHand, connection, packet );
                        break;
                }

                break;
            case PacketInventoryTransaction.TYPE_USE_ITEM_ON_ENTITY:
                // Check item in hand
                itemInHand = connection.getEntity().getInventory().getItemInHand();
                packetItemInHand = packet.getItemInHand();
                if ( !itemInHand.equals( packetItemInHand ) || itemInHand.getAmount() != packetItemInHand.getAmount() ) {
                    LOGGER.warn( connection.getEntity().getName() + " item in hand does not match: " + itemInHand + " / " + packetItemInHand );
                    reset( packet, connection );
                    return;
                }

                // When the player wants to do this it should have selected a entity in its interact
                if ( connection.getEntity().getHoverEntity() == null ) {
                    LOGGER.warn( connection.getEntity().getName() + " selected entity is null" );
                    reset( packet, connection );
                    return;
                }

                // Find the entity from this packet
                io.gomint.entity.Entity entity = connection.getEntity().getWorld().findEntity( packet.getEntityId() );
                if ( entity == null || !connection.getEntity().getHoverEntity().equals( entity ) ) {
                    LOGGER.warn( connection.getEntity().getName() + " entity does not match: " + entity + "; " + connection.getEntity().getHoverEntity() + "; " + packet.getEntityId() );
                    reset( packet, connection );
                    return;
                }

                // Fast check for interact rules
                Vector interactCheckVector = packet.getVector1().add( .5f, .5f, .5f );
                if ( !connection.getEntity().canInteract( interactCheckVector, 8 ) ||
                    connection.getEntity().getGamemode() == Gamemode.SPECTATOR ) {
                    LOGGER.warn( "Can't interact from position" );
                    reset( packet, connection );
                    return;
                }

                this.handleUseItemOnEntity( entity, connection, packet );

                break;
            default:
                break;
        }
    }

    private void handleUseItemOnEntity( io.gomint.entity.Entity target, PlayerConnection connection, PacketInventoryTransaction packet ) {
        switch ( packet.getActionType() ) {
            case 0:     // Interact
                break;
            case 1:     // Attack
                if ( connection.getEntity().attackWithItemInHand( target ) ) {
                    ItemStack itemInHand = connection.getEntity().getInventory().getItemInHand();
                    if ( ( (io.gomint.server.inventory.item.ItemStack) itemInHand ).damage( 1 ) ) {
                        connection.getEntity().getInventory().setItem( connection.getEntity().getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
                    } else {
                        connection.getEntity().getInventory().setItem( connection.getEntity().getInventory().getItemInHandSlot(), itemInHand );
                    }
                } else {
                    reset( packet, connection );
                }

                break;
        }
    }

    private void handleConsumeItem( ItemStack itemInHand, PlayerConnection connection, PacketInventoryTransaction packet ) {
        LOGGER.debug( "Action Type: " + packet.getActionType() );
        switch ( packet.getActionType() ) {
            case 0: // Release item ( shoot bow )
                // Check if the item is a bow
                if ( itemInHand instanceof ItemBow ) {
                    ( (ItemBow) itemInHand ).shoot( connection.getEntity() );
                }

                break;

            case 1: // Consume item
                // Check if item in hand is consumable
                if ( !( itemInHand instanceof ItemConsumable ) ) {
                    LOGGER.info( "Player " + connection.getEntity().getName() + " tried to consume " + itemInHand.getClass().getSimpleName() );
                    connection.getEntity().getInventory().sendContents( connection );
                    connection.getEntity().resendAttributes();
                    return;
                }

                // Call event
                PlayerConsumeItemEvent event = new PlayerConsumeItemEvent( connection.getEntity(), itemInHand );
                connection.getServer().getPluginManager().callEvent( event );

                if ( event.isCancelled() ) {
                    connection.getEntity().getInventory().sendContents( connection );
                    connection.getEntity().resendAttributes();
                    return;
                }

                // Consume
                ( (io.gomint.server.inventory.item.category.ItemConsumable) itemInHand ).onConsume( connection.getEntity() );
                if ( itemInHand.getAmount() <= 0 ) {
                    connection.getEntity().getInventory().setItem( connection.getEntity().getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
                } else {
                    connection.getEntity().getInventory().setItem( connection.getEntity().getInventory().getItemInHandSlot(), itemInHand );
                }

                break;
            default:
                break;
        }
    }

    private void handleUseItem( ItemStack itemInHand, PlayerConnection connection, PacketInventoryTransaction packet ) {
        switch ( packet.getActionType() ) {
            case 0: // Click on block
                if ( !connection.getEntity().getWorld().useItemOn( itemInHand, packet.getBlockPosition(), packet.getFace(), packet.getClickPosition(), connection.getEntity() ) ) {
                    reset( packet, connection );
                    return;
                }

                if ( ( (io.gomint.server.inventory.item.ItemStack) itemInHand ).afterPlacement() ) {
                    connection.getEntity().getInventory().setItem( connection.getEntity().getInventory().getItemInHandSlot(), ItemAir.create( 0 ) );
                } else {
                    connection.getEntity().getInventory().setItem( connection.getEntity().getInventory().getItemInHandSlot(), itemInHand );
                }

                break;
            case 1: // Click in air
                // Only send interact events, there is nothing special to be done in here
                PlayerInteractEvent event = new PlayerInteractEvent( connection.getEntity(), PlayerInteractEvent.ClickType.RIGHT, null );
                connection.getNetworkManager().getServer().getPluginManager().callEvent( event );

                if ( !event.isCancelled() ) {
                    io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) connection.getEntity().getInventory().getItemInHand();
                    itemStack.interact( connection.getEntity(), -1, packet.getClickPosition(), null );
                }

                break;
            case 2: // Break block
                // Breaking blocks too fast / missing start_break
                if ( connection.getEntity().getBreakVector() == null ) {
                    reset( packet, connection );
                    LOGGER.debug( "Breaking block without break vector" );
                    return;
                }

                if ( connection.getEntity().getGamemode() != Gamemode.CREATIVE ) {
                    // Check for transactions first
                    if ( packet.getActions().length > 1 ) {
                        // This can only have 0 or 1 transaction
                        reset( packet, connection );
                        return;
                    }

                    // The transaction can only affect the in hand item
                    if ( packet.getActions().length > 0 ) {
                        ItemStack source = packet.getActions()[0].getOldItem();
                        if ( !source.equals( itemInHand ) || source.getAmount() != itemInHand.getAmount() ) {
                            // Transaction is invalid
                            reset( packet, connection );
                            return;
                        }

                        // Check if target item is either the same item or air
                        io.gomint.server.inventory.item.ItemStack target = (io.gomint.server.inventory.item.ItemStack) packet.getActions()[0].getNewItem();
                        if ( target.getMaterial() != ( (io.gomint.server.inventory.item.ItemStack) itemInHand ).getMaterial() && target.getMaterial() != 0 ) {
                            // Transaction is invalid
                            reset( packet, connection );
                            return;
                        }
                    }
                }

                // Transaction seems valid
                io.gomint.server.world.block.Block block = connection.getEntity().getWorld().getBlockAt( connection.getEntity().getBreakVector() );
                if ( block != null ) {
                    BlockBreakEvent blockBreakEvent = new BlockBreakEvent( connection.getEntity(), block );
                    connection.getEntity().getWorld().getServer().getPluginManager().callEvent( blockBreakEvent );
                    if ( blockBreakEvent.isCancelled() ) {
                        reset( packet, connection );
                        return;
                    }

                    // Check for special break rights (creative)
                    if ( connection.getEntity().getGamemode() == Gamemode.CREATIVE ) {
                        if ( block.onBreak() ) {
                            block.setType( BlockAir.class );
                        } else {
                            reset( packet, connection );
                        }

                        return;
                    }

                    // Check if we can break this block in time
                    long breakTime = block.getFinalBreakTime( connection.getEntity().getInventory().getItemInHand(), connection.getEntity() );
                    LOGGER.debug( "Break time: " + ( connection.getEntity().getBreakTime() + 50 ) + "; should: " + breakTime + " for " + block.getClass().getSimpleName() );
                    if ( connection.getEntity().getBreakTime() + 50 < breakTime ) {
                        LOGGER.warn( connection.getEntity().getName() + " broke block too fast: break time: " + ( connection.getEntity().getBreakTime() + 50 ) +
                            "; should: " + breakTime + " for " + block.getClass().getSimpleName() + " with " + itemInHand.getClass().getSimpleName() );
                        connection.getEntity().setBreakVector( null );
                        reset( packet, connection );
                    } else {
                        if ( connection.getEntity().getWorld().breakBlock( connection.getEntity().getBreakVector(), connection.getEntity().getGamemode() == Gamemode.SURVIVAL, itemInHand ) ) {
                            // Add exhaustion
                            connection.getEntity().exhaust( 0.025f, PlayerExhaustEvent.Cause.MINING );

                            // Check if transaction wants to set air
                            if ( packet.getActions().length > 0 ) {
                                io.gomint.server.inventory.item.ItemStack target = (io.gomint.server.inventory.item.ItemStack) packet.getActions()[0].getNewItem();
                                if ( target.getMaterial() == 0 ) {
                                    connection.getEntity().getInventory().setItem( connection.getEntity().getInventory().getItemInHandSlot(), target );
                                } else {
                                    // Check if transaction wants to increment data of the item

                                    // When the item was broken with the correct tool it decreases by 1, else it should decrease by 2


                                    if ( ( target.getData() == itemInHand.getData() + 1 || target.getData() == itemInHand.getData() + 2 ) &&
                                        ( ( target.getNbtData() == null && itemInHand.getNbtData() == null ) ||
                                            target.getNbtData().equals( itemInHand.getNbtData() ) ) ) {
                                        connection.getEntity().getInventory().setItem( connection.getEntity().getInventory().getItemInHandSlot(), target );
                                    }
                                }
                            }
                        } else {
                            reset( packet, connection );
                            return;
                        }

                        connection.getEntity().setBreakVector( null );
                    }
                } else {
                    reset( packet, connection );
                }

                break;
            default:
                break;
        }
    }

    private void handleTypeNormal( PlayerConnection connection, PacketInventoryTransaction packet ) {
        TransactionGroup transactionGroup = new TransactionGroup();
        for ( PacketInventoryTransaction.NetworkTransaction transaction : packet.getActions() ) {
            Inventory inventory = getInventory( transaction, connection.getEntity() );

            switch ( transaction.getSourceType() ) {
                case 99999:
                case 0:
                    // Normal inventory stuff
                    InventoryTransaction inventoryTransaction = new InventoryTransaction( connection.getEntity(),
                        inventory, transaction.getSlot(), transaction.getOldItem(), transaction.getNewItem() );
                    transactionGroup.addTransaction( inventoryTransaction );
                    break;
                case 2:
                    // Drop item
                    PlayerDropItemEvent playerDropItemEvent = new PlayerDropItemEvent( connection.getEntity(), transaction.getNewItem() );
                    connection.getServer().getPluginManager().callEvent( playerDropItemEvent );
                    if ( !playerDropItemEvent.isCancelled() ) {
                        DropItemTransaction dropItemTransaction = new DropItemTransaction(
                            connection.getEntity().getLocation().add( 0, 1.3f, 0 ),
                            connection.getEntity().getDirection().normalize().multiply( 0.4f ),
                            transaction.getNewItem() );
                        transactionGroup.addTransaction( dropItemTransaction );
                    } else {
                        reset( packet, connection );
                    }

                    break;
                default:
                    break;
            }
        }

        transactionGroup.execute();
    }

    private Inventory getInventory( PacketInventoryTransaction.NetworkTransaction transaction, EntityPlayer entity ) {
        Inventory inventory = null;
        switch ( transaction.getWindowId() ) {
            // TODO: Please look away, crafting hacks at work
            case -2:    // Add to crafting
            case -3:    // Removed from crafting
                inventory = entity.getCraftingInventory();
                break;
            case -4:    // Set output slot
                inventory = entity.getCraftingResultInventory();
                break;
            case -5:    // Crafting result input
                inventory = entity.getCraftingInputInventory();
                break;
            case -100:  // Crafting container dropped contents
                inventory = entity.getCraftingInventory();
                break;
            case 0:     // EntityPlayer window id
                inventory = entity.getInventory();
                break;
            case 119:
                inventory = entity.getOffhandInventory();
                break;
            case 120:   // Armor window id
                inventory = entity.getArmorInventory();
                break;
            case 124:   // Cursor window id
                inventory = entity.getCursorInventory();
                break;

            default:
                // Check for container windows
                ContainerInventory containerInventory = entity.getContainerId( (byte) transaction.getWindowId() );
                if ( containerInventory != null ) {
                    inventory = containerInventory;
                } else {
                    LOGGER.warn( "Unknown window id: " + transaction.getWindowId() );
                }
        }

        return inventory;
    }

    private void reset( PacketInventoryTransaction packet, PlayerConnection connection ) {
        connection.getEntity().getInventory().sendContents( connection );

        if ( packet.getBlockPosition() != null ) {
            Block blockClicked = connection.getEntity().getWorld().getBlockAt( packet.getBlockPosition() );
            io.gomint.server.world.block.Block clickedBlock = (io.gomint.server.world.block.Block) blockClicked;

            if ( packet.getFace() > -1 ) {
                io.gomint.server.world.block.Block replacedBlock = (io.gomint.server.world.block.Block) clickedBlock.getSide( packet.getFace() );
                replacedBlock.send( connection );
            }

            clickedBlock.send( connection );
        }
    }

}
