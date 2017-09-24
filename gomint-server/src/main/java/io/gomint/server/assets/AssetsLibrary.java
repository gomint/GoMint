/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.assets;

import io.gomint.inventory.item.ItemAir;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.crafting.ShapedRecipe;
import io.gomint.server.crafting.ShapelessRecipe;
import io.gomint.server.crafting.SmeltingRecipe;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.taglib.NBTTagCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A wrapper class around any suitable file format (currently NBT) that allows
 * for loading constant game-data into memory at runtime instead of hardcoding
 * it.
 *
 * @author BlackyPaw
 * @version 1.0
 */
public class AssetsLibrary {

    private static final Logger LOGGER = LoggerFactory.getLogger( AssetsLibrary.class );
    private Set<Recipe> recipes;

    // Statistics
    private int shapelessRecipes;
    private int shapedRecipes;
    private int smeltingRecipes;

    /**
     * Loads the assets library from the given file.
     *
     * @param input The input stream to read the asset library from
     * @throws IOException Thrown if an I/O error occurs whilst loading the library
     */
    @SuppressWarnings( "unchecked" )
    public void load( InputStream input ) throws IOException {
        NBTTagCompound root = NBTTagCompound.readFrom( input, false, ByteOrder.BIG_ENDIAN );
        this.loadRecipes( (List<NBTTagCompound>) ( (List) root.getList( "recipes", false ) ) );
    }

    /**
     * Gets the set of recipes stored inside the library.
     *
     * @return The set of recipes stored inside the library
     */
    public Collection<Recipe> getRecipes() {
        return this.recipes;
    }

    private void loadRecipes( List<NBTTagCompound> raw ) throws IOException {
        this.recipes = new HashSet<>();

        this.shapelessRecipes = 0;
        this.shapedRecipes = 0;
        this.smeltingRecipes = 0;

        if ( raw == null ) {
            return;
        }

        for ( NBTTagCompound compound : raw ) {
            byte type = compound.getByte( "type", (byte) -1 );
            Recipe recipe;
            switch ( type ) {
                case 0:
                    recipe = this.loadShapelessRecipe( compound );
                    break;

                case 1:
                    recipe = this.loadShapedRecipe( compound );
                    break;

                case 2:
                    recipe = this.loadSmeltingRecipe( compound );
                    break;

                default:
                    continue;
            }

            this.recipes.add( recipe );
        }

        LOGGER.info( String.format( "Loaded %d shapeless, %d shaped and %d smelting recipes", this.shapelessRecipes, this.shapedRecipes, this.smeltingRecipes ) );
    }

    private ShapelessRecipe loadShapelessRecipe( NBTTagCompound data ) throws IOException {
        int count = data.getInteger( "c", 0 );

        byte[] ingredientData = data.getByteArray( "i", new byte[0] );
        PacketBuffer buffer = new PacketBuffer( ingredientData, 0 );

        ItemStack[] ingredients = new ItemStack[count];
        for ( int i = 0; i < count; ++i ) {
            ingredients[i] = this.loadItemStack( buffer );
        }

        count = data.getInteger( "r", 0 );

        byte[] outcomeData = data.getByteArray( "o", new byte[0] );
        buffer = new PacketBuffer( outcomeData, 0 );

        ItemStack[] outcome = new ItemStack[count];
        for ( int i = 0; i < count; ++i ) {
            outcome[i] = this.loadItemStack( buffer );
        }

        this.shapelessRecipes++;
        return new ShapelessRecipe( ingredients, outcome, null );
    }

    private ShapedRecipe loadShapedRecipe( NBTTagCompound data ) throws IOException {
        int width = data.getInteger( "w", 0 );
        int height = data.getInteger( "h", 0 );

        byte[] arrangementData = data.getByteArray( "a", new byte[0] );
        PacketBuffer buffer = new PacketBuffer( arrangementData, 0 );

        ItemStack[] arrangement = new ItemStack[width * height];
        for ( int j = 0; j < height; ++j ) {
            for ( int i = 0; i < width; ++i ) {
                arrangement[j * width + i] = this.loadItemStack( buffer );
            }
        }

        int count = data.getInteger( "r", 0 );

        byte[] outcomeData = data.getByteArray( "o", new byte[0] );
        buffer = new PacketBuffer( outcomeData, 0 );

        ItemStack[] outcome = new ItemStack[count];
        for ( int i = 0; i < count; ++i ) {
            outcome[i] = this.loadItemStack( buffer );
        }

        this.shapedRecipes++;
        return new ShapedRecipe( width, height, arrangement, outcome, null );
    }

    private SmeltingRecipe loadSmeltingRecipe( NBTTagCompound data ) throws IOException {
        byte[] inputData = data.getByteArray( "i", new byte[0] );
        ItemStack input = this.loadItemStack( new PacketBuffer( inputData, 0 ) );

        byte[] outcomeData = data.getByteArray( "o", new byte[0] );
        ItemStack outcome = this.loadItemStack( new PacketBuffer( outcomeData, 0 ) );

        this.smeltingRecipes++;
        return new SmeltingRecipe( input, outcome, null );
    }

    private ItemStack loadItemStack( PacketBuffer buffer ) throws IOException {
        short id = buffer.readShort();
        if ( id == 0 ) {
            return (ItemStack) ItemAir.create( 0 );
        }

        byte amount = buffer.readByte();
        short data = buffer.readShort();
        short extraLen = buffer.readShort();

        NBTTagCompound compound = null;
        if ( extraLen > 0 ) {
            ByteArrayInputStream bin = new ByteArrayInputStream( buffer.getBuffer(), buffer.getPosition(), extraLen );
            compound = NBTTagCompound.readFrom( bin, false, ByteOrder.BIG_ENDIAN );
            bin.close();
        }

        return Items.create( id, data, amount, compound );
    }

}