package io.gomint.inventory.item;

import io.gomint.enchant.Enchantment;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemStack {

    /**
     * Get the type of this item stack. This should only be used for fast lookup in switch tables. When you need
     * to check for a item interface (you want to use the API interface of a sign for example) you always need to
     * instanceof check for the interface.
     *
     * @return type of the item
     */
    ItemType getType();

    /**
     * Get the amount of items in this stack
     *
     * @return amount of items in stack
     */
    byte getAmount();

    /**
     * Get maximum amount which should be possible to store in this stack
     *
     * @return maximum amount of items possible
     */
    byte getMaximumAmount();

    /**
     * Set the amount of items in this stack. This is silently capped to {@link #getMaximumAmount()}
     *
     * @param amount of items which should be in this stack
     */
    void setAmount( int amount );

    /**
     * Get the metadata from this item stack
     *
     * @return metadata from this item stack
     */
    short getData();

    /**
     * Set the data of this item stack
     *
     * @param data to set into this stack
     */
    void setData( short data );

    /**
     * Get the NBT data from this item stack
     *
     * @return nbt data from this item stack
     */
    NBTTagCompound getNbtData();

    /**
     * Set a custom name for this item stack
     *
     * @param name of this item stack
     */
    void setCustomName( String name );

    /**
     * Get the custom name of this item
     *
     * @return custom name or null when there is none
     */
    String getCustomName();

    /**
     * Set the lore of this item stack
     *
     * @param lore which should be used in this item stack
     */
    void setLore( String ... lore );

    /**
     * Get the lore of this item stack
     *
     * @return lore of this item stack or null when there is none
     */
    String[] getLore();

    /**
     * Clone this item stack
     *
     * @return cloned item stack
     */
    ItemStack clone();

    /**
     * Add enchantment based on class and level
     *
     * @param clazz of the enchantment
     * @param level of the enchantment
     */
    void addEnchantment( Class<? extends Enchantment> clazz, short level );

    /**
     * Get the enchantment or null
     *
     * @param clazz of the enchantment
     * @param <T> type of enchantment object
     * @return enchantment object or null
     */
    <T extends Enchantment> T getEnchantment( Class<? extends Enchantment> clazz );

    /**
     * Remove a enchantment from this item stack
     *
     * @param clazz of the enchantment
     */
    void removeEnchantment( Class<? extends Enchantment> clazz );

}
