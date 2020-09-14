/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.tileentity;

import io.gomint.entity.Entity;
import io.gomint.math.BlockPosition;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.ChestInventory;
import io.gomint.server.inventory.ContainerInventory;
import io.gomint.server.inventory.DoubleChestInventory;
import io.gomint.server.inventory.InventoryHolder;
import io.gomint.server.inventory.item.ItemAir;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.CoordinateUtils;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.block.Chest;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "Chest")
public class ChestTileEntity extends ContainerTileEntity implements InventoryHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChestTileEntity.class);
    private final ChestInventory inventory;

    private DoubleChestInventory doubleChestInventory;

    private int pairX;
    private int pairZ;
    private boolean findable;

    public ChestTileEntity(Block block, Items items) {
        super(block, items);
        this.inventory = new ChestInventory(items, this);
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        super.fromCompound(compound);

        // Read in items
        List<Object> itemList = compound.getList("Items", false);
        if (itemList == null) return;

        for (Object item : itemList) {
            NBTTagCompound itemCompound = (NBTTagCompound) item;

            io.gomint.server.inventory.item.ItemStack itemStack = getItemStack(itemCompound);
            if (itemStack instanceof ItemAir) {
                continue;
            }

            byte slot = itemCompound.getByte("Slot", (byte) 127);
            if (slot == 127) {
                LOGGER.warn("Found item without slot information: {} @ {} setting it to the next free slot", itemStack.getMaterial(), this.block.getLocation());
                this.inventory.addItem(itemStack);
            } else {
                this.inventory.setItem(slot, itemStack);
            }
        }

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
            Location location = this.getBlock().getLocation();
            Block other = location.getWorld().getBlockAt(this.pairX, location.toBlockPosition().getY(), this.pairZ);
            return other.getBlockType() == this.getBlock().getBlockType();
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
        BlockPosition otherBP = other.getBlock().getLocation().toBlockPosition();
        long otherL = CoordinateUtils.toLong(otherBP.getX(), otherBP.getZ());

        BlockPosition thisBP = this.getBlock().getLocation().toBlockPosition();
        long thisL = CoordinateUtils.toLong(thisBP.getX(), thisBP.getZ());

        // Order them according to "natural" ordering in the world
        if (otherL > thisL) {
            this.doubleChestInventory = new DoubleChestInventory(this.items, other.getInventory(), this.inventory, this);
        } else {
            this.doubleChestInventory = new DoubleChestInventory(this.items, this.inventory, other.getInventory(), other);
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

        Location location = this.getBlock().getLocation();
        Chest other = location.getWorld().getBlockAt(this.pairX, location.toBlockPosition().getY(), this.pairZ);
        return other.getTileEntity();
    }

    private void setPair(BlockPosition otherBP) {
        this.findable = true;
        this.pairZ = otherBP.getZ();
        this.pairX = otherBP.getX();
    }

    private void setDoubleChestInventory(DoubleChestInventory doubleChestInventory) {
        this.doubleChestInventory = doubleChestInventory;
    }

    @Override
    public void update(long currentMillis, float dT) {

    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, io.gomint.inventory.item.ItemStack item) {
        // Open the chest inventory for the entity
        if (entity instanceof EntityPlayer) {
            ((EntityPlayer) entity).openInventory(this.getInventory());
        }
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        super.toCompound(compound, reason);
        compound.addValue("id", "Chest");

        if (reason == SerializationReason.PERSIST) {
            List<NBTTagCompound> nbtTagCompounds = new ArrayList<>();
            for (int i = 0; i < this.inventory.size(); i++) {
                ItemStack itemStack = (ItemStack) this.inventory.getItem(i);
                if (itemStack != null) {
                    NBTTagCompound nbtTagCompound = new NBTTagCompound("");
                    nbtTagCompound.addValue("Slot", (byte) i);
                    putItemStack(itemStack, nbtTagCompound);
                    nbtTagCompounds.add(nbtTagCompound);
                }
            }

            compound.addValue("Items", nbtTagCompounds);
        }

        compound.addValue("pairx", this.pairX);
        compound.addValue("pairz", this.pairZ);
        compound.addValue("Findable", this.findable ? (byte) 1 : (byte) 0);
    }

    /**
     * Get this chests inventory
     *
     * @return inventory of this tile
     */
    public ContainerInventory getInventory() {
        return this.doubleChestInventory != null ? this.doubleChestInventory : this.inventory;
    }

}
