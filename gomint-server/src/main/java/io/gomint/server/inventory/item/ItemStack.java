/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
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
import io.gomint.server.inventory.item.annotation.UseDataAsDamage;
import io.gomint.server.inventory.item.helper.ItemStackPlace;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.block.Block;
import io.gomint.world.block.data.Facing;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Represents a stack of up to 255 items of the same type which may
 * optionally also have an additional data value. May be cloned.
 *
 * @author BlackyPaw
 * @version 1.0
 */
@ToString
@EqualsAndHashCode( of = { "material", "data", "nbt" } )
public abstract class ItemStack implements Cloneable, io.gomint.inventory.item.ItemStack {

    private static final Set<Integer> USES_DATA_AS_DAMAGE = new HashSet<>();
    private static final Set<Integer> IS_CHECKED_FOR_USES_DATA_AS_DAMAGE = new HashSet<>();

    private static final Logger LOGGER = LoggerFactory.getLogger( ItemStack.class );

    private int material;
    private short data;
    private byte amount;
    private NBTTagCompound nbt;

    // Cached enchantments
    private Map<Class, Enchantment> enchantments;
    private boolean dirtyEnchantments;

    // Observer stuff for damaging items
    private ItemStackPlace itemStackPlace;

    // Item constructor factors
    @Setter
    private Items items;

    ItemStack setMaterial( int material ) {
        this.material = material;
        return this;
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
    public io.gomint.inventory.item.ItemStack setData( short data ) {
        this.data = data;
        return this;
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
    public io.gomint.inventory.item.ItemStack setAmount( int amount ) {
        this.amount = amount > getMaximumAmount() ? getMaximumAmount() : (byte) amount;
        return this.updateInventories( this.amount <= 0 );
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
    ItemStack setNbtData( NBTTagCompound compound ) {
        this.nbt = compound;
        return this;
    }

    @Override
    public io.gomint.inventory.item.ItemStack setCustomName( String name ) {
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

            return this;
        }

        // Do we have a compound tag?
        if ( this.nbt == null ) {
            this.nbt = new NBTTagCompound( "" );
        }

        // Get the display tag
        NBTTagCompound display = this.nbt.getCompound( "display", true );
        display.addValue( "Name", name );

        return this;
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
    public io.gomint.inventory.item.ItemStack setLore( String... lore ) {
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

            return this;
        }

        // Do we have a compound tag?
        if ( this.nbt == null ) {
            this.nbt = new NBTTagCompound( "" );
        }

        // Get the display tag
        NBTTagCompound display = this.nbt.getCompound( "display", true );
        List<String> loreList = Arrays.asList( lore );
        display.addValue( "Lore", loreList );

        return this;
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
    public io.gomint.inventory.item.ItemStack addEnchantment( Class<? extends Enchantment> clazz, short level ) {
        short id = ( (GoMintServer) GoMint.instance() ).getEnchantments().getId( clazz );
        if ( id == -1 ) {
            LOGGER.warn( "Unknown enchantment:{}", clazz.getName() );
            return this;
        }

        if ( this.nbt == null ) {
            this.nbt = new NBTTagCompound( "" );
        }

        List<Object> enchantmentList = this.nbt.getList( "ench", true );

        NBTTagCompound enchCompound = new NBTTagCompound( null );
        enchCompound.addValue( "id", id );
        enchCompound.addValue( "lvl", level );
        enchantmentList.add( enchCompound );

        this.dirtyEnchantments = true;
        return this;
    }

    @Override
    public <T extends Enchantment> T getEnchantment( Class<? extends Enchantment> clazz ) {
        if ( this.dirtyEnchantments ) {
            this.dirtyEnchantments = false;

            if ( this.nbt == null ) {
                return null;
            }

            List<Object> nbtEnchCompounds = this.nbt.getList( "ench", false );
            if ( nbtEnchCompounds == null ) {
                return null;
            }

            this.enchantments = new HashMap<>();
            for ( Object compound : nbtEnchCompounds ) {
                NBTTagCompound enchantCompound = (NBTTagCompound) compound;
                io.gomint.server.enchant.Enchantment enchantment = ( (GoMintServer) GoMint.instance() ).getEnchantments().create(
                    enchantCompound.getShort( "id", (short) 0 ),
                    enchantCompound.getShort( "lvl", (short) 0 )
                );

                this.enchantments.put( enchantment.getClass().getInterfaces()[0], enchantment );
            }
        }

        return this.enchantments == null ? null : (T) this.enchantments.get( clazz );
    }

    @Override
    public io.gomint.inventory.item.ItemStack removeEnchantment( Class<? extends Enchantment> clazz ) {
        short id = ( (GoMintServer) GoMint.instance() ).getEnchantments().getId( clazz );
        if ( id == -1 ) {
            return this;
        }

        if ( this.nbt == null ) {
            return this;
        }

        List<Object> enchantmentList = this.nbt.getList( "ench", false );
        if ( enchantmentList == null ) {
            return this;
        }

        for ( Object nbtObject : new ArrayList<>( enchantmentList ) ) {
            NBTTagCompound enchCompound = (NBTTagCompound) nbtObject;
            if ( enchCompound.getShort( "id", (short) -1 ) == id ) {
                enchantmentList.remove( enchCompound );
                this.dirtyEnchantments = true;
                break;
            }
        }

        if ( enchantmentList.isEmpty() ) {
            this.nbt.remove( "ench" );
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
            clone.nbt = ( this.nbt == null ? null : this.nbt.deepClone( "" ) );
            clone.itemStackPlace = null;
            return clone;
        } catch ( CloneNotSupportedException e ) {
            throw new AssertionError( "Clone of ItemStack failed", e );
        }
    }

    /**
     * Return the block id from this item
     *
     * @return id for the block when this item is placed
     */
    public String getBlockId() {
        return this.items.getBlockId(this.material);
    }

    /**
     * This gets called when a item was placed down as a block. The amount gets decreased and the inventories this item is
     * in get updated (if <= 0 to air, otherwise the amount gets updated)
     */
    public void afterPlacement() {
        // In a normal case the amount decreases
        this.updateInventories( --this.amount <= 0 );
    }

    /**
     * Check if we need to update this item in its inventories
     *
     * @param replaceWithAir if the item should be deleted (replaced with air)
     * @return the item instance used or the air instance which has been set
     */
    io.gomint.inventory.item.ItemStack updateInventories( boolean replaceWithAir ) {
        if ( replaceWithAir ) {
            io.gomint.inventory.item.ItemStack itemStack = ItemAir.create( 0 );

            if ( this.itemStackPlace != null ) {
                this.itemStackPlace.getInventory().setItem( this.itemStackPlace.getSlot(), itemStack );
            }

            return itemStack;
        } else {
            if ( this.itemStackPlace != null ) {
                this.itemStackPlace.getInventory().setItem( this.itemStackPlace.getSlot(), this );
            }
        }

        return this;
    }

    public void removeFromHand( EntityPlayer player ) {
        // NormalGenerator items do nothing
    }

    public void gotInHand( EntityPlayer player ) {
        // NormalGenerator items do nothing
    }

    public boolean interact(EntityPlayer entity, Facing face, Vector clickPosition, Block clickedBlock ) {
        return false;
    }

    /**
     * Same rules as in {@link #calculateUsage(int)} will be applied. If the result of {@link #calculateUsage(int)} is
     * true all inventories in which this item is will be notified to set air for this item, if its false the
     * updated itemstack gets set
     *
     * @param damage which should be applied
     */
    public void calculateUsageAndUpdate( int damage ) {
        this.updateInventories( this.calculateUsage( damage ) );
    }

    /**
     * Calculate the damage done to this stack when using it. When a item has damage calculation enabled with
     * {@link UseDataAsDamage} annotated, the parameter damage is added to the current data and checked against
     * {@link ItemStack#getMaxDamage()}. If the damage is high enough the amount will be decreased by one.
     * <p>
     * If the item isn't annotated the damage parameter is ignored and a amount of this stack is removed
     *
     * @param damage which should be applied
     * @return true when the stack is empty, false if the stack has still usages in it
     */
    public boolean calculateUsage( int damage ) {
        // Default no item uses calculateUsage
        if ( useDataAsDamage() ) {
            // Check if we need to destroy this item stack
            this.data += damage;
            if ( this.data >= this.getMaxDamage() ) {
                // Remove one amount
                if ( --this.amount <= 0 ) {
                    return true;
                }

                this.data = 0;
            }

            return false;
        }

        return false;
    }

    private boolean useDataAsDamage() {
        if ( !IS_CHECKED_FOR_USES_DATA_AS_DAMAGE.contains( this.material ) ) {
            Class current = this.getClass();
            boolean usesData;

            do {
                usesData = current.isAnnotationPresent( UseDataAsDamage.class );
                current = current.getSuperclass();
            } while ( !usesData && !Object.class.equals( current ) );

            if ( usesData ) {
                USES_DATA_AS_DAMAGE.add( this.material );
            }

            IS_CHECKED_FOR_USES_DATA_AS_DAMAGE.add( this.material );
            return usesData;
        }

        return USES_DATA_AS_DAMAGE.contains( this.material );
    }

    /**
     * Get the enchant ability of this item
     *
     * @return enchantment possibility
     */
    public int getEnchantAbility() {
        return 0;
    }

    void enforceNBTData() {
        if ( this.nbt == null ) {
            this.nbt = new NBTTagCompound( "" );
        }
    }

    public void addPlace( Inventory inventory, int slot ) {
        if ( this.itemStackPlace != null ) {
            LOGGER.warn( "Did not remove the previous itemStackPlace", new Exception() );
        }

        this.itemStackPlace = new ItemStackPlace( slot, inventory );
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

}
