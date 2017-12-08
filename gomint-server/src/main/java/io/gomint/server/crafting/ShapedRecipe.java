/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.crafting;

import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.inventory.CraftingInputInventory;
import io.gomint.server.network.packet.Packet;
import javafx.scene.paint.Material;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Resembles a shaped crafting recipe, i.e. a recipe that requires its
 * arrangement to be arranged in specific way and does not accept work
 * if any ingredient is not in the right spot.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class ShapedRecipe extends CraftingRecipe {

    private final int width;
    private final int height;

    private final ItemStack[] arrangement;
    private final ItemStack[] outcome;

    private Collection<ItemStack> ingredients;

    /**
     * New shaped recipe
     *
     * @param width       The width of the recipe
     * @param height      The height of the recipe
     * @param ingredients Input of the recipe
     * @param outcome     Output of the recipe
     * @param uuid        UUID of the recipe
     */
    public ShapedRecipe( int width, int height, ItemStack[] ingredients, ItemStack[] outcome, UUID uuid ) {
        super( outcome, uuid );
        assert ingredients.length == width * height : "Invalid arrangement: Fill out empty slots with air!";

        this.width = width;
        this.height = height;
        this.arrangement = ingredients;
        this.outcome = outcome;
    }

    /**
     * Gets the width of this shaped recipe.
     *
     * @return The width of this shaped recipe
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Gets the height of this shaped recipe.
     *
     * @return The height of this shaped recipe
     */
    public int getHeight() {
        return this.height;
    }

    @Override
    public Collection<ItemStack> getIngredients() {
        if ( this.ingredients == null ) {
            // Got to sort out possible AIR slots and combine types:
            this.ingredients = new ArrayList<>();

            for ( int j = 0; j < this.height; ++j ) {
                for ( int i = 0; i < this.width; ++i ) {
                    ItemStack stack = this.arrangement[j * this.width + i];
                    if ( !(stack instanceof ItemAir) ) {
                        this.ingredients.add( stack );
                    }
                }
            }
        }

        return this.ingredients;
    }

    @Override
    public void serialize( PacketBuffer buffer ) {
        // Type of recipe ( 1 == shaped )
        buffer.writeSignedVarInt( 1 );

        // Size of grid
        buffer.writeSignedVarInt( this.width );
        buffer.writeSignedVarInt( this.height );

        // Input items
        for ( int j = 0; j < this.height; ++j ) {
            for ( int i = 0; i < this.width; ++i ) {
                Packet.writeItemStack( this.arrangement[j * this.width + i], buffer );
            }
        }

        // Amount of result
        buffer.writeUnsignedVarInt( this.outcome.length );

        for ( ItemStack itemStack : this.outcome ) {
            Packet.writeItemStack( itemStack, buffer );
        }

        // Write recipe UUID
        buffer.writeUUID( this.getUUID() );
    }

}
