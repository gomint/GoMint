/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.component;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.tileentity.TileEntity;
import io.gomint.server.inventory.ContainerInventory;
import io.gomint.server.inventory.item.ItemAir;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.util.collection.FixedReadOnlyMap;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.server.world.block.Block;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.data.Facing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTileEntityComponent implements TileEntityComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTileEntityComponent.class);

    private final TileEntity entity;
    private final Items items;

    public AbstractTileEntityComponent(TileEntity entity, Items items) {
        this.entity = entity;
        this.items = items;
    }

    ItemStack<?> getItemStack(NBTTagCompound compound) {
        // Item not there?
        if (compound == null) {
            return this.items.create(0, (short) 0, (byte) 0, null);
        }

        short data = compound.getShort("Damage", (short) 0);
        byte amount = compound.getByte("Count", (byte) 0);
        String name = compound.getString("Name", null);
        NBTTagCompound tag = compound.getCompound("tag", false);

        if (name != null) {
            return this.items.create(name, data, amount, tag);
        }

        // This is needed since minecraft changed from storing raw ids to string keys somewhere in 1.7 / 1.8
        try {
            return this.items.create(compound.getShort("id", (short) 0), data, amount, tag);
        } catch (ClassCastException e) {
            try {
                return this.items.create(compound.getString("id", "minecraft:air"), data, amount, tag);
            } catch (ClassCastException e1) {
                return this.items.create(compound.getInteger("id", 0), data, amount, tag);
            }
        }
    }

    void putItemStack(ItemStack<?> itemStack, NBTTagCompound compound) {
        io.gomint.server.inventory.item.ItemStack<?> sStack = (io.gomint.server.inventory.item.ItemStack<?>) itemStack;

        compound.addValue("Name", sStack.material());
        compound.addValue("Damage", sStack.data());
        compound.addValue("Count", sStack.amount());

        if (sStack.nbtData() != null) {
            NBTTagCompound itemTag = sStack.nbtData().deepClone("tag");
            compound.addValue("tag", itemTag);
        }
    }

    protected Block getBlock() {
        return this.entity.getBlock();
    }

    public abstract void interact(Entity<?> entity, Facing face, Vector facePos, ItemStack<?> item);

    protected void readInventory(NBTTagCompound compound, ContainerInventory<?> inventory) {
        // Read in items
        List<Object> itemList = compound.getList("Items", false);
        if (itemList == null) return;

        for (Object item : itemList) {
            NBTTagCompound itemCompound = (NBTTagCompound) item;

            ItemStack<?> itemStack = getItemStack(itemCompound);
            if (itemStack instanceof ItemAir) {
                continue;
            }

            byte slot = itemCompound.getByte("Slot", (byte) 127);
            if (slot == 127) {
                LOGGER.warn("Found item without slot information: {} @ {} setting it to the next free slot", ((io.gomint.server.inventory.item.ItemStack<?>) itemStack).material(), this.getBlock().position());
                inventory.addItem(itemStack);
            } else {
                inventory.item(slot, itemStack);
            }
        }
    }

    protected void writeInventory(NBTTagCompound compound, ContainerInventory<?> inventory) {
        List<NBTTagCompound> nbtTagCompounds = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            io.gomint.server.inventory.item.ItemStack<?> itemStack = (io.gomint.server.inventory.item.ItemStack<?>) inventory.item(i);
            if (itemStack != null) {
                NBTTagCompound nbtTagCompound = new NBTTagCompound("");
                nbtTagCompound.addValue("Slot", (byte) i);
                putItemStack(itemStack, nbtTagCompound);
                nbtTagCompounds.add(nbtTagCompound);
            }
        }

        compound.addValue("Items", nbtTagCompounds);
    }

    protected void writeItem(NBTTagCompound compound, String key, ItemStack<?> holdingItem) {
        putItemStack(holdingItem, compound.getCompound(key, true));
    }

    protected ItemStack<?> readItem(NBTTagCompound compound, String key) {
        return getItemStack(compound.getCompound(key, false));
    }

    protected BlockIdentifier getBlockIdentifier(NBTTagCompound compound) {
        if (compound == null) {
            return null;
        }

        NBTTagCompound states = compound.getCompound("states", false);
        FixedReadOnlyMap stateMap = null;
        if (states != null && states.size() > 0) {
            stateMap = new FixedReadOnlyMap(states.entrySet());
        }

        return BlockRuntimeIDs.toBlockIdentifier(compound.getString("name", "minecraft:air"), stateMap);
    }

    protected void putBlockIdentifier(BlockIdentifier identifier, NBTTagCompound compound) {
        compound.addValue("name", identifier.getBlockId());
        compound.addValue("states", identifier.getNbt());
    }

}
