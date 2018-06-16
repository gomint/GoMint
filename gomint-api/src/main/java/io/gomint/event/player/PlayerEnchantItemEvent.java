package io.gomint.event.player;

import io.gomint.enchant.Enchantment;
import io.gomint.entity.EntityPlayer;
import io.gomint.inventory.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerEnchantItemEvent extends CancellablePlayerEvent {

    private final ItemStack item;
    private final List<Enchantment> enchantments;
    private final int levelCost;
    private final int lapisCost;

    public PlayerEnchantItemEvent( EntityPlayer player, ItemStack item, List<Enchantment> enchantments, int levelCost, int lapisCost ) {
        super( player );
        this.item = item;
        this.enchantments = Collections.unmodifiableList( enchantments );
        this.levelCost = levelCost;
        this.lapisCost = lapisCost;
    }

    /**
     * Get the item which should be enchanted
     *
     * @return the item which should get enchanted
     */
    public ItemStack getItem() {
        return this.item;
    }

    /**
     * Get the list of enchants which should be applied
     *
     * @return the enchants which will be applied
     */
    public List<Enchantment> getEnchantments() {
        return this.enchantments;
    }

    /**
     * Get the lapis cost of this enchantment process
     *
     * @return the amount of lapis which will be removed from the enchantment table
     */
    public int getLapisCost() {
        return this.lapisCost;
    }

    /**
     * Get the level cost for this enchantment process
     *
     * @return the amount of levels this enchantment process costs
     */
    public int getLevelCost() {
        return this.levelCost;
    }

}
