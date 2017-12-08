/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.inventory.item;

import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.world.block.Block;
import io.gomint.taglib.NBTTagCompound;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a stack of up to 255 items of the same type which may
 * optionally also have an additional data value. May be cloned.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public abstract class ItemStack implements Cloneable, io.gomint.inventory.item.ItemStack {

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
        this.amount = (byte) ( amount > getMaximumAmount() ? getMaximumAmount() : amount );
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
     * Get the maximum amount of damage before this item breaks
     *
     * @return maximum amount of damage
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
    public void setCustomName( String name ) {
        // Check if we should clear the name
        if ( name == null ) {
            if ( this.nbt != null ) {
                NBTTagCompound display = this.nbt.getCompound( "display", false );
                if ( display != null ) {
                    display.remove( "Name" );

                    // Delete the display NBT when no data is in it
                    if ( display.size() == 0 ) {
                        this.nbt.remove( "display" );

                        // Delete the tag when no data is in it
                        if ( this.nbt.size() == 0 ) {
                            this.nbt = null;
                        }
                    }
                }
            }

            return;
        }

        // Do we have a compound tag?
        if ( this.nbt == null ) {
            this.nbt = new NBTTagCompound( "" );
        }

        // Get the display tag
        NBTTagCompound display = this.nbt.getCompound( "display", true );
        display.addValue( "Name", name );
    }

    @Override
    public String getCustomName() {
        // Check if we have a NBT tag
        if ( this.nbt == null ) {
            return null;
        }

        // Get display part
        NBTTagCompound display = this.nbt.getCompound( "display", false );
        if ( display == null ) {
            return null;
        }

        return display.getString( "Name", null );
    }

    @Override
    public void setLore( String... lore ) {
        // Check if we should clear the name
        if ( lore == null ) {
            if ( this.nbt != null ) {
                NBTTagCompound display = this.nbt.getCompound( "display", false );
                if ( display != null ) {
                    display.remove( "Lore" );

                    // Delete the display NBT when no data is in it
                    if ( display.size() == 0 ) {
                        this.nbt.remove( "display" );

                        // Delete the tag when no data is in it
                        if ( this.nbt.size() == 0 ) {
                            this.nbt = null;
                        }
                    }
                }
            }

            return;
        }

        // Do we have a compound tag?
        if ( this.nbt == null ) {
            this.nbt = new NBTTagCompound( "" );
        }

        // Get the display tag
        NBTTagCompound display = this.nbt.getCompound( "display", true );
        List<String> loreList = Arrays.asList( lore );
        display.addValue( "Lore", loreList );
    }

    @Override
    public String[] getLore() {
        // Check if we have a NBT tag
        if ( this.nbt == null ) {
            return null;
        }

        // Get display part
        NBTTagCompound display = this.nbt.getCompound( "display", false );
        if ( display == null ) {
            return null;
        }

        List<Object> loreList = display.getList( "Lore", false );
        if ( loreList == null ) {
            return null;
        }

        String[] loreCopy = new String[loreList.size()];
        for ( int i = 0; i < loreList.size(); i++ ) {
            loreCopy[i] = (String) loreList.get( i );
        }

        return loreCopy;
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
            clone.nbt = ( this.nbt == null ? null : this.nbt.deepClone( "" ) );
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

    /**
     * This gets called when a item was placed down as a block
     */
    public boolean afterPlacement() {
        // In a normal case the amount decreases
        return --this.amount == 0;
    }

    public boolean useDamageAsData() {
        return true;
    }

    public boolean usesDamage() {
        return false;
    }

    public void removeFromHand( EntityPlayer player ) {
        // Normal items do nothing
    }

    public void gotInHand( EntityPlayer player ) {
        // Normal items do nothing
    }

    public boolean interact( EntityPlayer entity, int face, Vector clickPosition, Block clickedBlock ) {
        return false;
    }

    public boolean damage( int damage ) {
        // Do we use damage as data values?
        if ( useDamageAsData() ) {
            return false;
        }

        // Default no item uses damage
        if ( !usesDamage() ) {
            return false;
        }

        // Check if we need to destroy this item stack
        this.data += damage;
        if ( this.data > this.getMaxDamage() ) {
            // Remove one amount
            if ( --this.amount == 0 ) {
                return true;
            }

            this.data = 0;
        }

        return false;
    }

}
