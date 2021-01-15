package io.gomint.server.inventory;

import io.gomint.inventory.InventoryType;
import io.gomint.math.BlockPosition;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketContainerOpen;
import io.gomint.server.network.packet.PacketInventoryContent;
import io.gomint.server.network.packet.PacketInventorySetSlot;
import io.gomint.server.network.type.WindowType;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ContainerInventory<I> extends Inventory<I> implements io.gomint.inventory.ContainerInventory<I> {

    /**
     * Construct a new container inventory
     *
     * @param owner of the container (mostly a tile or normal entity)
     * @param size  of the entity
     * @param items factory
     */
    public ContainerInventory(Items items, InventoryHolder owner, int size) {
        super(items, owner, size);
    }

    /**
     * Get the type of inventory
     *
     * @return inventory type
     */
    public abstract WindowType getType();

    @Override
    public BlockPosition containerPosition() {
        TileEntity tileEntity = (TileEntity) this.owner;
        return tileEntity.getBlock().position();
    }

    @Override
    public WorldAdapter world() {
        TileEntity tileEntity = (TileEntity) this.owner;
        return tileEntity.getBlock().world();
    }

    /**
     * Called when a container has been opened
     *
     * @param player for which the container should be opened
     */
    public abstract void onOpen(EntityPlayer player);

    /**
     * Called when a container has been closed
     *
     * @param player for which the container closed
     */
    public abstract void onClose(EntityPlayer player);

    /**
     * Add a player to this container
     *
     * @param player   to add
     * @param windowId to use for this player
     */
    public void addViewer(EntityPlayer player, byte windowId) {
        // Sent ContainerOpen first
        PacketContainerOpen containerOpen = new PacketContainerOpen();
        containerOpen.setWindowId(windowId);
        containerOpen.setType(this.getType().getId());
        containerOpen.setLocation(this.containerPosition());
        player.connection().addToSendQueue(containerOpen);

        // Add viewer and send contents
        super.addViewer(player);

        // Trigger additional actions for the container
        this.onOpen(player);
    }

    @Override
    public void removeViewer(EntityPlayer player) {
        // Call special close event
        this.onClose(player);

        // Remove from view
        super.removeViewer(player);
    }

    @Override
    public void sendContents(PlayerConnection playerConnection) {
        PacketInventoryContent inventoryContent = new PacketInventoryContent();
        inventoryContent.setWindowId(WindowMagicNumbers.OPEN_CONTAINER);
        inventoryContent.setItems(this.contents());
        playerConnection.addToSendQueue(inventoryContent);
    }

    @Override
    public void sendContents(int slot, PlayerConnection playerConnection) {
        PacketInventorySetSlot inventorySetSlot = new PacketInventorySetSlot();
        inventorySetSlot.setWindowId(WindowMagicNumbers.OPEN_CONTAINER);
        inventorySetSlot.setSlot(slot);
        inventorySetSlot.setItemStack(this.item(slot));
        playerConnection.addToSendQueue(inventorySetSlot);
    }

    @Override
    public InventoryType inventoryType() {
        return InventoryType.CONTAINER;
    }

}
