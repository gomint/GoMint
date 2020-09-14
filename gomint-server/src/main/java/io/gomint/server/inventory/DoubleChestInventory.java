/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.packet.PacketBlockEvent;
import io.gomint.server.network.type.WindowType;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.Sound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class DoubleChestInventory extends ContainerInventory {

    private final ContainerInventory left;
    private final ContainerInventory right;

    /**
     * Create new chest inventory
     *
     * @param items factory which generates items
     * @param left  side of the inventory
     * @param right side of the inventory
     * @param owner tile entity of the chest
     */
    public DoubleChestInventory(Items items, ContainerInventory left, ContainerInventory right, InventoryHolder owner) {
        super(items, owner, left.size() + right.size());
        this.left = left;
        this.right = right;
    }

    @Override
    public void clear() {
        // We don't clear this, both sides need to be cleared on their own
    }

    @Override
    public WindowType getType() {
        return WindowType.CONTAINER;
    }

    @Override
    public void setItem(int slot, ItemStack item) {
        if ( slot < this.left.size() ) {
            this.left.setItem(slot, item);
            return;
        }

        this.right.setItem(slot - this.left.size(), item);
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot < this.left.size() ? this.left.getItem(slot) : this.right.getItem(slot - this.left.size());
    }

    @Override
    public int size() {
        return this.left.size() + this.right.size();
    }

    @Override
    public ItemStack[] getContents() {
        ItemStack[] contents = new ItemStack[this.left.size() + this.right.size()];
        System.arraycopy(this.left.getContents(), 0, contents, 0, this.left.size());
        System.arraycopy(this.right.getContents(), 0, contents, this.left.size(), this.right.size());
        return contents;
    }

    @Override
    public void onOpen(EntityPlayer player) {
        // Sound and open animation
        if (this.viewer.size() == 1) {
            BlockPosition position = this.getContainerPosition();
            WorldAdapter world = this.getWorld();

            PacketBlockEvent blockEvent = new PacketBlockEvent();
            blockEvent.setPosition(position);
            blockEvent.setData1(1);
            blockEvent.setData2(2);

            world.sendToVisible(position, blockEvent, entity -> true);
            world.playSound(position.toVector().add(0.5f, 0.5f, 0.5f), Sound.CHEST_OPEN, (byte) 1);
        }
    }

    @Override
    public void onClose(EntityPlayer player) {
        // Sound and close animation
        if (this.viewer.size() == 1) {
            BlockPosition position = this.getContainerPosition();
            WorldAdapter world = this.getWorld();

            PacketBlockEvent blockEvent = new PacketBlockEvent();
            blockEvent.setPosition(position);
            blockEvent.setData1(1);
            blockEvent.setData2(0);

            world.sendToVisible(position, blockEvent, entity -> true);
            world.playSound(position.toVector().add(0.5f, 0.5f, 0.5f), Sound.CHEST_CLOSED, (byte) 1);
        }
    }

}
