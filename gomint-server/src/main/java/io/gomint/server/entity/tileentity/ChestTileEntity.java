/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.component.InventoryComponent;
import io.gomint.server.inventory.ChestInventory;
import io.gomint.server.inventory.ContainerInventory;
import io.gomint.server.inventory.DoubleChestInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.block.Chest;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "Chest")
public class ChestTileEntity extends ContainerTileEntity implements InventoryHolder {

    private final ChestInventory inventory;
    private final InventoryComponent inventoryComponent;

    private DoubleChestInventory doubleChestInventory;

    private int pairX;
    private int pairZ;
    private boolean findable;

    public ChestTileEntity(Block block, Items items) {
        super(block, items);
        this.inventory = new ChestInventory(items, this);
        this.inventoryComponent = new InventoryComponent(this, items, this.inventory);
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);

        // Read in items
        this.inventoryComponent.fromCompound(compound);

        // Get pair
        this.pairX = compound.getInteger("pairx", 0);
        this.pairZ = compound.getInteger("pairz", 0);
        this.findable = compound.getByte("Findable", (byte) 0) == 1;

        // Reconstruct pair
        if (this.isPaired()) {
            this.pair(this.getPaired());
        } else {
            this.unpair();
        }
    }

    /**
     * Check if this chest is paired with another chest
     *
     * @return true if paired, false otherwise
     */
    public boolean isPaired() {
        if (this.findable) {
            BlockPosition position = this.getBlock().position();
            Block other = this.getBlock().world().blockAt(this.pairX, position.y(), this.pairZ);
            return other.blockType() == this.getBlock().blockType();
        }

        return false;
    }

    /**
     * Pair a chest to another one
     *
     * @param other chest which should be paired with this one
     */
    public void pair(ChestTileEntity other) {
        // Get the positions of both sides of the pair
        BlockPosition otherBP = other.getBlock().position();
        long otherL = CoordinateUtils.toLong(otherBP.x(), otherBP.z());

        BlockPosition thisBP = this.getBlock().position();
        long thisL = CoordinateUtils.toLong(thisBP.x(), thisBP.z());

        // Order them according to "natural" ordering in the world
        if (otherL > thisL) {
            this.doubleChestInventory = new DoubleChestInventory(this.items, other.inventory(), this.inventory, this);
        } else {
            this.doubleChestInventory = new DoubleChestInventory(this.items, this.inventory, other.inventory(), other);
        }

        other.setDoubleChestInventory(this.doubleChestInventory);

        // Set the other pair side into the tiles
        other.setPair(thisBP);
        this.setPair(otherBP);
    }

    /**
     * Unpair this chest from its paired part
     */
    public void unpair() {
        ChestTileEntity other = this.getPaired();
        if (other != null) {
            other.setDoubleChestInventory(null);
            other.resetPair();
        }

        this.setDoubleChestInventory(null);
        this.resetPair();
    }

    private void resetPair() {
        this.findable = false;
        this.pairX = 0;
        this.pairZ = 0;
    }

    private ChestTileEntity getPaired() {
        if (!this.isPaired()) {
            return null;
        }

        BlockPosition position = this.getBlock().position();
        Chest other = this.getBlock().world().blockAt(this.pairX, position.y(), this.pairZ);
        return other.tileEntity();
    }

    private void setPair(BlockPosition otherBP) {
        this.findable = true;
        this.pairZ = otherBP.z();
        this.pairX = otherBP.x();
    }

    private void setDoubleChestInventory(DoubleChestInventory doubleChestInventory) {
        this.doubleChestInventory = doubleChestInventory;
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item) {
        // Open the chest inventory for the entity
        if (entity instanceof EntityPlayer) {
            ((EntityPlayer) entity).openInventory(this.inventory());
        }
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);
        compound.addValue("id", "Chest");

        this.inventoryComponent.toCompound(compound, reason);

        compound.addValue("pairx", this.pairX);
        compound.addValue("pairz", this.pairZ);
        compound.addValue("Findable", this.findable ? (byte) 1 : (byte) 0);
    }

    /**
     * Get this chests inventory
     *
     * @return inventory of this tile
     */
    public ContainerInventory<io.gomint.inventory.ChestInventory> inventory() {
        return this.doubleChestInventory != null ? this.doubleChestInventory : this.inventory;
    }

}
