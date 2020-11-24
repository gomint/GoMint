/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.component;

import io.gomint.GoMint;
import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemBurnable;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.GoMintServer;
import io.gomint.server.crafting.SmeltingRecipe;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.ContainerInventory;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.packet.PacketSetContainerData;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.block.Furnace;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.Facing;

import java.util.ArrayList;
import java.util.List;

public class SmeltingComponent extends AbstractTileEntityComponent {

    private static final int CONTAINER_PROPERTY_TICK_COUNT = 0;
    private static final int CONTAINER_PROPERTY_LIT_TIME = 1;
    private static final int CONTAINER_PROPERTY_LIT_DURATION = 2;

    private ContainerInventory inventory;

    private short cookTime;
    private short burnTime;
    private short burnDuration;

    private io.gomint.inventory.item.ItemStack output;

    public SmeltingComponent(ContainerInventory inventory, TileEntity entity, Items items) {
        super(entity, items);

        this.inventory = inventory;
        this.inventory.addObserver(pair -> {
            if (pair.getFirst() == 0) {
                // Input slot has changed
                onInputChanged(pair.getSecond());
            }
        });
    }

    private void onInputChanged(io.gomint.inventory.item.ItemStack input) {
        // If we currently smelt reset progress
        if (this.cookTime > 0) {
            this.cookTime = 0;

            for (Entity viewer : this.inventory.getViewers()) {
                if (viewer instanceof io.gomint.server.entity.EntityPlayer) {
                    this.sendTickProgress((io.gomint.server.entity.EntityPlayer) viewer);
                }
            }
        }

        // Check for new recipe
        this.checkForRecipe(input);
    }

    private void sendTickProgress(io.gomint.server.entity.EntityPlayer player) {
        byte windowId = player.getWindowId(this.inventory);

        PacketSetContainerData containerData = new PacketSetContainerData();
        containerData.setWindowId(windowId);
        containerData.setKey(CONTAINER_PROPERTY_TICK_COUNT);
        containerData.setValue(this.cookTime);
        player.getConnection().addToSendQueue(containerData);
    }

    private void sendFuelInfo(io.gomint.server.entity.EntityPlayer player) {
        byte windowId = player.getWindowId(this.inventory);

        PacketSetContainerData containerData = new PacketSetContainerData();
        containerData.setWindowId(windowId);
        containerData.setKey(CONTAINER_PROPERTY_LIT_TIME);
        containerData.setValue(this.burnTime);
        player.getConnection().addToSendQueue(containerData);

        containerData = new PacketSetContainerData();
        containerData.setWindowId(windowId);
        containerData.setKey(CONTAINER_PROPERTY_LIT_DURATION);
        containerData.setValue(this.burnDuration);
        player.getConnection().addToSendQueue(containerData);
    }

    private void sendDataProperties(io.gomint.server.entity.EntityPlayer player) {
        byte windowId = player.getWindowId(this.inventory);

        PacketSetContainerData containerData = new PacketSetContainerData();
        containerData.setWindowId(windowId);
        containerData.setKey(CONTAINER_PROPERTY_TICK_COUNT);
        containerData.setValue(this.cookTime);
        player.getConnection().addToSendQueue(containerData);

        containerData = new PacketSetContainerData();
        containerData.setWindowId(windowId);
        containerData.setKey(CONTAINER_PROPERTY_LIT_TIME);
        containerData.setValue(this.burnTime);
        player.getConnection().addToSendQueue(containerData);

        containerData = new PacketSetContainerData();
        containerData.setWindowId(windowId);
        containerData.setKey(CONTAINER_PROPERTY_LIT_DURATION);
        containerData.setValue(this.burnDuration);
        player.getConnection().addToSendQueue(containerData);
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        if (reason == SerializationReason.PERSIST) {
            List<NBTTagCompound> itemCompounds = new ArrayList<>();
            for (int i = 0; i < this.inventory.size(); i++) {
                ItemStack itemStack = (ItemStack) this.inventory.getItem(i);
                if (!(itemStack instanceof ItemAir)) {
                    NBTTagCompound itemCompound = new NBTTagCompound("");
                    itemCompound.addValue("Slot", (byte) i);
                    putItemStack(itemStack, itemCompound);
                    itemCompounds.add(itemCompound);
                }
            }

            compound.addValue("Items", itemCompounds);

            compound.addValue("CookTime", this.cookTime);
            compound.addValue("BurnTime", this.burnTime);
            compound.addValue("BurnDuration", this.burnDuration);
        }
    }

    private void checkForRecipe(io.gomint.inventory.item.ItemStack input) {
        // Reset just to be sure that the new item needs to have a new recipe
        this.output = null;

        // Check if there is a smelting recipe present
        GoMintServer server = (GoMintServer) GoMint.instance();
        SmeltingRecipe recipe = server.getRecipeManager().getSmeltingRecipe(input);
        if (recipe != null) {
            for (io.gomint.inventory.item.ItemStack stack : recipe.createResult()) {
                this.output = stack; // Smelting only has one result
            }
        }
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {
        List<Object> itemCompounds = compound.getList("Items", false);
        if (itemCompounds != null) {
            for (Object itemCompound : itemCompounds) {
                NBTTagCompound cd = (NBTTagCompound) itemCompound;

                byte slot = cd.getByte("Slot", (byte) -1);
                if (slot == -1) {
                    this.inventory.addItem(getItemStack(cd));
                } else {
                    this.inventory.setItem(slot, getItemStack(cd));

                    if (slot == 0) {
                        checkForRecipe(this.inventory.getItem(0));
                    }
                }
            }
        }

        this.cookTime = compound.getShort("CookTime", (short) 0);
        this.burnTime = compound.getShort("BurnTime", (short) 0);
        this.burnDuration = compound.getShort("BurnDuration", (short) 0);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        // Check if we "crafted"
        if (this.output != null && this.burnTime > 0) {
            // Check if visuals are correct!
            Block maybeBurningFurnace = this.getBlock();
            if (maybeBurningFurnace.getBlockType() == BlockType.FURNACE) {
                Furnace furnace = (Furnace) maybeBurningFurnace;
                if (!furnace.isBurning()) {
                    furnace.setBurning(true);
                }
            }

            this.cookTime++;

            if (this.cookTime >= 200) {
                // We did it
                ItemStack itemStack = (ItemStack) this.inventory.getItem(2);
                if (itemStack.getItemType() != this.output.getItemType()) {
                    this.inventory.setItem(2, this.output);
                } else {
                    itemStack.setAmount(itemStack.getAmount() + this.output.getAmount());
                    this.inventory.setItem(2, itemStack);
                }

                ItemStack input = (ItemStack) this.inventory.getItem(0);
                input.afterPlacement();

                this.cookTime = 0;
                this.broadcastCookTime();
            } else if (this.cookTime % 20 == 0) {
                this.broadcastCookTime();
            }
        }

        // Check if we have fuel loaded
        if (this.burnDuration > 0) {
            this.burnTime--;

            // Check if we can refuel
            boolean didRefuel = false;
            if (this.burnTime == 0) {
                this.burnDuration = 0;
                if (this.checkForRefuel()) {
                    didRefuel = true;
                    this.broadcastFuelInfo();
                } else {
                    Furnace furnace = (Furnace) this.getBlock();
                    if (furnace.isBurning()) {
                        furnace.setBurning(false);
                    }
                }
            }

            // Broadcast data
            if (!didRefuel && (this.burnTime == 0 || this.burnTime % 20 == 0)) {
                this.broadcastFuelInfo();
            }
        } else {
            if (this.checkForRefuel()) {
                this.broadcastFuelInfo();
            }
        }
    }

    private void broadcastCookTime() {
        for (Entity viewer : this.inventory.getViewers()) {
            if (viewer instanceof io.gomint.server.entity.EntityPlayer) {
                this.sendTickProgress((io.gomint.server.entity.EntityPlayer) viewer);
            }
        }
    }

    private void broadcastFuelInfo() {
        for (Entity viewer : this.inventory.getViewers()) {
            if (viewer instanceof io.gomint.server.entity.EntityPlayer) {
                this.sendFuelInfo((io.gomint.server.entity.EntityPlayer) viewer);
            }
        }
    }

    private boolean checkForRefuel() {
        // We need a recipe to load fuel
        if (this.canProduceOutput()) {
            io.gomint.inventory.item.ItemStack fuelItem = this.inventory.getItem(1);
            if (fuelItem instanceof ItemBurnable) {
                long duration = ((ItemBurnable) fuelItem).getBurnTime();

                if (fuelItem.getAmount() > 0) {
                    ItemStack itemStack = (ItemStack) fuelItem;
                    itemStack.afterPlacement();

                    this.burnDuration = (short) (duration / 50);
                    this.burnTime = this.burnDuration;

                    return true;
                }
            }
        }

        return false;
    }

    private boolean canProduceOutput() {
        // Do we have a recipe loaded?
        if (this.output == null) {
            return false;
        }

        // Do we have enough input?
        ItemStack input = (ItemStack) this.inventory.getItem(0);
        if (input.getItemType() == ItemType.AIR || input.getAmount() == 0) {
            return false;
        }

        // Do we have enough space in the output slot for this
        io.gomint.inventory.item.ItemStack itemStack = this.inventory.getItem(2);
        if (itemStack.getItemType() == this.output.getItemType()) {
            return itemStack.getAmount() + this.output.getAmount() <= itemStack.getMaximumAmount();
        }

        return true;
    }

    @Override
    public void interact(Entity entity, Facing face, Vector facePos, io.gomint.inventory.item.ItemStack item) {
        // Send the needed container data
        this.sendDataProperties((io.gomint.server.entity.EntityPlayer) entity);
    }
}
