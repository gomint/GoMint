package io.gomint.inventory.item;

import io.gomint.enchant.Enchantment;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemStack<I> {

    /**
     * Get the type of this item stack. This should only be used for fast lookup in switch tables. When you need
     * to check for a item interface (you want to use the API interface of a sign for example) you always need to
     * instanceof check for the interface.
     *
     * @return type of the item
     */
    ItemType itemType();

    /**
     * Get the amount of items in this stack
     *
     * @return amount of items in stack
     */
    byte amount();

    /**
     * Get maximum amount which should be possible to store in this stack
     *
     * @return maximum amount of items possible
     */
    byte maximumAmount();

    /**
     * Set the amount of items in this stack. This is silently capped to {@link #maximumAmount()}, also this
     * item stack returns air when amount is lower or equals 0
     *
     * @param amount of items which should be in this stack
     * @return the itemstack instance for further manipulation
     */
    I amount(int amount);

    /**
     * Set a custom name for this item stack
     *
     * @param name of this item stack
     * @return the itemstack instance for further manipulation
     */
    I customName(String name);

    /**
     * Get the custom name of this item
     *
     * @return custom name or null when there is none
     */
    String customName();

    /**
     * Set the lore of this item stack
     *
     * @param lore which should be used in this item stack
     * @return the itemstack instance for further manipulation
     */
    I lore(String... lore);

    /**
     * Get the lore of this item stack
     *
     * @return lore of this item stack or null when there is none
     */
    String[] lore();

    /**
     * Clone this item stack
     *
     * @return cloned item stack
     */
    ItemStack<I> clone();

    /**
     * Add enchantment based on class and level
     *
     * @param clazz of the enchantment
     * @param level of the enchantment, starting by one
     * @return the item stack instance for further manipulation
     */
    I enchant(Class<? extends Enchantment> clazz, int level);

    /**
     * Get the enchantment or null
     *
     * @param clazz of the enchantment
     * @param <T>   type of enchantment object
     * @return enchantment object or null
     */
    <T extends Enchantment> T enchantment(Class<? extends Enchantment> clazz);

    /**
     * Remove a enchantment from this item stack
     *
     * @param clazz of the enchantment
     * @return the itemstack instance for further manipulation
     */
    I removeEnchantment(Class<? extends Enchantment> clazz);

    /**
     * Get the damage done to an item. This will return 0 for all non damageable items
     *
     * @return 0 is not damaged, 1 is broken
     */
    float damage();

    /**
     * Set the damage done to an item. This is a no-op to non damageable items
     *
     * @param damage 0 is not damage, 1 is broken
     */
    I damage(float damage);

}
