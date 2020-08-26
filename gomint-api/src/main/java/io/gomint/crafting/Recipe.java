package io.gomint.crafting;

import io.gomint.inventory.item.ItemStack;

import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface Recipe {

    /**
     * Gets the UUID of this recipe.
     *
     * @return The UUID of this recipe
     */
    UUID getUUID();

    /**
     * Returns a array of ingredients required by this recipe.
     *
     * @return The array of ingredients required by this recipe
     */
    ItemStack[] getIngredients();

}
