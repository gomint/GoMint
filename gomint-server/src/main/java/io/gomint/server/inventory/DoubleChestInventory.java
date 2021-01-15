/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory;

import io.gomint.inventory.ChestInventory;
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
public class DoubleChestInventory extends ContainerInventory<ChestInventory> implements ChestInventory {

    private final ContainerInventory<ChestInventory> left;
    private final ContainerInventory<ChestInventory> right;

    /**
     * Create new chest inventory
     *
     * @param items factory which generates items
     * @param left  side of the inventory
     * @param right side of the inventory
     * @param owner tile entity of the chest
     */
    public DoubleChestInventory(Items items, ContainerInventory<ChestInventory> left, ContainerInventory<ChestInventory> right, InventoryHolder owner) {
        super(items, owner, left.size() + right.size());
        this.left = left;
        this.right = right;
    }

    @Override
    public ChestInventory clear() {
        this.right.clear();
        this.left.clear();
        return this;
    }

    @Override
    public WindowType getType() {
        return WindowType.CONTAINER;
    }

    @Override
    public ChestInventory item(int slot, ItemStack<?> item) {
        if ( slot < this.left.size() ) {
            this.left.item(slot, item);
            return this;
        }

        this.right.item(slot - this.left.size(), item);
        return this;
    }

    @Override
    public ItemStack<?> item(int slot) {
        return slot < this.left.size() ? this.left.item(slot) : this.right.item(slot - this.left.size());
    }

    @Override
    public int size() {
        return this.left.size() + this.right.size();
    }

    @Override
    public ItemStack<?>[] contents() {
        ItemStack<?>[] contents = new ItemStack[this.left.size() + this.right.size()];
        System.arraycopy(this.left.contents(), 0, contents, 0, this.left.size());
        System.arraycopy(this.right.contents(), 0, contents, this.left.size(), this.right.size());
        return contents;
    }

    @Override
    public void onOpen(EntityPlayer player) {
        // Sound and open animation
        if (this.viewer.size() == 1) {
            BlockPosition position = this.containerPosition();
            WorldAdapter world = this.world();

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
            BlockPosition position = this.containerPosition();
            WorldAdapter world = this.world();

            PacketBlockEvent blockEvent = new PacketBlockEvent();
            blockEvent.setPosition(position);
            blockEvent.setData1(1);
            blockEvent.setData2(0);

            world.sendToVisible(position, blockEvent, entity -> true);
            world.playSound(position.toVector().add(0.5f, 0.5f, 0.5f), Sound.CHEST_CLOSED, (byte) 1);
        }
    }

}
