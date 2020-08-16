/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.network.Protocol;

import java.util.Collection;
import java.util.Objects;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PacketCraftingRecipes extends Packet {

    private Collection<Recipe> recipes;

    /**
     * Construct new crafting recipe packet
     */
    public PacketCraftingRecipes() {
        super( Protocol.PACKET_CRAFTING_RECIPES );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarInt( this.recipes.size() );

        for ( Recipe recipe : this.recipes ) {
            recipe.serialize( buffer );
        }

        buffer.writeUnsignedVarInt(0); // Potions
        buffer.writeUnsignedVarInt(0);
        buffer.writeBoolean( true); // Clean client recipes
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public Collection<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacketCraftingRecipes that = (PacketCraftingRecipes) o;
        return Objects.equals(recipes, that.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipes);
    }
}
