package io.gomint.server.network.handler;

import io.gomint.event.player.*;
import io.gomint.event.world.BlockBreakEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.inventory.item.ItemSword;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.enchant.EnchantmentProcessor;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.ContainerInventory;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.item.ItemBow;
import io.gomint.server.inventory.item.ItemCrossbow;
import io.gomint.server.inventory.item.category.ItemConsumable;
import io.gomint.server.inventory.transaction.DropItemTransaction;
import io.gomint.server.inventory.transaction.InventoryTransaction;
import io.gomint.server.inventory.transaction.TransactionGroup;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInventoryTransaction;
import io.gomint.server.plugin.EventCaller;
import io.gomint.server.world.block.Block;
import io.gomint.world.Gamemode;
import io.gomint.world.block.BlockAir;
import io.gomint.world.block.data.Facing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketInventoryTransactionHandler implements PacketHandler<PacketInventoryTransaction> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketInventoryTransactionHandler.class);

    private final EventCaller eventCaller;

    public PacketInventoryTransactionHandler(EventCaller eventCaller) {
        this.eventCaller = eventCaller;
    }

    @Override
    public void handle(PacketInventoryTransaction packet, long currentTimeMillis, PlayerConnection connection) {
        // Hack for enchantment tables
        EnchantmentProcessor processor = connection.getEntity().getEnchantmentProcessor();
        if (processor != null) {
            processor.addTransaction(packet);
            return;
        }

        switch (packet.getType()) {
            case PacketInventoryTransaction.TYPE_NORMAL:
                this.handleTypeNormal(connection, packet);
                break;

            case PacketInventoryTransaction.TYPE_USE_ITEM:  // Interact / Build
            case PacketInventoryTransaction.TYPE_RELEASE_ITEM: // Consume / use item
                // Sanity checks before we interact with worlds and their chunk loading
                Vector packetPosition = packet.getPlayerPosition().add(0, -connection.getEntity().getEyeHeight(), 0);
                Vector playerPosition = connection.getEntity().getPosition();

                double distance = packetPosition.distanceSquared(playerPosition);
                double offsetLimit = 0.5;
                if (distance > offsetLimit) {
                    Block block = connection.getEntity().getWorld().getBlockAt(playerPosition.toBlockPosition());
                    LOGGER.warn("Mismatching position: {} -> {} / {}", distance, playerPosition, block.getClass().getSimpleName());
                    reset(packet, connection);
                    return;
                }

                // Special case in air clicks
                if (packet.getType() == PacketInventoryTransaction.TYPE_USE_ITEM && packet.getActionType() != 1) {
                    // Check if we can interact
                    Vector interactCheckVector = packet.getBlockPosition().toVector().add(.5f, .5f, .5f);
                    if (!connection.getEntity().canInteract(interactCheckVector, 13) ||
                        connection.getEntity().getGamemode() == Gamemode.SPECTATOR) {
                        LOGGER.warn("Can't interact from position: {} / {}", connection.getEntity().getPosition(), packet.getBlockPosition());
                        reset(packet, connection);
                        return;
                    }
                }

                // Check if in hand item is in sync
                ItemStack itemInHand = connection.getEntity().getInventory().getItemInHand();
                ItemStack packetItemInHand = packet.getItemInHand();
                if (itemInHand.getItemType() != packetItemInHand.getItemType()) {
                    LOGGER.debug("{} item in hand does not match: {} / {}", connection.getEntity().getName(), itemInHand, packetItemInHand);
                    reset(packet, connection);
                    return;
                }

                if (packet.getType() == PacketInventoryTransaction.TYPE_USE_ITEM) {
                    this.handleUseItem(itemInHand, connection, packet);
                } else {
                    this.handleConsumeItem(itemInHand, connection, packet);
                }

                break;
            case PacketInventoryTransaction.TYPE_USE_ITEM_ON_ENTITY:
                // Check item in hand
                itemInHand = connection.getEntity().getInventory().getItemInHand();
                packetItemInHand = packet.getItemInHand();
                if (itemInHand.getItemType() != packetItemInHand.getItemType()) {
                    LOGGER.warn("{} item in hand does not match (X): {} / {}", connection.getEntity().getName(), itemInHand, packetItemInHand);
                    reset(packet, connection);
                    return;
                }

                // When the player wants to do this it should have selected a entity in its interact
                if (connection.getEntity().getHoverEntity() == null) {
                    LOGGER.warn("{} selected entity is null", connection.getEntity().getName());
                    reset(packet, connection);
                    return;
                }

                // Find the entity from this packet
                io.gomint.entity.Entity entity = connection.getEntity().getWorld().findEntity(packet.getEntityId());
                if (!connection.getEntity().getHoverEntity().equals(entity)) {
                    LOGGER.warn("{} entity does not match: {}; {}; {}", connection.getEntity().getName(), entity, connection.getEntity().getHoverEntity(), packet.getEntityId());
                    reset(packet, connection);
                    return;
                }

                // Fast check for interact rules
                Vector interactCheckVector = packet.getVector1().add(.5f, .5f, .5f);
                if (!connection.getEntity().canInteract(interactCheckVector, 8) ||
                    connection.getEntity().getGamemode() == Gamemode.SPECTATOR) {
                    LOGGER.warn("Can't interact from position");
                    reset(packet, connection);
                    return;
                }

                this.handleUseItemOnEntity(entity, connection, packet);

                break;
            default:
                connection.getEntity().setUsingItem(false);
                break;
        }
    }

    private void handleUseItemOnEntity(io.gomint.entity.Entity target, PlayerConnection connection, PacketInventoryTransaction packet) {
        switch (packet.getActionType()) {
            case 0:     // Interact
                io.gomint.entity.Entity entity = connection.getEntity().getWorld().findEntity(packet.getEntityId());

                PlayerInteractWithEntityEvent event = new PlayerInteractWithEntityEvent(connection.getEntity(), entity);

                connection.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled()) {
                    break;
                }

                entity.interact(connection.getEntity(), packet.getVector2());

                break;
            case 1:     // Attack
                if (connection.getEntity().attackWithItemInHand(target)) {
                    if (connection.getEntity().getGamemode() != Gamemode.CREATIVE) {
                        ItemStack itemInHand = connection.getEntity().getInventory().getItemInHand();
                        ((io.gomint.server.inventory.item.ItemStack) itemInHand).calculateUsageAndUpdate(1);
                    }
                } else {
                    reset(packet, connection);
                }

                break;
            default:
                break;
        }
    }

    private void handleConsumeItem(ItemStack itemInHand, PlayerConnection connection, PacketInventoryTransaction packet) {
        LOGGER.debug("Action Type: {}", packet.getActionType());
        if (packet.getActionType() == 0) { // Release item ( shoot bow )
            // Check if the item is a bow
            if (itemInHand instanceof ItemBow) {
                ((ItemBow) itemInHand).shoot(connection.getEntity());
            } else if (itemInHand instanceof ItemCrossbow) {
                ((ItemCrossbow) itemInHand).shoot(connection.getEntity());
            }
        }

        connection.getEntity().setUsingItem(false);
    }

    private void handleUseItem(ItemStack itemInHand, PlayerConnection connection, PacketInventoryTransaction packet) {
        switch (packet.getActionType()) {
            case 0: // Click on block
                // Only accept valid interactions
                if (!checkInteraction(packet, connection)) {
                    return;
                }

                connection.getEntity().setUsingItem(false);

                if (!connection.getEntity().getWorld().useItemOn(itemInHand, packet.getBlockPosition(), packet.getFace(),
                    packet.getClickPosition(), connection.getEntity())) {
                    reset(packet, connection);
                    return;
                }

                break;
            case 1: // Click in air
                // Only accept valid interactions
                if (!checkInteraction(packet, connection)) {
                    return;
                }

                // Only send interact events, there is nothing special to be done in here
                PlayerInteractEvent event = new PlayerInteractEvent(connection.getEntity(), PlayerInteractEvent.ClickType.RIGHT, null);
                this.eventCaller.callEvent(event);

                if (!event.isCancelled()) {
                    io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) connection.getEntity().getInventory().getItemInHand();
                    itemStack.interact(connection.getEntity(), null, packet.getClickPosition(), null);
                } else {
                    reset(packet, connection);
                }

                break;
            case 2: // Break block
                // Breaking blocks too fast / missing start_break
                if (connection.getEntity().getGamemode() != Gamemode.CREATIVE && connection.getEntity().getBreakVector() == null) {
                    reset(packet, connection);
                    LOGGER.debug("Breaking block without break vector");
                    return;
                }

                if (connection.getEntity().getGamemode() != Gamemode.CREATIVE) {
                    // Check for transactions first
                    if (packet.getActions().length > 1) {
                        // This can only have 0 or 1 transaction
                        reset(packet, connection);
                        connection.getEntity().setBreakVector(null);
                        return;
                    }

                    // The transaction can only affect the in hand item
                    if (packet.getActions().length > 0) {
                        ItemStack source = packet.getActions()[0].getOldItem();
                        if (!source.equals(itemInHand) || source.getAmount() != itemInHand.getAmount()) {
                            // Transaction is invalid
                            reset(packet, connection);
                            connection.getEntity().setBreakVector(null);
                            return;
                        }

                        // Check if target item is either the same item or air
                        io.gomint.server.inventory.item.ItemStack target = (io.gomint.server.inventory.item.ItemStack) packet.getActions()[0].getNewItem();
                        if (!target.getMaterial().equals(((io.gomint.server.inventory.item.ItemStack) itemInHand).getMaterial()) && !target.isAir()) {
                            // Transaction is invalid
                            reset(packet, connection);
                            connection.getEntity().setBreakVector(null);
                            return;
                        }
                    }
                }

                // Transaction seems valid
                io.gomint.server.world.block.Block block = connection.getEntity().getWorld().getBlockAt(connection.getEntity().getGamemode() == Gamemode.CREATIVE ? packet.getBlockPosition() : connection.getEntity().getBreakVector());
                if (block != null) {
                    BlockBreakEvent blockBreakEvent = new BlockBreakEvent(connection.getEntity(), block, connection.getEntity().getGamemode() == Gamemode.CREATIVE ? new ArrayList() : block.getDrops(itemInHand));
                    blockBreakEvent.setCancelled(connection.getEntity().getGamemode() == Gamemode.ADVENTURE); // TODO: Better handling for canBreak rules for adventure gamemode

                    connection.getEntity().getWorld().getServer().getPluginManager().callEvent(blockBreakEvent);
                    if (blockBreakEvent.isCancelled()) {
                        reset(packet, connection);
                        connection.getEntity().setBreakVector(null);
                        return;
                    }

                    // Check for special break rights (creative)
                    if (connection.getEntity().getGamemode() == Gamemode.CREATIVE) {
                        if (connection.getEntity().getWorld().breakBlock(packet.getBlockPosition(), blockBreakEvent.getDrops(), true)) {
                            block.setBlockType(BlockAir.class);
                            connection.getEntity().setBreakVector(null);
                        } else {
                            reset(packet, connection);
                            connection.getEntity().setBreakVector(null);
                        }

                        return;
                    }

                    // Check if we can break this block in time
                    long breakTime = block.getFinalBreakTime(connection.getEntity().getInventory().getItemInHand(), connection.getEntity());
                    if (breakTime > 50 && ((connection.getEntity().getBreakTime() + 50) / (double) breakTime) < 0.75) {
                        LOGGER.warn(connection.getEntity().getName() + " broke block too fast: break time: " + (connection.getEntity().getBreakTime() + 50) +
                            "; should: " + breakTime + " for " + block.getClass().getSimpleName() + " with " + itemInHand.getClass().getSimpleName());
                        reset(packet, connection);
                        connection.getEntity().setBreakVector(null);
                    } else {
                        if (connection.getEntity().getWorld().breakBlock(connection.getEntity().getBreakVector(), blockBreakEvent.getDrops(), false)) {
                            // Add exhaustion
                            connection.getEntity().exhaust(0.025f, PlayerExhaustEvent.Cause.MINING);

                            // Damage the target item
                            if (breakTime > 50) {
                                int damage = 1;

                                // Swords get 2 calculateUsage
                                if (itemInHand instanceof ItemSword) {
                                    damage = 2;
                                }

                                io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) itemInHand;
                                itemStack.calculateUsageAndUpdate(damage);
                            }
                        } else {
                            reset(packet, connection);
                            return;
                        }

                        connection.getEntity().setBreakVector(null);
                    }
                } else {
                    reset(packet, connection);
                    connection.getEntity().setBreakVector(null);
                }

                break;
            default:
                break;
        }

    }

    private boolean checkInteraction(PacketInventoryTransaction packet, PlayerConnection connection) {
        // We cache all packets for this tick so we don't handle one twice, vanilla likes spam (https://upload.wikimedia.org/wikipedia/commons/thumb/0/09/Spam_can.png/220px-Spam_can.png)
        if (connection.getTransactionsHandled().contains(packet)) {
            return false;
        }

        connection.getTransactionsHandled().add(packet);
        return true;
    }

    private void handleTypeNormal(PlayerConnection connection, PacketInventoryTransaction packet) {
        connection.getEntity().setUsingItem(false);

        TransactionGroup transactionGroup = new TransactionGroup(connection.getEntity());
        for (PacketInventoryTransaction.NetworkTransaction transaction : packet.getActions()) {
            Inventory inventory = getInventory(transaction, connection.getEntity());

            switch (transaction.getSourceType()) {
                case 99999:
                case 100:
                case 0:
                    // Normal inventory stuff
                    InventoryTransaction inventoryTransaction = new InventoryTransaction(connection.getEntity(),
                        inventory, transaction.getSlot(), transaction.getOldItem(), transaction.getNewItem());
                    transactionGroup.addTransaction(inventoryTransaction);
                    break;
                case 2:
                    // Drop item
                    PlayerDropItemEvent playerDropItemEvent = new PlayerDropItemEvent(connection.getEntity(), transaction.getNewItem());
                    connection.getServer().getPluginManager().callEvent(playerDropItemEvent);
                    if (!playerDropItemEvent.isCancelled()) {
                        DropItemTransaction dropItemTransaction = new DropItemTransaction(
                            connection.getEntity().getLocation().add(0, connection.getEntity().getEyeHeight(), 0),
                            connection.getEntity().getDirection().normalize().multiply(0.4f),
                            transaction.getNewItem());
                        transactionGroup.addTransaction(dropItemTransaction);
                    } else {
                        reset(packet, connection);
                    }

                    break;
                default:
                    break;
            }
        }

        transactionGroup.execute(connection.getEntity().getGamemode() == Gamemode.CREATIVE);
    }

    private Inventory getInventory(PacketInventoryTransaction.NetworkTransaction transaction, EntityPlayer entity) {
        Inventory inventory = null;
        switch (transaction.getWindowId()) {
            case -4:    // Set output slot
                inventory = entity.getCraftingResultInventory();
                break;
            case -5:    // Crafting result input
                inventory = entity.getCraftingInputInventory();
                if (inventory.getItem(transaction.getSlot()).getItemType() != ItemType.AIR) {
                    for (int i = 0; i < inventory.getContents().length; i++) {
                        if (inventory.getItem(i).getItemType() == ItemType.AIR) {
                            transaction.setSlot(i);
                            break;
                        }
                    }
                }

                break;
            case -15:   // Input of items which should be enchanted
                inventory = entity.getEnchantmentInputInventory();
                break;
            case -16:   // Lapis input
                inventory = entity.getEnchantmentInputInventory();
                transaction.setSlot(1);
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
                if (transaction.getSlot() > 0) {
                    // WTF is slot 50?!
                    if (transaction.getSlot() == 50) {
                        transaction.setSlot(0);
                        inventory = entity.getCursorInventory();
                    } else {

                        // Crafting input
                        if (transaction.getSlot() >= 28 && transaction.getSlot() <= 31) {
                            inventory = entity.getCraftingInventory();
                            if (inventory.size() != 4) {
                                LOGGER.warn("Crafting inventory is not correctly sized");
                                return null;
                            }

                            transaction.setSlot(transaction.getSlot() - 28);
                        } else if (transaction.getSlot() >= 32 && transaction.getSlot() <= 40) {
                            inventory = entity.getCraftingInventory();
                            if (inventory.size() != 9) {
                                LOGGER.warn("Crafting inventory is not correctly sized");
                                return null;
                            }

                            transaction.setSlot(transaction.getSlot() - 32);
                        }
                    }
                } else {
                    inventory = entity.getCursorInventory();
                }
                break;

            default:
                // Check for container windows
                ContainerInventory containerInventory = entity.getContainerId((byte) transaction.getWindowId());
                if (containerInventory != null) {
                    inventory = containerInventory;
                } else {
                    LOGGER.warn("Unknown window id: {}", transaction.getWindowId());
                }
        }

        return inventory;
    }

    private void reset(PacketInventoryTransaction packet, PlayerConnection connection) {
        // Reset whole inventory
        connection.getEntity().getInventory().sendContents(connection);
        connection.getEntity().getOffhandInventory().sendContents(connection);
        connection.getEntity().getArmorInventory().sendContents(connection);

        // Does the viewer currently see any additional inventory?
        for (ContainerInventory inventory : connection.getEntity().getOpenInventories()) {
            inventory.sendContents(connection);
        }

        // Now check if we need to reset blocks
        if (packet.getBlockPosition() != null) {
            io.gomint.server.world.block.Block blockClicked = connection.getEntity().getWorld().getBlockAt(packet.getBlockPosition());
            blockClicked.send(connection);

            if (packet.getFace() != null) {
                // Attach to block send queue
                io.gomint.server.world.block.Block replacedBlock = blockClicked.getSide(packet.getFace());
                replacedBlock.send(connection);

                for (Facing face : Facing.values()) {
                    io.gomint.server.world.block.Block replacedSide = replacedBlock.getSide(face);
                    replacedSide.send(connection);
                }
            }
        }

        // Resend attributes for consumptions
        connection.getEntity().resendAttributes();
    }

}
