/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.assets;

import io.gomint.GoMint;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.crafting.ShapedRecipe;
import io.gomint.server.crafting.ShapelessRecipe;
import io.gomint.server.crafting.SmeltingRecipe;
import io.gomint.server.inventory.CreativeInventory;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;
import java.util.function.Function;

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

    @Getter
    private CreativeInventory creativeInventory;
    @Getter
    private Set<Recipe> recipes;

    @Getter
    private List<BlockIdentifier> blockPalette;

    // Statistics
    private int shapelessRecipes;
    private int shapedRecipes;
    private int smeltingRecipes;

    private final Items items;

    /**
     * Create new asset library
     *
     * @param items which should be used to create new items
     */
    public AssetsLibrary( Items items ) {
        this.items = items;
    }

    /**
     * Loads the assets library from the assets.dat located inside the class path.
     *
     * @throws IOException Thrown if an I/O error occurs whilst loading the library
     */
    @SuppressWarnings( "unchecked" )
    public void load() throws IOException, AllocationLimitReachedException {
        NBTTagCompound root = NBTTagCompound.readFrom( this.getClass().getResourceAsStream( "/assets.dat" ), true, ByteOrder.BIG_ENDIAN );
        if ( GoMint.instance() != null ) {
            this.debug( (List<NBTTagCompound>) ( (List) root.getList( "itemLegacyIDs", false ) ) );
            this.loadRecipes( (List<NBTTagCompound>) ( (List) root.getList( "recipes", false ) ) );
            this.loadCreativeInventory( (List<byte[]>) ( (List) root.getList( "creativeInventory", false ) ) );
            this.loadBlockPalette( (List<NBTTagCompound>) ( (List) root.getList( "blockPalette", false ) ) );
        }
    }

    private void debug(List<NBTTagCompound> itemLegacyIDs) {
        for (NBTTagCompound compound : itemLegacyIDs) {
            LOGGER.info("{} -> {}", compound.getShort("id", (short) -1), compound.getString("name", ""));
        }
    }

    private void loadBlockPalette( List<NBTTagCompound> blockPaletteCompounds ) {
        Map<String, List<SortedMap<String, Object>>> states = new HashMap<>();

        this.blockPalette = new ArrayList<>();
        for ( NBTTagCompound compound : blockPaletteCompounds ) {
            String block = compound.getCompound("block", false).getString( "name", "minecraft:air" );

            List<SortedMap<String, Object>> st = states.computeIfAbsent(block, s -> new ArrayList<>());

            SortedMap<String, Object> sta = new Object2ObjectLinkedOpenHashMap<>();
            for (Map.Entry<String, Object> entry : compound.getCompound("block", false).getCompound("states", false).entrySet()) {
                sta.put(entry.getKey(), entry.getValue());
            }

            st.add(sta);

            this.blockPalette.add( new BlockIdentifier(
                compound.getCompound("block", false).getString( "name", "minecraft:air" ),
                compound.getCompound("block", false).getCompound("states", false)
            ) );
        }

        for (Map.Entry<String, List<SortedMap<String, Object>>> entry : states.entrySet()) {
            LOGGER.info("Block {}", entry.getKey());

            for (SortedMap<String, Object> map : entry.getValue()) {
                StringBuilder builder = new StringBuilder("{");
                for (Map.Entry<String, Object> objectEntry : map.entrySet()) {
                    builder.append("\"").append(objectEntry.getKey()).append("\"").append(":").append(objectEntry.getValue()).append(" [").append(objectEntry.getValue().getClass().getName()).append("]").append(",");
                }

                builder.delete(builder.length() - 1, builder.length()).append("}");

                LOGGER.info("State: {}", builder.toString());
            }
        }
    }

    private void loadCreativeInventory( List<byte[]> raw ) {
        if ( GoMint.instance() != null ) {
            this.creativeInventory = new CreativeInventory( null, raw.size() );

            for ( byte[] bytes : raw ) {
                try {
                    this.creativeInventory.addItem( this.loadItemStack( new PacketBuffer( bytes, 0 ) ) );
                } catch ( IOException | AllocationLimitReachedException e ) {
                    LOGGER.error( "Could not load creative item: ", e );
                }
            }

            LOGGER.info( "Loaded {} items into creative inventory", raw.size() );
        }
    }

    private void loadRecipes( List<NBTTagCompound> raw ) throws IOException, AllocationLimitReachedException {
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

    private ShapelessRecipe loadShapelessRecipe( NBTTagCompound data ) throws IOException, AllocationLimitReachedException {
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

    private ShapedRecipe loadShapedRecipe( NBTTagCompound data ) throws IOException, AllocationLimitReachedException {
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

    private SmeltingRecipe loadSmeltingRecipe( NBTTagCompound data ) throws IOException, AllocationLimitReachedException {
        List<Object> inputList = data.getList( "i", false );
        byte[] inputData = (byte[]) inputList.get( 0 );
        ItemStack input = this.loadItemStack( new PacketBuffer( inputData, 0 ) );

        List<Object> outputList = data.getList( "o", false );
        byte[] outcomeData = (byte[]) outputList.get( 0 );
        ItemStack outcome = this.loadItemStack( new PacketBuffer( outcomeData, 0 ) );

        this.smeltingRecipes++;
        return new SmeltingRecipe( input, outcome, UUID.fromString( data.getString( "u", UUID.randomUUID().toString() ) ) );
    }

    private ItemStack loadItemStack( PacketBuffer buffer ) throws IOException, AllocationLimitReachedException {
        short id = buffer.readShort();
        if ( id == 0 ) {
            return this.items == null ? null : this.items.create( 0, (short) 0, (byte) 0, null );
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

        return this.items == null ? null : this.items.create( id, data, amount, compound );
    }

    /**
     * Cleanup and release resources not needed anymore
     */
    public void cleanup() {
        // Release all references to data which had been loaded to other wrappers
        this.recipes = null;
        this.blockPalette = null;
        this.creativeInventory = null;
    }

}
