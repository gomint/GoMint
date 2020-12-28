/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.GoMint;
import io.gomint.enchant.Enchantment;
import io.gomint.inventory.item.ItemAir;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.item.annotation.CanBeDamaged;
import io.gomint.server.inventory.item.helper.ItemStackPlace;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.server.world.block.Blocks;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a stack of up to 255 items of the same type which may
 * optionally also have an additional data value. May be cloned.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public abstract class ItemStack implements Cloneable, io.gomint.inventory.item.ItemStack {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemStack.class);
    private static final AtomicInteger STACK_ID = new AtomicInteger(2);

    private int stackId;
    private String material;
    private short data;
    private byte amount;
    private NBTTagCompound nbt;

    // Cached enchantments
    private Map<Class, Enchantment> enchantments;
    private boolean dirtyEnchantments;

    // Observer stuff for damaging items
    private ItemStackPlace itemStackPlace;

    // Item constructor factors
    protected Items items;
    protected Blocks blocks;

    // Damageable
    private boolean isDamageableCached;
    private boolean isDamageable;

    ItemStack setMaterial(String material) {
        this.material = material;

        if (!this.isAir()) {
            this.stackId = STACK_ID.getAndAdd(1);
        }

        return this.updateInventories(false);
    }

    /**
     * Gets the material of the item(s) on this stack.
     *
     * @return The material of the item(s) on this stack
     */
    public String getMaterial() {
        return this.material;
    }

    public int getRuntimeID() {
        return this.items.getRuntimeId(this.getMaterial());
    }

    /**
     * Get the maximum amount of calculateUsage before this item breaks
     *
     * @return maximum amount of calculateUsage
     */
    public short getMaxDamage() {
        return Short.MAX_VALUE;
    }

    /**
     * The data value of the item(s) on this stack.
     *
     * @return The data value of the item(s) on this stack
     */
    public short getData() {
        return this.data;
    }

    /**
     * Sets the additional data value of the item(s) on this stack.
     *
     * @param data The data value of the item(s) on this stack
     */
    public io.gomint.inventory.item.ItemStack setData(short data) {
        this.data = data;
        return this.updateInventories(false);
    }

    /**
     * Get the maximum amount of items which can be stored in this stack
     *
     * @return maximum amount of items which can be stored in this stack
     */
    public byte getMaximumAmount() {
        if (canBeDamaged()) {
            return 1;
        }

        return 64;
    }

    /**
     * Gets the number of items on this stack.
     *
     * @return The number of items on this stack
     */
    public byte getAmount() {
        return this.amount;
    }

    /**
     * Sets the number of items on this stack (255 max).
     *
     * @param amount The number of items on this stack
     */
    public io.gomint.inventory.item.ItemStack setAmount(int amount) {
        this.amount = amount > getMaximumAmount() ? getMaximumAmount() : (byte) amount;
        return this.updateInventories(this.amount <= 0);
    }

    /**
     * Gets the raw NBT data of the item(s) on this stack.
     *
     * @return The raw NBT data of the item(s) on this stack or null
     */
    public NBTTagCompound getNbtData() {
        return this.nbt;
    }

    /**
     * Set new nbt data into the item stack
     *
     * @param compound The raw NBT data of this item
     */
    ItemStack setNbtData(NBTTagCompound compound) {
        this.nbt = compound;
        return this;
    }

    NBTTagCompound getOrCreateNBT() {
        if (this.nbt == null) {
            this.nbt = new NBTTagCompound("");
        }

        return this.nbt;
    }

    @Override
    public io.gomint.inventory.item.ItemStack setCustomName(String name) {
        // Check if we should clear the name
        if (name == null) {
            if (this.nbt != null) {
                NBTTagCompound display = this.nbt.getCompound("display", false);
                if (display != null) {
                    display.remove("Name");

                    // Delete the display NBT when no data is in it
                    if (display.size() == 0) {
                        this.nbt.remove("display");

                        // Delete the tag when no data is in it
                        if (this.nbt.size() == 0) {
                            this.nbt = null;
                        }
                    }
                }
            }

            return this;
        }

        // Get the display tag
        NBTTagCompound display = this.getOrCreateNBT().getCompound("display", true);
        display.addValue("Name", name);

        return this;
    }

    @Override
    public String getCustomName() {
        // Check if we have a NBT tag
        if (this.nbt == null) {
            return null;
        }

        // Get display part
        NBTTagCompound display = this.nbt.getCompound("display", false);
        if (display == null) {
            return null;
        }

        return display.getString("Name", null);
    }

    @Override
    public io.gomint.inventory.item.ItemStack setLore(String... lore) {
        // Check if we should clear the name
        if (lore == null) {
            if (this.nbt != null) {
                NBTTagCompound display = this.nbt.getCompound("display", false);
                if (display != null) {
                    display.remove("Lore");

                    // Delete the display NBT when no data is in it
                    if (display.size() == 0) {
                        this.nbt.remove("display");

                        // Delete the tag when no data is in it
                        if (this.nbt.size() == 0) {
                            this.nbt = null;
                        }
                    }
                }
            }

            return this;
        }

        // Do we have a compound tag?
        if (this.nbt == null) {
            this.nbt = new NBTTagCompound("");
        }

        // Get the display tag
        NBTTagCompound display = this.nbt.getCompound("display", true);
        List<String> loreList = Arrays.asList(lore);
        display.addValue("Lore", loreList);

        return this;
    }

    @Override
    public String[] getLore() {
        // Check if we have a NBT tag
        if (this.nbt == null) {
            return null;
        }

        // Get display part
        NBTTagCompound display = this.nbt.getCompound("display", false);
        if (display == null) {
            return null;
        }

        List<Object> loreList = display.getList("Lore", false);
        if (loreList == null) {
            return null;
        }

        String[] loreCopy = new String[loreList.size()];
        for (int i = 0; i < loreList.size(); i++) {
            loreCopy[i] = (String) loreList.get(i);
        }

        return loreCopy;
    }

    @Override
    public io.gomint.inventory.item.ItemStack addEnchantment(Class<? extends Enchantment> clazz, int level) {
        short id = ((GoMintServer) GoMint.instance()).getEnchantments().getId(clazz);
        if (id == -1) {
            LOGGER.warn("Unknown enchantment: {}", clazz.getName());
            return this;
        }

        if (this.nbt == null) {
            this.nbt = new NBTTagCompound("");
        }

        List<Object> enchantmentList = this.nbt.getList("ench", true);

        LOGGER.info("Enchanting item {} with {} level {}", this, clazz.getName(), level);

        NBTTagCompound enchCompound = new NBTTagCompound(null);
        enchCompound.addValue("id", id);
        enchCompound.addValue("lvl", (short) level);
        enchantmentList.add(enchCompound);

        this.dirtyEnchantments = true;
        return this;
    }

    @Override
    public <T extends Enchantment> T getEnchantment(Class<? extends Enchantment> clazz) {
        if (this.dirtyEnchantments) {
            this.dirtyEnchantments = false;

            if (this.nbt == null) {
                return null;
            }

            List<Object> nbtEnchCompounds = this.nbt.getList("ench", false);
            if (nbtEnchCompounds == null) {
                return null;
            }

            this.enchantments = new HashMap<>();
            for (Object compound : nbtEnchCompounds) {
                NBTTagCompound enchantCompound = (NBTTagCompound) compound;
                io.gomint.server.enchant.Enchantment enchantment = ((GoMintServer) GoMint.instance()).getEnchantments().create(
                    enchantCompound.getShort("id", (short) 0),
                    enchantCompound.getShort("lvl", (short) 0)
                );

                this.enchantments.put(enchantment.getClass().getInterfaces()[0], enchantment);
            }
        }

        return this.enchantments == null ? null : (T) this.enchantments.get(clazz);
    }

    @Override
    public io.gomint.inventory.item.ItemStack removeEnchantment(Class<? extends Enchantment> clazz) {
        short id = ((GoMintServer) GoMint.instance()).getEnchantments().getId(clazz);
        if (id == -1) {
            return this;
        }

        if (this.nbt == null) {
            return this;
        }

        List<Object> enchantmentList = this.nbt.getList("ench", false);
        if (enchantmentList == null) {
            return this;
        }

        for (Object nbtObject : new ArrayList<>(enchantmentList)) {
            NBTTagCompound enchCompound = (NBTTagCompound) nbtObject;
            if (enchCompound.getShort("id", (short) -1) == id) {
                enchantmentList.remove(enchCompound);
                this.dirtyEnchantments = true;
                break;
            }
        }

        if (enchantmentList.isEmpty()) {
            this.nbt.remove("ench");
        }

        return this;
    }

    @Override
    public ItemStack clone() {
        try {
            ItemStack clone = (ItemStack) super.clone();
            clone.dirtyEnchantments = true;
            clone.enchantments = this.enchantments;
            clone.material = this.material;
            clone.data = this.data;
            clone.amount = this.amount;
            clone.nbt = (this.nbt == null ? null : this.nbt.deepClone(""));
            clone.itemStackPlace = null;
            clone.items = this.items;
            clone.blocks = this.blocks;

            if (!clone.isAir()) {
                clone.stackId = STACK_ID.getAndAdd( 1);
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone of ItemStack failed", e);
        }
    }

    /**
     * Return the block from this item
     *
     * @return id for the block when this item is placed
     */
    public Block getBlock() {
        BlockIdentifier identifier = BlockRuntimeIDs.toBlockIdentifier(this.getMaterial(), null);
        if (identifier == null) {
            return null;
        }

        return this.blocks.get(identifier);
    }

    /**
     * This gets called when a item was placed down as a block. The amount gets decreased and the inventories this item is
     * in get updated (if <= 0 to air, otherwise the amount gets updated)
     */
    public void afterPlacement() {
        // In a normal case the amount decreases
        this.updateInventories(--this.amount <= 0);
    }

    /**
     * Check if we need to update this item in its inventories
     *
     * @param replaceWithAir if the item should be deleted (replaced with air)
     * @return the item instance used or the air instance which has been set
     */
    ItemStack updateInventories(boolean replaceWithAir) {
        if (replaceWithAir) {
            ItemStack itemStack = (ItemStack) ItemAir.create(0);

            if (this.itemStackPlace != null) {
                this.itemStackPlace.getInventory().setItem(this.itemStackPlace.getSlot(), itemStack);
            }

            return itemStack;
        } else {
            if (this.itemStackPlace != null) {
                this.itemStackPlace.getInventory().setItem(this.itemStackPlace.getSlot(), this);
            }
        }

        return this;
    }

    public void removeFromHand(EntityPlayer player) {
        // Normal items do nothing
    }

    public void gotInHand(EntityPlayer player) {
        // Normal items do nothing
    }

    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock) {
        return false;
    }

    /**
     * Same rules as in {@link #calculateUsage(int)} will be applied. If the result of {@link #calculateUsage(int)} is
     * true all inventories in which this item is will be notified to set air for this item, if its false the
     * updated itemstack gets set
     *
     * @param damage which should be applied
     */
    public void calculateUsageAndUpdate(int damage) {
        this.updateInventories(this.calculateUsage(damage));
    }

    /**
     * Calculate the damage done to this stack when using it. When a item has damage calculation enabled with
     * {@link CanBeDamaged} annotated, the parameter damage is added to the current data and checked against
     * {@link ItemStack#getMaxDamage()}. If the damage is high enough the amount will be decreased by one.
     * <p>
     * If the item isn't annotated the damage parameter is ignored and a amount of this stack is removed
     *
     * @param damage which should be applied
     * @return true when the stack is empty, false if the stack has still usages in it
     */
    public boolean calculateUsage(int damage) {
        // Default no item uses calculateUsage
        if (canBeDamaged()) {
            // Check if we need to destroy this item stack
            int currentDamage = this.getDamageFromNBT();
            currentDamage += damage;
            return this.setDamageAndCheckAmount(currentDamage);
        }

        return false;
    }

    public boolean setDamageAndCheckAmount(int damage) {
        int intDamage = damage;
        if (intDamage >= this.getMaxDamage()) {
            // Remove one amount
            if (--this.amount <= 0) {
                return true;
            }

            intDamage = 0;
        }

        this.setDamageToNBT(intDamage);
        return false;
    }

    public int getDamageFromNBT() {
        return this.getOrCreateNBT().getInteger("Damage", 0);
    }

    public void setDamageToNBT(int damage) {
        this.getOrCreateNBT().addValue("Damage", damage);
    }

    @Override
    public float getDamage() {
        if (!canBeDamaged()) {
            return 0;
        }

        return this.getDamageFromNBT() / (float) this.getMaxDamage();
    }

    @Override
    public void setDamage(float damage) {
        if (canBeDamaged()) {
            this.setDamageAndCheckAmount((int) (this.getMaxDamage() * damage));
        }
    }

    public boolean canBeDamaged() {
        if (this.isDamageableCached) {
            return this.isDamageable;
        }

        Class current = this.getClass();
        boolean usesData;

        do {
            usesData = current.isAnnotationPresent(CanBeDamaged.class);
            current = current.getSuperclass();
        } while (!usesData && !Object.class.equals(current));

        this.isDamageable = usesData;
        this.isDamageableCached = true;

        return usesData;
    }

    /**
     * Get the enchant ability of this item
     *
     * @return enchantment possibility
     */
    public int getEnchantAbility() {
        return 0;
    }

    public void addPlace(Inventory inventory, int slot) {
        if (this.itemStackPlace != null) {
            LOGGER.warn("Did not remove the previous itemStackPlace", new Exception());
        }

        this.itemStackPlace = new ItemStackPlace(slot, inventory);
    }

    public void removePlace() {
        this.itemStackPlace = null;
    }

    /**
     * Packets can define additional data for items (currently only the shield seems to be doing that)
     *
     * @param buffer from the network which holds the data
     */
    public void readAdditionalData(PacketBuffer buffer) {

    }

    /**
     * Write additional item data to the network
     *
     * @param buffer from/to the network
     */
    public void writeAdditionalData(PacketBuffer buffer) {

    }

    public int getStackId() {
        return this.isAir() ? 0 : this.stackId;
    }

    public void setStackId(int id) {
        this.stackId = id;
    }

    protected void setBlockId(String blockId) {
        this.setMaterial(blockId);
    }

    @Override
    public String toString() {
        return "{\"_class\":\"ItemStack\", " +
            "\"stackId\":\"" + stackId + "\"" + ", " +
            "\"material\":" + (material == null ? "null" : "\"" + material + "\"") + ", " +
            "\"data\":\"" + data + "\"" + ", " +
            "\"amount\":\"" + amount + "\"" + ", " +
            "\"nbt\":" + (nbt == null ? "null" : nbt) + ", " +
            "\"enchantments\":" + (enchantments == null ? "null" : "\"" + enchantments + "\"") + ", " +
            "\"dirtyEnchantments\":\"" + dirtyEnchantments + "\"" + ", " +
            "\"itemStackPlace\":" + (itemStackPlace == null ? "null" : itemStackPlace) + ", " +
            "\"items\":" + (items == null ? "null" : items) + ", " +
            "\"blocks\":" + (blocks == null ? "null" : blocks) + ", " +
            "\"isDamageableCached\":\"" + isDamageableCached + "\"" + ", " +
            "\"isDamageable\":\"" + isDamageable + "\"" +
            "}";
    }

    public ItemStack setItems(Items items) {
        this.items = items;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStack itemStack = (ItemStack) o;
        return material.equals(itemStack.material) &&
            data == itemStack.data &&
            Objects.equals(nbt, itemStack.nbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, data, nbt);
    }

    public boolean isAir() {
        return "minecraft:air".equals(this.material);
    }

    public void setBlocks(Blocks blocks) {
        this.blocks = blocks;
    }

    public ItemStackPlace getItemStackPlace() {
        return itemStackPlace;
    }

    public boolean isEnchanted() {
        if (this.nbt == null) {
            return false;
        }

        return this.nbt.getList("ench", false) != null;
    }

}
