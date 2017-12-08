/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.crafting.ShapedRecipe;
import io.gomint.server.crafting.ShapelessRecipe;
import io.gomint.server.crafting.SmeltingRecipe;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.Protocol;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

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
    public void serialize( PacketBuffer buffer ) {
        buffer.writeUnsignedVarInt( this.recipes.size() );

        for ( Recipe recipe : this.recipes ) {
            recipe.serialize( buffer );
        }

        buffer.writeByte( (byte) 1 ); // Unknown use
    }

    @Override
    public void deserialize( PacketBuffer buffer ) {
        this.recipes = new ArrayList<>();

        int count = buffer.readUnsignedVarInt();
        for ( int i = 0; i < count; i++ ) {
            int recipeType = buffer.readSignedVarInt();
            if ( recipeType < 0 ) {
                continue;
            }

            switch ( recipeType ) {
                case 0:
                    // Shapeless

                    // Read input side
                    int inputCount = buffer.readUnsignedVarInt();
                    ItemStack[] input = new ItemStack[inputCount];
                    for ( int i1 = 0; i1 < inputCount; i1++ ) {
                        input[i1] = Packet.readItemStack( buffer );
                    }

                    // Read output side
                    int outputCount = buffer.readUnsignedVarInt();
                    ItemStack[] output = new ItemStack[outputCount];
                    for ( int i1 = 0; i1 < outputCount; i1++ ) {
                        output[i1] = Packet.readItemStack( buffer );
                    }

                    // Read uuid
                    UUID uuid = buffer.readUUID();
                    this.recipes.add( new ShapelessRecipe( input, output, uuid ) );
                    break;
                case 1:
                    // Shaped

                    // Read size of shape
                    int width = buffer.readSignedVarInt();
                    int height = buffer.readSignedVarInt();

                    // Read input side
                    input = new ItemStack[width * height];
                    for ( int w = 0; w < width; w++ ) {
                        for ( int h = 0; h < height; h++ ) {
                            input[h * width + w] = Packet.readItemStack( buffer );
                        }
                    }

                    // Read output side
                    outputCount = buffer.readUnsignedVarInt();
                    output = new ItemStack[outputCount];
                    for ( int i1 = 0; i1 < outputCount; i1++ ) {
                        output[i1] = Packet.readItemStack( buffer );
                    }

                    // Read uuid
                    uuid = buffer.readUUID();
                    this.recipes.add( new ShapedRecipe( width, height, input, output, uuid ) );
                    break;

                case 3:
                    // Smelting with metadata

                    int id = buffer.readSignedVarInt();
                    short data = (short) buffer.readSignedVarInt();
                    ItemStack result = Packet.readItemStack( buffer );

                    this.recipes.add( new SmeltingRecipe( Items.create( id, data, (byte) -1, null ), result, null ) );
                    break;

                case 2:
                    // Smelting without metadata

                    id = buffer.readSignedVarInt();
                    result = Packet.readItemStack( buffer );

                    this.recipes.add( new SmeltingRecipe( Items.create( id, (short) 0, (byte) -1, null ), result, null ) );
                    break;
            }
        }

        for ( Recipe recipe : this.recipes ) {
            System.out.println( "Input" );
            for ( ItemStack itemStack : recipe.getIngredients() ) {
                System.out.println( itemStack );
            }

            System.out.println( "Output" );
            for ( ItemStack itemStack : recipe.createResult() ) {
                System.out.println( itemStack );
            }
        }
    }

}
