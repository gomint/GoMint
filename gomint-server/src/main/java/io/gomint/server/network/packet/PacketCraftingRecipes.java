/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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
    private PacketBuffer cache = null;

    /**
     * Construct new crafting recipe packet
     */
    public PacketCraftingRecipes() {
        super( Protocol.PACKET_CRAFTING_RECIPES );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        if (this.cache == null) {
            this.cache();
        }

        buffer.writeBytes(this.cache.getBuffer().asReadOnly());
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public Collection<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Collection<Recipe> recipes) {
        this.recipes = recipes;

        if (this.cache != null) {
            this.cache.release();
            this.cache = null;
        }
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

    public void cache() {
        this.cache = new PacketBuffer(1024);
        this.cache.writeUnsignedVarInt( this.recipes.size() );

        for ( Recipe recipe : this.recipes ) {
            recipe.serialize( this.cache );
        }

        this.cache.writeUnsignedVarInt(0); // Potions
        this.cache.writeUnsignedVarInt(0);
        this.cache.writeBoolean( true); // Clean client recipes
    }

}
