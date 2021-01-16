package io.gomint.event.enchant;

import com.google.common.base.Preconditions;
import io.gomint.enchant.Enchantment;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.CancellablePlayerEvent;
import io.gomint.inventory.item.ItemStack;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 1
 *
 * This event gets fired when the player has selected the enchantments it wants on given item.
 */
public class ItemEnchantEvent extends CancellablePlayerEvent<ItemEnchantEvent> {

    private final ItemStack<?> itemToEnchant;
    private final List<Enchantment> enchantments;
    private int levelCost;
    private final int materialCost;
    private int levelRequirement;

    public ItemEnchantEvent(EntityPlayer player,
                            ItemStack<?> itemToEnchant,
                            int levelCost,
                            int materialCost,
                            List<Enchantment> enchantments,
                            int levelRequirement) {
        super(player);
        this.itemToEnchant = itemToEnchant;
        this.enchantments = enchantments;
        this.levelCost = levelCost;
        this.materialCost = materialCost;
    }

    /**
     * Get the amount of levels this enchantment operation should cost
     *
     * @return amount of levels this enchantment operation costs
     */
    public int levelCost() {
        return levelCost;
    }

    /**
     * Set the amount of levels this enchantment operation should cost
     *
     * @param levelCost for this enchantment operation. This can only be positive, including 0
     */
    public ItemEnchantEvent levelCost(int levelCost) {
        Preconditions.checkArgument(levelCost > 0, "Only positive level costs, including 0, are allowed");
        this.levelCost = levelCost;
        return this;
    }

    /**
     * Get the amount of material this enchantment operation should cost
     *
     * @return amount of material this enchantment operation costs
     */
    public int materialCost() {
        return materialCost;
    }

    /**
     * Get the item which should be enchanted
     *
     * @return item stack which should be enchanted
     */
    public ItemStack<?> itemToEnchant() {
        return itemToEnchant;
    }

    /**
     * Get the list of enchants which should be applied. You can modify those, even clear them. When you clear
     * the enchantments though the levels and materials are still subtracted and checked for.
     *
     * @return list of enchantments
     */
    public List<Enchantment> enchantments() {
        return enchantments;
    }

    /**
     * Get the amount of levels the player needs to unlock this enchantment
     *
     * @return minimum level the player needs
     */
    public int levelRequirement() {
        return levelRequirement;
    }

    /**
     * Set the minimum required levels the player needs to unlock this enchantment
     *
     * @param levelRequirement which the player needs
     */
    public ItemEnchantEvent levelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
        return this;
    }

}
