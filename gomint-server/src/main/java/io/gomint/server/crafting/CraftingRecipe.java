/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.crafting;

import io.gomint.inventory.item.ItemStack;

import java.util.*;

/**
 * Interface which may be expanded in the future but is currently only used for
 * type hinting.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public abstract class CraftingRecipe extends Recipe {

    private final ItemStack[] outcome;

    CraftingRecipe( ItemStack[] outcome, UUID uuid, int priority ) {
        super( uuid, priority );
        this.outcome = outcome;
    }

    @Override
    public Collection<ItemStack> createResult() {
        if ( this.outcome.length == 1 ) {
            if ( this.outcome[0] == null ) {
                System.out.println("T");
            }

            return Collections.singletonList( ( (io.gomint.server.inventory.item.ItemStack) this.outcome[0] ).clone() );
        } else {
            List<ItemStack> list = new ArrayList<>();
            for ( ItemStack stack : this.outcome ) {
                list.add( ( (io.gomint.server.inventory.item.ItemStack) stack ).clone() );
            }

            return list;
        }
    }

    /**
     * Check if the two given items are equal enough to be used as crafting input
     *
     * @param recipeItem of the recipe
     * @param invItem of the inventory
     * @return
     */
    protected boolean canBeUsedForCrafting( ItemStack recipeItem, ItemStack invItem ) {
        io.gomint.server.inventory.item.ItemStack rI = (io.gomint.server.inventory.item.ItemStack) recipeItem;
        io.gomint.server.inventory.item.ItemStack iI = (io.gomint.server.inventory.item.ItemStack) invItem;

        String recipeMaterial = rI.getMaterial();
        String inputMaterial = iI.getMaterial();

        return recipeMaterial.equals(inputMaterial) &&
            ( rI.getData() == -1 || rI.getData() == iI.getData() );
    }

    public ItemStack[] getOutcome() {
        return outcome;
    }

}
