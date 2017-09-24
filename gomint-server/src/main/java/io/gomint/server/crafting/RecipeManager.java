/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.crafting;

import io.gomint.server.GoMintServer;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.network.packet.PacketBatch;
import io.gomint.server.network.packet.PacketCraftingRecipes;
import io.gomint.server.util.BatchUtil;

import java.util.*;

/**
 * Helper class used to manage all available crafting recipes.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class RecipeManager {

    private final GoMintServer server;
    private Set<Recipe> recipes;

    // Lookup stuff
    private Map<UUID, Recipe> lookup;
    private Map<List<ItemStack>, Recipe> outputLookup;

    private PacketBatch batchPacket;
    private boolean dirty;

    /**
     * Constructs a new recipe manager.
     *
     * @param server The GoMint server instance the recipe manager belongs to
     */
    public RecipeManager( GoMintServer server ) {
        this.server = server;
        this.recipes = new HashSet<>();
        this.lookup = new HashMap<>();
        this.outputLookup = new HashMap<>();
        this.dirty = true;
    }

    /**
     * Gets a packet containing all crafting recipes that may be sent to players in
     * order to let them know what crafting recipes are supported by the server.
     *
     * @return The packet containing all crafting recipes
     */
    public PacketBatch getCraftingRecipesBatch() {
        if ( this.dirty ) {
            PacketCraftingRecipes recipes = new PacketCraftingRecipes();
            recipes.setRecipes( this.recipes );

            this.batchPacket = BatchUtil.batch( null, recipes );
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

        // TODO: Due to a MC:PE Bug there is chance the wrong recipe UUID has been sent. To get rid of it we need to do a expensive output search
        List<ItemStack> sortedOutput = new ArrayList<>( recipe.createResult() );
        sortOutput( sortedOutput );
        this.outputLookup.put( sortedOutput, recipe );

        this.dirty = true;
    }

    private void sortOutput( List<ItemStack> sortedOutput ) {
        sortedOutput.sort( new Comparator<ItemStack>() {
            @Override
            public int compare( ItemStack o1, ItemStack o2 ) {
                int mat1 = ((io.gomint.server.inventory.item.ItemStack) o1).getMaterial();
                int mat2 = ((io.gomint.server.inventory.item.ItemStack) o2).getMaterial();

                if ( mat1 == mat2 ) {
                    return Short.compare( o1.getData(), o2.getData() );
                }

                return Integer.compare( mat1, mat2 );
            }
        } );
    }

    /**
     * Get the stored recipe by its id
     *
     * @param recipeId  The id we should lookup
     * @return either null when no recipe was found or the recipe
     */
    public Recipe getRecipe( UUID recipeId ) {
        return this.lookup.get( recipeId );
    }

    /**
     * Lookup a recipe by its output
     *
     * @param output    The output collection we want to lookup
     * @return the recipe found or null
     */
    public Recipe getRecipe( Collection<ItemStack> output ) {
        List<ItemStack> sortedOutput = new ArrayList<>( output );
        sortOutput( sortedOutput );
        return this.outputLookup.get( sortedOutput );
    }

}
