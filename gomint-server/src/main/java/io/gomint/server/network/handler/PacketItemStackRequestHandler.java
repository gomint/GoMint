/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler;

import io.gomint.inventory.item.ItemAir;
import io.gomint.server.crafting.session.SessionInventory;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.EnchantmentTableInventory;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.OneSlotInventory;
import io.gomint.server.inventory.WindowMagicNumbers;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.transaction.DropItemTransaction;
import io.gomint.server.inventory.transaction.InventoryTransaction;
import io.gomint.server.inventory.transaction.Transaction;
import io.gomint.server.inventory.transaction.TransactionGroup;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.handler.session.CraftingSession;
import io.gomint.server.network.handler.session.CreativeSession;
import io.gomint.server.network.handler.session.EnchantingSession;
import io.gomint.server.network.handler.session.Session;
import io.gomint.server.network.packet.PacketItemStackRequest;
import io.gomint.server.network.packet.PacketItemStackResponse;
import io.gomint.server.network.packet.types.InventoryAction;
import io.gomint.server.network.packet.types.InventoryConsumeAction;
import io.gomint.server.network.packet.types.InventoryCraftAction;
import io.gomint.server.network.packet.types.InventoryCraftingResultAction;
import io.gomint.server.network.packet.types.InventoryDestroyCreativeAction;
import io.gomint.server.network.packet.types.InventoryDropAction;
import io.gomint.server.network.packet.types.InventoryGetCreativeAction;
import io.gomint.server.network.packet.types.InventoryTransferAction;
import io.gomint.server.network.packet.types.ItemStackRequestSlotInfo;
import io.gomint.world.Gamemode;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PacketItemStackRequestHandler implements PacketHandler<PacketItemStackRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketItemStackRequestHandler.class);

    @Override
    public void handle(PacketItemStackRequest packet, long currentTimeMillis, PlayerConnection connection) throws Exception {
        List<PacketItemStackResponse.Response> responses = new ArrayList<>();
        Session session = null;

        for (PacketItemStackRequest.Request request : packet.getRequests()) {
            PacketItemStackResponse.Response resp = null;
            TransactionGroup transactionGroup = new TransactionGroup(connection.getEntity());
            Byte2ObjectMap<Byte2ObjectMap<PacketItemStackResponse.StackResponseSlotInfo>> successChanges = new Byte2ObjectOpenHashMap<>();

            request.getActions().sort((o1, o2) -> Integer.compare(o2.weight(), o1.weight()));

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Sorted itemstack request:");

                for (InventoryAction action : request.getActions()) {
                    LOGGER.debug(" > {}", action);
                }
            }

            for (InventoryAction action : request.getActions()) {
                if (action instanceof InventoryDestroyCreativeAction) {
                    if (connection.getEntity().getGamemode() == Gamemode.CREATIVE) {
                        InventoryDestroyCreativeAction destroyCreativeAction = (InventoryDestroyCreativeAction) action;
                        ItemStackRequestSlotInfo source = destroyCreativeAction.getSource();

                        ItemStack item = getItemStack(connection.getEntity(), destroyCreativeAction.getSource(), session);
                        if (destroyCreativeAction.getAmount() <= item.getAmount()) {
                            int remaining = item.getAmount() - destroyCreativeAction.getAmount();
                            Inventory inventory = getInventory(connection.getEntity(), source.getWindowId(), session);

                            if (remaining > 0) {
                                item.setAmount(remaining);
                            } else {
                                inventory.setItem(destroyCreativeAction.getSource().getSlot(), ItemAir.create(0));
                            }

                            item = (ItemStack) inventory.getItem(source.getSlot());
                            successChanges
                                .computeIfAbsent(source.getWindowId(), value -> new Byte2ObjectOpenHashMap<>())
                                .put(source.getSlot(), new PacketItemStackResponse.StackResponseSlotInfo(source.getSlot(), item.getAmount(), item.getStackId()));
                        }
                    } else {
                        resp = new PacketItemStackResponse.Response(PacketItemStackResponse.ResponseResult.Error, request.getRequestId(), null);
                    }
                } else if (action instanceof InventoryGetCreativeAction) {
                    if (connection.getEntity().getGamemode() == Gamemode.CREATIVE) {
                        int slot = ((InventoryGetCreativeAction) action).getCreativeItemId();
                        ItemStack item = (ItemStack) connection.getServer().getCreativeInventory().getItem(slot);
                        item = (ItemStack) item.clone().setAmount(64);

                        session = new CreativeSession(connection);
                        session.addInput(item, 0);
                    } else {
                        resp = new PacketItemStackResponse.Response(PacketItemStackResponse.ResponseResult.Error, request.getRequestId(), null);
                    }
                } else if (action instanceof InventoryConsumeAction) {
                    InventoryConsumeAction consumeAction = (InventoryConsumeAction) action;

                    if (session == null) {
                        resp = new PacketItemStackResponse.Response(PacketItemStackResponse.ResponseResult.Error, request.getRequestId(), null);
                        break;
                    } else {
                        ItemStackRequestSlotInfo source = ((InventoryConsumeAction) action).getSource();

                        // Get the item
                        ItemStack item = getItemStack(connection.getEntity(), source, session);
                        if (consumeAction.getAmount() <= item.getAmount()) {
                            item.setAmount(item.getAmount() - consumeAction.getAmount());
                            item = (ItemStack) item.clone().setAmount(consumeAction.getAmount());
                        }

                        byte slot = fixSlotInput(source);
                        session.addInput(item, slot);

                        item = getItemStack(connection.getEntity(), source, session);
                        successChanges
                            .computeIfAbsent(source.getWindowId(), value -> new Byte2ObjectOpenHashMap<>())
                            .put(source.getSlot(), new PacketItemStackResponse.StackResponseSlotInfo(source.getSlot(), item.getAmount(), item.getStackId()));
                    }
                } else if (action instanceof InventoryTransferAction) {
                    resp = handleInventoryTransfer((InventoryTransferAction) action, connection, transactionGroup, request, session);
                } else if (action instanceof InventoryDropAction) {
                    resp = handleInventoryDrop((InventoryDropAction) action, connection, transactionGroup, request, session);
                } else if (action instanceof InventoryCraftAction) {
                    if (connection.getEntity().getCurrentOpenContainer() != null &&
                        connection.getEntity().getCurrentOpenContainer() instanceof EnchantmentTableInventory) {
                        session = new EnchantingSession(connection)
                            .selectOption(((InventoryCraftAction) action).getRecipeId());
                    } else {
                        session = new CraftingSession(connection)
                            .findRecipe(((InventoryCraftAction) action).getRecipeId());
                    }
                } else if (action instanceof InventoryCraftingResultAction) {
                    if (session == null) {
                        resp = new PacketItemStackResponse.Response(PacketItemStackResponse.ResponseResult.Error, request.getRequestId(), null);
                        break;
                    } else if (session instanceof CraftingSession) {
                        ((CraftingSession) session).setAmountOfCrafts(((InventoryCraftingResultAction) action).getAmount());
                    }
                }
            }

            if (resp == null) {
                if (transactionGroup.getTransactions().size() > 0 && !transactionGroup.execute(false)) {
                    LOGGER.warn("Could not commit wanted transaction: {}", transactionGroup);
                    resp = new PacketItemStackResponse.Response(PacketItemStackResponse.ResponseResult.Error, request.getRequestId(), null);
                } else {
                    // We need to tell the client the new stack ids (for whatever reason)
                    for (Transaction transaction : transactionGroup.getTransactions()) {
                        ItemStack target = (ItemStack) transaction.getTargetItem();

                        byte fixedSlot = fixSlotOutput(transaction);

                        successChanges
                            .computeIfAbsent(transaction.getInventoryWindowId(), value -> new Byte2ObjectOpenHashMap<>())
                            .put(fixedSlot, new PacketItemStackResponse.StackResponseSlotInfo(fixedSlot, target.getAmount(), target.getStackId()));
                    }

                    List<PacketItemStackResponse.StackResponseContainerInfo> changes = new ArrayList<>();
                    for (Byte2ObjectMap.Entry<Byte2ObjectMap<PacketItemStackResponse.StackResponseSlotInfo>> entry : successChanges.byte2ObjectEntrySet()) {
                        List<PacketItemStackResponse.StackResponseSlotInfo> infos = new ArrayList<>();

                        for (Byte2ObjectMap.Entry<PacketItemStackResponse.StackResponseSlotInfo> infoEntry : entry.getValue().byte2ObjectEntrySet()) {
                            infos.add(infoEntry.getValue());
                        }

                        PacketItemStackResponse.StackResponseContainerInfo containerInfo = new PacketItemStackResponse.StackResponseContainerInfo(
                            entry.getByteKey(),
                            infos
                        );

                        changes.add(containerInfo);
                    }

                    resp = new PacketItemStackResponse.Response(PacketItemStackResponse.ResponseResult.Success, request.getRequestId(), changes);
                }
            }

            responses.add(resp);
        }

        PacketItemStackResponse response = new PacketItemStackResponse();
        response.setResponses(responses);
        connection.addToSendQueue(response);

        if (session != null) {
            session.postProcess();
        }
    }

    private PacketItemStackResponse.Response handleInventoryDrop(InventoryDropAction dropAction,
                                                                 PlayerConnection connection,
                                                                 TransactionGroup transactionGroup,
                                                                 PacketItemStackRequest.Request request,
                                                                 Session session) {
        Inventory inventory = getInventory(connection.getEntity(), dropAction.getSource().getWindowId(), session);
        ItemStack source = (ItemStack) inventory.getItem(dropAction.getSource().getSlot());

        byte sourceSlot = fixSlotInput(dropAction.getSource());

        // Create new item with the correct drop amount
        InventoryTransaction inventoryTransactionSource;
        if (dropAction.getAmount() == source.getAmount()) {
            // We need to replace the source with air
            inventoryTransactionSource = new InventoryTransaction(
                connection.getEntity(), inventory,
                sourceSlot, source, ItemAir.create(0),
                dropAction.getSource().getWindowId());
        } else {
            inventoryTransactionSource = new InventoryTransaction(
                connection.getEntity(), inventory,
                sourceSlot, source, source.clone().setAmount(source.getAmount() - dropAction.getAmount()),
                dropAction.getSource().getWindowId());
        }

        DropItemTransaction dropItemTransaction = new DropItemTransaction(
            connection.getEntity().getLocation().add(0, connection.getEntity().getEyeHeight(), 0),
            connection.getEntity().getDirection().normalize().multiply(0.4f),
            source.clone().setAmount(dropAction.getAmount()));

        transactionGroup.addTransaction(inventoryTransactionSource);
        transactionGroup.addTransaction(dropItemTransaction);

        return null;
    }

    private PacketItemStackResponse.Response handleInventoryTransfer(InventoryTransferAction transferAction,
                                                                     PlayerConnection connection,
                                                                     TransactionGroup transactionGroup,
                                                                     PacketItemStackRequest.Request request,
                                                                     Session session) {
        Inventory sourceInventory = getInventory(connection.getEntity(), transferAction.getSource().getWindowId(), session);
        if (sourceInventory instanceof SessionInventory) {
            if (!session.process()) {
                return new PacketItemStackResponse.Response(PacketItemStackResponse.ResponseResult.Error, request.getRequestId(), null);
            }
        }

        byte sourceSlot = fixSlotInput(transferAction.getSource());
        byte destinationSlot = fixSlotInput(transferAction.getDestination());

        ItemStack destination = getItemStack(connection.getEntity(), transferAction.getDestination(), session);

        ItemStack source = getItemStack(connection.getEntity(), transferAction.getSource(), session);
        if (transferAction.hasAmount()) {
            if (transferAction.getAmount() <= source.getAmount()) {
                int remaining = source.getAmount() - transferAction.getAmount();
                InventoryTransaction inventoryTransactionSource;

                if (remaining > 0) {
                    // We need to set leftovers back or we are left up with dangling items
                    inventoryTransactionSource = new InventoryTransaction(
                        connection.getEntity(), getInventory(connection.getEntity(), transferAction.getSource().getWindowId(), session),
                        sourceSlot, source, source.clone().setAmount(remaining),
                        transferAction.getSource().getWindowId());

                } else {
                    inventoryTransactionSource = new InventoryTransaction(
                        connection.getEntity(), getInventory(connection.getEntity(), transferAction.getSource().getWindowId(), session),
                        sourceSlot, source, ItemAir.create(0),
                        transferAction.getSource().getWindowId());
                }

                transactionGroup.addTransaction(inventoryTransactionSource);

                source = source.clone();
                source.setAmount(transferAction.getAmount());

                if (source.equals(destination)) {
                    source.setAmount(destination.getAmount() + source.getAmount());
                }

                InventoryTransaction inventoryTransactionDestination = new InventoryTransaction(
                    connection.getEntity(), getInventory(connection.getEntity(), transferAction.getDestination().getWindowId(), session),
                    destinationSlot, destination, source,
                    transferAction.getDestination().getWindowId());

                transactionGroup.addTransaction(inventoryTransactionDestination);
            } else {
                return new PacketItemStackResponse.Response(PacketItemStackResponse.ResponseResult.Error, request.getRequestId(), null);
            }
        } else {
            InventoryTransaction inventoryTransactionSource = new InventoryTransaction(
                connection.getEntity(), getInventory(connection.getEntity(), transferAction.getSource().getWindowId(), session),
                sourceSlot, source, destination,
                transferAction.getSource().getWindowId());
            InventoryTransaction inventoryTransactionDestination = new InventoryTransaction(
                connection.getEntity(), getInventory(connection.getEntity(), transferAction.getDestination().getWindowId(), session),
                destinationSlot, destination, source,
                transferAction.getDestination().getWindowId());

            transactionGroup.addTransaction(inventoryTransactionSource);
            transactionGroup.addTransaction(inventoryTransactionDestination);
        }

        return null;
    }

    private byte fixSlotInput(ItemStackRequestSlotInfo info) {
        switch (info.getWindowId()) {
            case WindowMagicNumbers.ENCHANTMENT_TABLE_INPUT:
                return 0;
            case WindowMagicNumbers.ENCHANTMENT_TABLE_MATERIAL:
                return 1;
            case WindowMagicNumbers.CRAFTING_INPUT:
                return (byte) (info.getSlot() - 28);
            case WindowMagicNumbers.CREATED_OUTPUT:
                return (byte) (info.getSlot() - 50);
        }

        return info.getSlot();
    }

    private byte fixSlotOutput(Transaction info) {
        switch (info.getInventoryWindowId()) {
            case WindowMagicNumbers.ENCHANTMENT_TABLE_INPUT:
                return 14;
            case WindowMagicNumbers.ENCHANTMENT_TABLE_MATERIAL:
                return 15;
            case WindowMagicNumbers.CRAFTING_INPUT:
                return (byte) (info.getSlot() + 28);
            case WindowMagicNumbers.CREATED_OUTPUT:
                return (byte) (info.getSlot() + 50);
        }

        return (byte) info.getSlot();
    }

    private ItemStack getItemStack(EntityPlayer entityPlayer, ItemStackRequestSlotInfo requestSlotInfo, Session session) {
        Inventory inventory = getInventory(entityPlayer, requestSlotInfo.getWindowId(), session);
        ItemStack itemStack = (ItemStack) inventory.getItem(fixSlotInput(requestSlotInfo));

        // TODO: check for item stack id

        return itemStack;
    }

    private Inventory getInventory(EntityPlayer entity, byte windowId, Session session) {
        switch (windowId) {
            case WindowMagicNumbers.ARMOR:
                return entity.getArmorInventory();
            case WindowMagicNumbers.OFFHAND:
                return entity.getOffhandInventory();
            case WindowMagicNumbers.HOTBAR:
            case WindowMagicNumbers.INVENTORY:
            case WindowMagicNumbers.COMBINED_INVENTORY:
                return entity.getInventory();
            case WindowMagicNumbers.CURSOR:
                return entity.getCursorInventory();
            case WindowMagicNumbers.ENCHANTMENT_TABLE_INPUT:
            case WindowMagicNumbers.ENCHANTMENT_TABLE_MATERIAL:
            case WindowMagicNumbers.CONTAINER:
                return entity.getCurrentOpenContainer();
            case WindowMagicNumbers.CRAFTING_INPUT:
                return entity.getCraftingInputInventory();
            case WindowMagicNumbers.CRAFTING_OUTPUT:
            case WindowMagicNumbers.CREATED_OUTPUT:
                return session.getOutput();
        }

        LOGGER.warn("Unknown inventory window id for item stack request: {}", windowId);
        return null;
    }

}
