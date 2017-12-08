package io.gomint.crafting;

import io.gomint.inventory.item.ItemStack;

import java.util.Collection;
import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Recipe {

    /**
     * Gets the UUID of this recipe.
     *
     * @return The UUID of this recipe
     */
    UUID getUUID();

    /**
     * Returns a list of ingredients required by this recipe.
     *
     * @return The list of ingredients required by this recipe
     */
    Collection<ItemStack> getIngredients();

}
