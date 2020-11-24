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
    private RecipeReverseLookup[] outputLookup;

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

    private void sortOutput( List<ItemStack> sortedOutput ) {
        sortedOutput.sort((o1, o2) -> {
            io.gomint.server.inventory.item.ItemStack impl1 = ( (io.gomint.server.inventory.item.ItemStack) o1 );
            io.gomint.server.inventory.item.ItemStack impl2 = ( (io.gomint.server.inventory.item.ItemStack) o2 );

            String mat1 = impl1.getMaterial();
            String mat2 = impl2.getMaterial();

            if ( mat1.equals(mat2) ) {
                return Short.compare( impl1.getData(), impl2.getData() );
            }

            return mat1.compareTo(mat2);
        });
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

    /**
     * Lookup a recipe by its output
     *
     * @param output The output collection we want to lookup
     * @return the recipe found or null
     */
    public Recipe getRecipe( Collection<ItemStack> output ) {
        List<ItemStack> sortedOutput = new ArrayList<>( output );
        sortOutput( sortedOutput );

        recipeLoop:
        for ( RecipeReverseLookup lookup : this.outputLookup ) {
            // Fast forward non matching in size
            if ( lookup.output.size() != sortedOutput.size() ) {
                continue;
            }

            // Check each item going forward
            for ( int i = 0; i < lookup.output.size(); i++ ) {
                ItemStack itemStack = lookup.output.get( i );
                if ( !Objects.equals( itemStack, sortedOutput.get( i ) ) ) {
                    continue recipeLoop;
                }
            }

            return lookup.recipe;
        }

        return null;
    }

    public void fixMCPEBugs() {
        this.outputLookup = new RecipeReverseLookup[this.recipes.size()];
        int index = 0;

        for ( Recipe recipe : this.recipes ) {
            // TODO: Due to a MC:PE Bug there is chance the wrong recipe UUID has been sent. To get rid of it we need to do a expensive output search
            List<ItemStack> sortedOutput = new ArrayList<>( recipe.createResult() );
            sortOutput( sortedOutput );
            this.outputLookup[index++] = new RecipeReverseLookup(recipe, sortedOutput);
        }
    }

    public SmeltingRecipe getSmeltingRecipe( ItemStack input ) {
        return this.smeltingRecipes.get( input );
    }

    private static class RecipeReverseLookup {
        private final Recipe recipe;
        private final List<ItemStack> output;

        public RecipeReverseLookup(Recipe recipe, List<ItemStack> output) {
            this.recipe = recipe;
            this.output = output;
        }
    }

}
