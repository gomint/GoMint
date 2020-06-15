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
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
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
        buffer.writeByte( (byte) 1 ); // Unknown use
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
