/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.assets;

import io.gomint.inventory.item.ItemAir;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.GoMintServer;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.crafting.ShapedRecipe;
import io.gomint.server.crafting.ShapelessRecipe;
import io.gomint.server.crafting.SmeltingRecipe;
import io.gomint.server.inventory.CreativeInventory;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.taglib.NBTTagCompound;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    @Getter private CreativeInventory creativeInventory;
    @Getter private Set<Recipe> recipes;

    // Statistics
    private int shapelessRecipes;
    private int shapedRecipes;
    private int smeltingRecipes;

    private final GoMintServer server;

    /**
     * Create new asset library
     *
     * @param server which has been started
     */
    public AssetsLibrary( GoMintServer server ) {
        this.server = server;
    }

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
        this.loadCreativeInventory( (List<byte[]>) ( (List) root.getList( "creativeInventory", false ) ) );
    }

    private void loadCreativeInventory( List<byte[]> raw ) {
        this.creativeInventory = new CreativeInventory( null, raw.size() );

        for ( byte[] bytes : raw ) {
            try {
                this.creativeInventory.addItem( this.loadItemStack( new PacketBuffer( bytes, 0 ) ) );
            } catch ( IOException e ) {
                LOGGER.error( "Could not load creative item: ", e );
            }
        }

        LOGGER.info( "Loaded {} items into creative inventory", raw.size() );
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

        LOGGER.info( "Loaded {} shapeless, {} shaped and {} smelting recipes", this.shapelessRecipes, this.shapedRecipes, this.smeltingRecipes );
    }

    private ShapelessRecipe loadShapelessRecipe( NBTTagCompound data ) throws IOException {
        List<Object> inputItems = data.getList( "i", false );
        ItemStack[] ingredients = new ItemStack[inputItems.size()];
        for ( int i = 0; i < ingredients.length; ++i ) {
            ingredients[i] = this.loadItemStack( new PacketBuffer( (byte[]) inputItems.get( i ), 0 ) );
        }

        List<Object> outputItems = data.getList( "o", false );
        ItemStack[] outcome = new ItemStack[outputItems.size()];
        for ( int i = 0; i < outcome.length; ++i ) {
            outcome[i] = this.loadItemStack( new PacketBuffer( (byte[]) outputItems.get( i ), 0 ) );
        }

        this.shapelessRecipes++;
        return new ShapelessRecipe( ingredients, outcome, null );
    }

    private ShapedRecipe loadShapedRecipe( NBTTagCompound data ) throws IOException {
        int width = data.getInteger( "w", 0 );
        int height = data.getInteger( "h", 0 );

        List<Object> inputItems = data.getList( "i", false );

        ItemStack[] arrangement = new ItemStack[width * height];
        for ( int j = 0; j < height; ++j ) {
            for ( int i = 0; i < width; ++i ) {
                arrangement[j * width + i] = this.loadItemStack( new PacketBuffer( (byte[]) inputItems.get( j * width + i ), 0 ) );
            }
        }

        List<Object> outputItems = data.getList( "o", false );

        ItemStack[] outcome = new ItemStack[outputItems.size()];
        for ( int i = 0; i < outcome.length; ++i ) {
            outcome[i] = this.loadItemStack( new PacketBuffer( (byte[]) outputItems.get( i ), 0 ) );
        }

        this.shapedRecipes++;
        return new ShapedRecipe( width, height, arrangement, outcome, UUID.fromString( data.getString( "u", UUID.randomUUID().toString() ) ) );
    }

    private SmeltingRecipe loadSmeltingRecipe( NBTTagCompound data ) throws IOException {
        List<Object> inputList = data.getList( "i", false );
        byte[] inputData = (byte[]) inputList.get( 0 );
        ItemStack input = this.loadItemStack( new PacketBuffer( inputData, 0 ) );

        List<Object> outputList = data.getList( "o", false );
        byte[] outcomeData = (byte[]) outputList.get( 0 );
        ItemStack outcome = this.loadItemStack( new PacketBuffer( outcomeData, 0 ) );

        this.smeltingRecipes++;
        return new SmeltingRecipe( input, outcome, UUID.fromString( data.getString( "u", UUID.randomUUID().toString() ) ) );
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

        return this.server.getItems().create( id, data, amount, compound );
    }

}
