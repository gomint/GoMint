/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.taglib.NBTTagCompound;

/**
 * Represents a stack of up to 255 items of the same type which may
 * optionally also have an additional data value. May be cloned.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class ItemStack implements Cloneable, io.gomint.inventory.item.ItemStack {

    private int material;
    private short data;
    private byte amount;
    private NBTTagCompound nbt;

    /**
     * Constructs a new item stack that will hold the given amount
     * of items of the specified type. Additionally specifies the
     * data value of the item.
     *
     * @param material The material of the item
     * @param data     The data value of the item
     * @param amount   The number of items on this stack (255 max)
     */
    public ItemStack( int material, short data, int amount ) {
        this.material = material;
        this.data = data;
        this.amount = (byte) ( amount > Byte.MAX_VALUE ? Byte.MAX_VALUE : amount );
    }

    /**
     * Constructs a new item stack that will hold the given amount
     * of items of the specified type. Additionally specifies the
     * data value of the item as well as raw NBT data that resembles
     * additional required storage such as a chest's inventory.
     *
     * @param material The material of the item
     * @param data     The data value of the item
     * @param amount   The number of items on this stack (255 max)
     * @param nbt      The additional raw NBT data of the item
     */
    public ItemStack( int material, short data, int amount, NBTTagCompound nbt ) {
        this( material, data, amount );
        this.nbt = nbt;
    }

    /**
     * Gets the material of the item(s) on this stack.
     *
     * @return The material of the item(s) on this stack
     */
    public int getMaterial() {
        return this.material;
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
    public void setData( short data ) {
        this.data = data;
    }

    /**
     * Get the maximum amount of items which can be stored in this stack
     *
     * @return maximum amount of items which can be stored in this stack
     */
    public byte getMaximumAmount() {
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
    public void setAmount( int amount ) {
        this.amount = amount > getMaximumAmount() ? getMaximumAmount() : (byte) amount;
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
     * Set new nbt data into the itemstack
     *
     * @param compound The raw NBT data of this item
     */
    public void setNbtData( NBTTagCompound compound ) {
        this.nbt = compound;
    }

    @Override
    public int hashCode() {
        int hash = 157;
        hash = 31 * hash + this.material;
        hash = 31 * hash + this.data;
        hash = 31 * hash + this.amount;
        return hash;
    }

    @Override
    public String toString() {
        return String.format( "[ItemStack %s:%d x %d]", this.material, this.data, this.amount );
    }

    @Override
    public ItemStack clone() {
        try {
            ItemStack clone = (ItemStack) super.clone();
            clone.material = this.material;
            clone.data = this.data;
            clone.amount = this.amount;
            clone.nbt = ( this.nbt == null ? null : this.nbt.deepClone() );
            return clone;
        } catch ( CloneNotSupportedException e ) {
            throw new AssertionError( "Clone of ItemStack failed", e );
        }
    }

    @Override
    public final boolean equals( Object other ) {
        if ( !( other instanceof ItemStack ) ) return false;
        ItemStack otherItemStack = (ItemStack) other;
        return this.getMaterial() == otherItemStack.getMaterial() &&
                this.getData() == otherItemStack.getData() &&
                ( this.nbt == otherItemStack.nbt || this.nbt.equals( otherItemStack.nbt ) );
    }

    /**
     * Return the block id from this item
     *
     * @return id for the block when this item is placed
     */
    public int getBlockId() {
        return this.material;
    }

}
