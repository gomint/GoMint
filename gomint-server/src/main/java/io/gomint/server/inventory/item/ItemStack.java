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
public abstract class ItemStack<I extends io.gomint.inventory.item.ItemStack<I>> implements Cloneable, io.gomint.inventory.item.ItemStack<I> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemStack.class);
    private static final AtomicInteger STACK_ID = new AtomicInteger(2);

    private int stackId;
    private String material;
    private short data;
    private byte amount;
    private NBTTagCompound nbt;

    // Cached enchantments
    private Map<Class<? extends Enchantment>, Enchantment> enchantments;
    private boolean dirtyEnchantments;

    // Observer stuff for damaging items
    private ItemStackPlace itemStackPlace;

    // Item constructor factors
    protected Items items;
    protected Blocks blocks;

    // Damageable
    private boolean isDamageableCached;
    private boolean isDamageable;

    I material(String material) {
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
    public String material() {
        return this.material;
    }

    public int runtimeID() {
        return this.items.getRuntimeId(this.material());
    }

    /**
     * Get the maximum amount of calculateUsage before this item breaks
     *
     * @return maximum amount of calculateUsage
     */
    public short maxDamage() {
        return Short.MAX_VALUE;
    }

    /**
     * The data value of the item(s) on this stack.
     *
     * @return The data value of the item(s) on this stack
     */
    public short data() {
        return this.data;
    }

    /**
     * Sets the additional data value of the item(s) on this stack.
     *
     * @param data The data value of the item(s) on this stack
     */
    public I data(short data) {
        this.data = data;
        return this.updateInventories(false);
    }

    @Override
    public byte maximumAmount() {
        if (canBeDamaged()) {
            return 1;
        }

        return 64;
    }

    @Override
    public byte amount() {
        return this.amount;
    }

    @Override
    public I amount(int amount) {
        this.amount = amount > maximumAmount() ? maximumAmount() : (byte) amount;
        return this.updateInventories(this.amount <= 0);
    }

    /**
     * Gets the raw NBT data of the item(s) on this stack.
     *
     * @return The raw NBT data of the item(s) on this stack or null
     */
    public NBTTagCompound nbtData() {
        return this.nbt;
    }

    /**
     * Set new nbt data into the item stack
     *
     * @param compound The raw NBT data of this item
     */
    I nbtData(NBTTagCompound compound) {
        this.nbt = compound;
        return (I) this;
    }

    NBTTagCompound nbt() {
        if (this.nbt == null) {
            this.nbt = new NBTTagCompound("");
        }

        return this.nbt;
    }

    @Override
    public I customName(String name) {
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

            return (I) this;
        }

        // Get the display tag
        NBTTagCompound display = this.nbt().getCompound("display", true);
        display.addValue("Name", name);

        return (I) this;
    }

    @Override
    public String customName() {
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
    public I lore(String... lore) {
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

            return (I) this;
        }

        // Get the display tag
        NBTTagCompound display = this.nbt().getCompound("display", true);
        List<String> loreList = Arrays.asList(lore);
        display.addValue("Lore", loreList);

        return (I) this;
    }

    @Override
    public String[] lore() {
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
    public I enchant(Class<? extends Enchantment> clazz, int level) {
        short id = ((GoMintServer) GoMint.instance()).enchantments().idOf(clazz);
        if (id == -1) {
            LOGGER.warn("Unknown enchantment: {}", clazz.getName());
            return (I) this;
        }

        List<Object> enchantmentList = this.nbt().getList("ench", true);

        LOGGER.info("Enchanting item {} with {} level {}", this, clazz.getName(), level);

        NBTTagCompound enchCompound = new NBTTagCompound(null);
        enchCompound.addValue("id", id);
        enchCompound.addValue("lvl", (short) level);
        enchantmentList.add(enchCompound);

        this.dirtyEnchantments = true;
        return (I) this;
    }

    @Override
    public <T extends Enchantment> T enchantment(Class<? extends Enchantment> clazz) {
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
                io.gomint.server.enchant.Enchantment enchantment = ((GoMintServer) GoMint.instance()).enchantments().create(
                    enchantCompound.getShort("id", (short) 0),
                    enchantCompound.getShort("lvl", (short) 0)
                );

                this.enchantments.put((Class<? extends Enchantment>) enchantment.getClass().getInterfaces()[0], enchantment);
            }
        }

        return this.enchantments == null ? null : (T) this.enchantments.get(clazz);
    }

    @Override
    public I removeEnchantment(Class<? extends Enchantment> clazz) {
        short id = ((GoMintServer) GoMint.instance()).enchantments().idOf(clazz);
        if (id == -1) {
            return (I) this;
        }

        if (this.nbt == null) {
            return (I) this;
        }

        List<Object> enchantmentList = this.nbt.getList("ench", false);
        if (enchantmentList == null) {
            return (I) this;
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

        return (I) this;
    }

    @Override
    public ItemStack<I> clone() {
        try {
            ItemStack<I> clone = (ItemStack<I>) super.clone();
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
    public Block block() {
        BlockIdentifier identifier = BlockRuntimeIDs.toBlockIdentifier(this.material(), null);
        if (identifier == null) {
            return null;
        }

        return this.blocks.get(identifier);
    }

    /**
     * This gets called when a item was placed down as a block. The amount gets decreased and the inventories this item is
     * in get updated (if <= 0 to air, otherwise the amount gets updated)
     */
    public I afterPlacement() {
        // In a normal case the amount decreases
        return this.updateInventories(--this.amount <= 0);
    }

    /**
     * Check if we need to update this item in its inventories
     *
     * @param replaceWithAir if the item should be deleted (replaced with air)
     * @return the item instance used or the air instance which has been set
     */
    I updateInventories(boolean replaceWithAir) {
        if (replaceWithAir) {
            ItemAir itemStack = ItemAir.create(0);

            if (this.itemStackPlace != null) {
                this.itemStackPlace.getInventory().item(this.itemStackPlace.getSlot(), itemStack);
            }

            return null;
        } else {
            if (this.itemStackPlace != null) {
                this.itemStackPlace.getInventory().item(this.itemStackPlace.getSlot(), this);
            }
        }

        return (I) this;
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
     * {@link ItemStack#maxDamage()}. If the damage is high enough the amount will be decreased by one.
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
            int currentDamage = this.damageFromNBT();
            currentDamage += damage;
            return this.damageAndCheckAmount(currentDamage);
        }

        return false;
    }

    public boolean damageAndCheckAmount(int damage) {
        int intDamage = damage;
        if (intDamage >= this.maxDamage()) {
            // Remove one amount
            if (--this.amount <= 0) {
                return true;
            }

            intDamage = 0;
        }

        this.damageToNBT(intDamage);
        return false;
    }

    public int damageFromNBT() {
        return this.nbt().getInteger("Damage", 0);
    }

    public void damageToNBT(int damage) {
        this.nbt().addValue("Damage", damage);
    }

    @Override
    public float damage() {
        if (!canBeDamaged()) {
            return 0;
        }

        return this.damageFromNBT() / (float) this.maxDamage();
    }

    @Override
    public I damage(float damage) {
        if (canBeDamaged()) {
            this.damageAndCheckAmount((int) (this.maxDamage() * damage));
        }

        return (I) this;
    }

    public boolean canBeDamaged() {
        if (this.isDamageableCached) {
            return this.isDamageable;
        }

        Class<?> current = this.getClass();
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
    public int enchantAbility() {
        return 0;
    }

    public void place(Inventory<?> inventory, int slot) {
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

    public int stackId() {
        return this.isAir() ? 0 : this.stackId;
    }

    public I stackId(int id) {
        this.stackId = id;
        return (I) this;
    }

    protected I blockId(String blockId) {
        return this.material(blockId);
    }

    @Override
    public String toString() {
        return "{\"_class\":\"ItemStack\", " +
            "\"stackId\":\"" + this.stackId + "\"" + ", " +
            "\"material\":" + (this.material == null ? "null" : "\"" + this.material + "\"") + ", " +
            "\"data\":\"" + this.data + "\"" + ", " +
            "\"amount\":\"" + this.amount + "\"" + ", " +
            "\"nbt\":" + (this.nbt == null ? "null" : this.nbt) + ", " +
            "\"enchantments\":" + (this.enchantments == null ? "null" : "\"" + this.enchantments + "\"") + ", " +
            "\"dirtyEnchantments\":\"" + this.dirtyEnchantments + "\"" + ", " +
            "\"itemStackPlace\":" + (this.itemStackPlace == null ? "null" : this.itemStackPlace) + ", " +
            "\"items\":" + (this.items == null ? "null" : this.items) + ", " +
            "\"blocks\":" + (this.blocks == null ? "null" : this.blocks) + ", " +
            "\"isDamageableCached\":\"" + this.isDamageableCached + "\"" + ", " +
            "\"isDamageable\":\"" + this.isDamageable + "\"" +
            "}";
    }

    public I items(Items items) {
        this.items = items;
        return (I) this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStack<?> itemStack = (ItemStack<?>) o;
        return this.material.equals(itemStack.material) &&
                this.data == itemStack.data &&
            Objects.equals(this.nbt, itemStack.nbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.material, this.data, this.nbt);
    }

    public boolean isAir() {
        return "minecraft:air".equals(this.material);
    }

    public I blocks(Blocks blocks) {
        this.blocks = blocks;
        return (I) this;
    }

    public ItemStackPlace getItemStackPlace() {
        return this.itemStackPlace;
    }

    public boolean isEnchanted() {
        if (this.nbt == null) {
            return false;
        }

        return this.nbt.getList("ench", false) != null;
    }

}
