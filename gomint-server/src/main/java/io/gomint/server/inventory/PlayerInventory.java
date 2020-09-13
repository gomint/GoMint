package io.gomint.server.inventory;

import io.gomint.entity.Entity;
import io.gomint.inventory.InventoryType;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.passive.EntityHuman;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketContainerOpen;
import io.gomint.server.network.packet.PacketInventoryContent;
import io.gomint.server.network.packet.PacketInventorySetSlot;
import io.gomint.server.network.packet.PacketMobEquipment;
import io.gomint.server.network.type.WindowType;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerInventory extends ContainerInventory implements io.gomint.inventory.PlayerInventory {

    private byte itemInHandSlot;

    /**
     * Construct a new Inventory for the player which is 36 + 9 in size
     *
     * @param player for which this inventory is
     */
    public PlayerInventory(Items items, EntityHuman player) {
        super(items, player, 36);
    }

    /**
     * Get the item this player is currently holding
     *
     * @return the itemstack the player is holding
     */
    public ItemStack getItemInHand() {
        return this.contents[this.itemInHandSlot];
    }

    @Override
    public void setItem(int index, ItemStack item) {
        ItemStack oldItem = getItem(index);
        super.setItem(index, item);

        if (index == this.itemInHandSlot && this.owner instanceof EntityPlayer) {
            // Inform the old item it got deselected
            io.gomint.server.inventory.item.ItemStack oldItemInHand = (io.gomint.server.inventory.item.ItemStack) oldItem;
            oldItemInHand.removeFromHand((EntityPlayer) this.owner);

            // Inform the item it got selected
            io.gomint.server.inventory.item.ItemStack newItemInHand = (io.gomint.server.inventory.item.ItemStack) item;
            newItemInHand.gotInHand((EntityPlayer) this.owner);

            // Update the item for everyone else
            this.updateItemInHand();
        }
    }

    @Override
    public void sendContents(int slot, PlayerConnection playerConnection) {
        byte windowId = playerConnection.getEntity().getWindowId(this);
        if (windowId >= WindowMagicNumbers.FIRST.getId()) {
            PacketInventorySetSlot setSlot = new PacketInventorySetSlot();
            setSlot.setSlot(slot);
            setSlot.setWindowId(windowId);
            setSlot.setItemStack(this.contents[slot]);
            playerConnection.addToSendQueue(setSlot);
        }

        PacketInventorySetSlot setSlot = new PacketInventorySetSlot();
        setSlot.setSlot(slot);
        setSlot.setWindowId(WindowMagicNumbers.PLAYER.getId());
        setSlot.setItemStack(this.contents[slot]);
        playerConnection.addToSendQueue(setSlot);
    }

    @Override
    public WindowType getType() {
        return WindowType.INVENTORY;
    }

    @Override
    public void onOpen(EntityPlayer player) {

    }

    @Override
    public void onClose(EntityPlayer player) {

    }

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
        containerOpen.setLocation(Vector.ZERO.toBlockPosition());
        player.getConnection().addToSendQueue(containerOpen);

        // Trigger additional actions for the container
        this.onOpen(player);
    }

    @Override
    public void removeViewer(EntityPlayer player) {
        // Call special close event
        this.onClose(player);
    }

    @Override
    public void sendContents(PlayerConnection playerConnection) {
        byte windowId = playerConnection.getEntity().getWindowId(this);
        if (windowId >= WindowMagicNumbers.FIRST.getId()) {
            PacketInventoryContent inventory = new PacketInventoryContent();
            inventory.setWindowId(WindowMagicNumbers.PLAYER.getId());
            inventory.setItems(getContents());
            playerConnection.addToSendQueue(inventory);
        }

        PacketInventoryContent inventory = new PacketInventoryContent();
        inventory.setWindowId(WindowMagicNumbers.PLAYER.getId());
        inventory.setItems(getContents());
        playerConnection.addToSendQueue(inventory);
    }

    /**
     * Set the slot for the item the player currently has in hand
     *
     * @param slot in the inventory to point on the item in hand
     */
    public void setItemInHand(byte slot) {
        if (this.owner instanceof EntityPlayer) {
            this.updateItemInHandWithItem(slot);
        }

        this.updateItemInHand();
    }

    private void updateItemInHand() {
        EntityHuman player = (EntityHuman) this.owner;

        PacketMobEquipment packet = this.createMobEquipmentPacket(player);

        // Relay packet
        for (Entity entity : player.getAttachedEntities()) {
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer) entity).getConnection().addToSendQueue(packet);
            }
        }
    }

    private PacketMobEquipment createMobEquipmentPacket(EntityHuman human) {
        PacketMobEquipment packet = new PacketMobEquipment();
        packet.setEntityId(human.getEntityId());
        packet.setStack(this.getItemInHand());
        packet.setWindowId(WindowMagicNumbers.PLAYER.getId());
        packet.setSelectedSlot(this.itemInHandSlot);
        packet.setSlot(this.itemInHandSlot);
        return packet;
    }

    /**
     * Get the number of the slot the user is currently holding in hand
     *
     * @return the slot number for the in hand item
     */
    public byte getItemInHandSlot() {
        return this.itemInHandSlot;
    }

    @Override
    public void setItemInHandSlot(byte slot) {
        if (slot > 8 || slot < 0) {
            return;
        }

        this.itemInHandSlot = slot;
        this.updateItemInHand();
    }

    public void updateItemInHandWithItem(byte slot) {
        // Inform the old item it got deselected
        io.gomint.server.inventory.item.ItemStack oldItemInHand = (io.gomint.server.inventory.item.ItemStack) this.getItemInHand();
        oldItemInHand.removeFromHand((EntityPlayer) this.owner);

        // Set item in hand index
        this.itemInHandSlot = slot;

        // Inform the item it got selected
        io.gomint.server.inventory.item.ItemStack newItemInHand =
            (io.gomint.server.inventory.item.ItemStack) this.getItemInHand();
        newItemInHand.gotInHand((EntityPlayer) this.owner);
    }

    @Override
    protected void onRemove(int slot) {
        super.onRemove(slot);

        if (slot == this.itemInHandSlot && this.owner instanceof EntityPlayer) {
            // Inform the old item it got deselected
            io.gomint.server.inventory.item.ItemStack oldItemInHand = (io.gomint.server.inventory.item.ItemStack) this.getItem(slot);
            oldItemInHand.removeFromHand((EntityPlayer) this.owner);
        }
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.PLAYER;
    }

    public void sendItemInHand() {
        EntityHuman player = (EntityHuman) this.owner;

        PacketMobEquipment packet = this.createMobEquipmentPacket(player);

        // Send it to our own if needed
        if ( player instanceof EntityPlayer ) {
            EntityPlayer p = (EntityPlayer) player;
            p.getConnection().addToSendQueue(packet);
        }
    }

}
