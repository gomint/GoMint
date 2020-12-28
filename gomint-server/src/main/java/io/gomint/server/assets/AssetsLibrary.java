/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.assets;

import io.gomint.GoMint;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.crafting.RecipeManager;
import io.gomint.server.crafting.ShapedRecipe;
import io.gomint.server.crafting.ShapelessRecipe;
import io.gomint.server.crafting.SmeltingRecipe;
import io.gomint.server.inventory.CreativeInventory;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.util.Allocator;
import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.util.StringShortPair;
import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.taglib.NBTStream;
import io.gomint.taglib.NBTStreamListener;
import io.gomint.taglib.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetsLibrary.class);

    private CreativeInventory creativeInventory;

    private List<BlockIdentifier> blockPalette;
    private List<StringShortPair> itemIDs;

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
    public AssetsLibrary(Items items) {
        this.items = items;
    }

    /**
     * Loads the assets library from the assets.dat located inside the class path.
     *
     * @throws IOException Thrown if an I/O error occurs whilst loading the library
     */
    @SuppressWarnings("unchecked")
    public void load(RecipeManager recipeManager) throws IOException, AllocationLimitReachedException {
        InputStream in = this.getClass().getResourceAsStream("/assets.dat");
        ByteBuf buf = Allocator.allocate(in.readAllBytes());

        NBTTagCompound root = NBTTagCompound.readFrom(buf, ByteOrder.BIG_ENDIAN);
        if (GoMint.instance() != null) {
            this.loadItemIDs((List<NBTTagCompound>) ((List) root.getList("itemLegacyIDs", false)));
            this.loadRecipes(recipeManager, (List<NBTTagCompound>) ((List) root.getList("recipes", false)));
            this.loadCreativeInventory((List<byte[]>) ((List) root.getList("creativeInventory", false)));
            this.loadBlockPalette((List<NBTTagCompound>) ((List) root.getList("blockPalette", false)));
        }

        buf.release();
    }

    private void loadItemIDs(List<NBTTagCompound> itemLegacyIDs) {
        this.itemIDs = new ArrayList<>();
        for (NBTTagCompound itemLegacyID : itemLegacyIDs) {
            StringShortPair pair = new StringShortPair(itemLegacyID.getString("name", ""), itemLegacyID.getShort("id", (short) 0));
            this.itemIDs.add(pair);
        }

        this.items.initItemIDs(this.itemIDs);
    }

    private void loadBlockPalette(List<NBTTagCompound> blockPaletteCompounds) {
        int blockNumber = 0;
        Map<String, Integer> knownBlocks = new HashMap<>();

        this.blockPalette = new ArrayList<>();

        short runtimeId = 0;
        for (NBTTagCompound compound : blockPaletteCompounds) {
            String block = compound.getString("name", "minecraft:air");

            Integer id = knownBlocks.get(block);
            if ( id == null ) {
                id = blockNumber++;
                knownBlocks.put(block, id);
            }

            BlockIdentifier identifier = new BlockIdentifier(
                block,
                id,
                runtimeId++,
                compound.getCompound("states", false)
            );

            this.blockPalette.add(identifier);
        }
    }

    private void loadCreativeInventory(List<byte[]> raw) {
        if (GoMint.instance() != null) {
            this.creativeInventory = new CreativeInventory(null, raw.size());

            int index = 0;
            for (byte[] bytes : raw) {
                try {
                    ByteBuf i = PooledByteBufAllocator.DEFAULT.directBuffer(bytes.length);
                    i.writeBytes(bytes);

                    ItemStack itemStack = this.loadItemStack(new PacketBuffer(i));
                    if (itemStack != null) {
                        itemStack.setStackId(index);
                        this.creativeInventory.setItemWithoutClone(index, itemStack);
                        index++;
                    }

                    i.release(2);
                } catch (IOException | AllocationLimitReachedException e) {
                    LOGGER.error("Could not load creative item: ", e);
                }
            }

            LOGGER.info("Loaded {} items into creative inventory", raw.size());
        }
    }

    private void loadRecipes(RecipeManager recipeManager, List<NBTTagCompound> raw) throws IOException, AllocationLimitReachedException {
        this.shapelessRecipes = 0;
        this.shapedRecipes = 0;
        this.smeltingRecipes = 0;

        if (raw == null) {
            return;
        }

        for (NBTTagCompound compound : raw) {
            byte type = compound.getByte("type", (byte) -1);
            Recipe recipe;
            switch (type) {
                case 0:
                    recipe = this.loadShapelessRecipe(compound);
                    break;

                case 1:
                    recipe = this.loadShapedRecipe(compound);
                    break;

                case 2:
                    recipe = this.loadSmeltingRecipe(compound);
                    break;

                default:
                    continue;
            }

            recipeManager.registerRecipe(recipe);
        }

        LOGGER.info("Loaded {} shapeless, {} shaped and {} smelting recipes", this.shapelessRecipes, this.shapedRecipes, this.smeltingRecipes);
    }

    private ShapelessRecipe loadShapelessRecipe(NBTTagCompound data) throws IOException, AllocationLimitReachedException {
        String name = data.getString("name", null);
        String block = data.getString("block", null);
        int priority = data.getInteger("prio", 50);

        List<Object> inputItems = data.getList("i", false);
        ItemStack[] ingredients = new ItemStack[inputItems.size()];
        for (int i = 0; i < ingredients.length; ++i) {
            byte[] in = (byte[]) inputItems.get(i);
            ByteBuf ini = PooledByteBufAllocator.DEFAULT.directBuffer(in.length);
            ini.writeBytes(in);
            ingredients[i] = this.loadItemStack(new PacketBuffer(ini));
            ini.release(2);
        }

        List<Object> outputItems = data.getList("o", false);
        ItemStack[] outcome = new ItemStack[outputItems.size()];
        for (int i = 0; i < outcome.length; ++i) {
            byte[] in = (byte[]) outputItems.get(i);
            ByteBuf ini = PooledByteBufAllocator.DEFAULT.directBuffer(in.length);
            ini.writeBytes(in);
            outcome[i] = this.loadItemStack(new PacketBuffer(ini));
            ini.release(2);
        }

        this.shapelessRecipes++;
        return new ShapelessRecipe(name.intern(), block.intern(), ingredients, outcome, null, priority);
    }

    private ShapedRecipe loadShapedRecipe(NBTTagCompound data) throws IOException, AllocationLimitReachedException {
        String name = data.getString("name", null);
        String block = data.getString("block", null);
        int priority = data.getInteger("prio", 50);

        int width = data.getInteger("w", 0);
        int height = data.getInteger("h", 0);

        List<Object> inputItems = data.getList("i", false);

        ItemStack[] arrangement = new ItemStack[width * height];
        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                byte[] in = (byte[]) inputItems.get(j * width + i);
                ByteBuf ini = PooledByteBufAllocator.DEFAULT.directBuffer(in.length);
                ini.writeBytes(in);
                arrangement[j * width + i] = this.loadItemStack(new PacketBuffer(ini));
                ini.release(2);
            }
        }

        List<Object> outputItems = data.getList("o", false);

        ItemStack[] outcome = new ItemStack[outputItems.size()];
        for (int i = 0; i < outcome.length; ++i) {
            byte[] in = (byte[]) outputItems.get(i);
            ByteBuf ini = PooledByteBufAllocator.DEFAULT.directBuffer(in.length);
            ini.writeBytes(in);
            outcome[i] = this.loadItemStack(new PacketBuffer(ini));
            ini.release(2);
        }

        this.shapedRecipes++;
        return new ShapedRecipe(name.intern(), block.intern(), width, height, arrangement, outcome, UUID.fromString(data.getString("u", UUID.randomUUID().toString())), priority);
    }

    private SmeltingRecipe loadSmeltingRecipe(NBTTagCompound data) throws IOException, AllocationLimitReachedException {
        String block = data.getString("block", null);
        int priority = data.getInteger("prio", 50);

        List<Object> inputList = data.getList("i", false);
        byte[] i = (byte[]) inputList.get(0);
        ByteBuf inputData = PooledByteBufAllocator.DEFAULT.directBuffer(i.length);
        inputData.writeBytes(i);
        ItemStack input = this.loadItemStack(new PacketBuffer(inputData));
        inputData.release(2);

        List<Object> outputList = data.getList("o", false);
        byte[] o = (byte[]) outputList.get(0);
        ByteBuf outputData = PooledByteBufAllocator.DEFAULT.directBuffer(o.length);
        outputData.writeBytes(o);
        ItemStack outcome = this.loadItemStack(new PacketBuffer(outputData));
        outputData.release(2);

        this.smeltingRecipes++;
        return new SmeltingRecipe(block.intern(), input, outcome, UUID.fromString(data.getString("u", UUID.randomUUID().toString())), priority);
    }

    private ItemStack loadItemStack(PacketBuffer buffer) throws IOException, AllocationLimitReachedException {
        short id = buffer.readShort();
        if (id == 0) {
            return this.items == null ? null : this.items.create("minecraft:air", (short) 0, (byte) 0, null);
        }

        byte amount = buffer.readByte();
        short data = buffer.readShort();
        short extraLen = buffer.readShort();

        NBTTagCompound compound = null;
        if (extraLen > 0) {
            compound = NBTTagCompound.readFrom(buffer.getBuffer(), ByteOrder.BIG_ENDIAN);
        }

        return this.items == null ? null : this.items.create(id, data, amount, compound);
    }

    public CreativeInventory getCreativeInventory() {
        return creativeInventory;
    }

    public List<BlockIdentifier> getBlockPalette() {
        return blockPalette;
    }

}
