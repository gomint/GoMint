/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.crafting;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.network.packet.PacketCraftingRecipes;

import java.util.*;

/**
 * Helper class used to manage all available crafting recipes.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class RecipeManager {

    private List<Recipe> recipes;

    // Lookup stuff
    private Map<ItemStack, SmeltingRecipe> smeltingRecipes;
    private Map<UUID, Recipe> lookup;

    private PacketCraftingRecipes batchPacket;
    private boolean dirty;

    /**
     * Constructs a new recipe manager.
     */
    public RecipeManager() {
        this.smeltingRecipes = new HashMap<>();
        this.recipes = new ArrayList<>();
        this.lookup = new HashMap<>();
        this.dirty = true;
    }

    /**
     * Gets a packet containing all crafting recipes that may be sent to players in
     * order to let them know what crafting recipes are supported by the server.
     *
     * @return The packet containing all crafting recipes
     */
    public PacketCraftingRecipes getCraftingRecipesBatch() {
        if ( this.dirty ) {
            PacketCraftingRecipes recipes = new PacketCraftingRecipes();
            recipes.setRecipes( this.recipes );
            recipes.cache();

            this.batchPacket = recipes;
            this.dirty = false;
        }

        return this.batchPacket;
    }

    /**
     * Registers the given crafting recipe thus making it available for crafting
     * from now on.
     *
     * @param recipe The recipe to register
     */
    public void registerRecipe( Recipe recipe ) {
        this.recipes.add( recipe );

        if ( recipe.getUUID() != null ) {
            this.lookup.put( recipe.getUUID(), recipe );
        }

        // Check if this is a smelting recipe
        if ( recipe instanceof SmeltingRecipe ) {
            SmeltingRecipe smeltingRecipe = (SmeltingRecipe) recipe;
            this.smeltingRecipes.put( smeltingRecipe.getIngredients()[0], smeltingRecipe );
        }

        this.dirty = true;
    }

    /**
     * Get the stored recipe by its id
     *
     * @param recipeId The id we should lookup
     * @return either null when no recipe was found or the recipe
     */
    public Recipe getRecipe( UUID recipeId ) {
        return this.lookup.get( recipeId );
    }

    public SmeltingRecipe getSmeltingRecipe( ItemStack input ) {
        return this.smeltingRecipes.get( input );
    }

    public Recipe getRecipe( int recipeId ) {
        return this.recipes.get( recipeId );
    }

}
