package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.registry.Generator;
import io.gomint.server.registry.StringRegistry;
import io.gomint.server.util.ClassPath;
import io.gomint.server.util.StringShortPair;
import io.gomint.server.util.performance.LambdaConstructionFactory;
import io.gomint.server.world.block.Block;
import io.gomint.server.world.block.Blocks;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Items {

    private static final Logger LOGGER = LoggerFactory.getLogger(Items.class);
    private final StringRegistry<io.gomint.server.inventory.item.ItemStack> generators;
    private final Object2IntMap<String> blockIdToItemId = new Object2IntOpenHashMap<>();
    private final Int2ObjectMap<String> itemIdToBlockId = new Int2ObjectOpenHashMap<>();

    private PacketBuffer packetCache;
    private Blocks blocks;

    /**
     * Create a new item registry
     *
     * @param classPath which builds this registry
     */
    public Items(ClassPath classPath) {
        this.generators = new StringRegistry<>((clazz, id) -> {
            LambdaConstructionFactory<io.gomint.server.inventory.item.ItemStack> factory = new LambdaConstructionFactory<>(clazz);

            return in -> {
                io.gomint.server.inventory.item.ItemStack itemStack = factory.newInstance();
                itemStack.setMaterial(id).setItems(this).setBlocks(blocks);
                return itemStack;
            };
        });

        this.generators.register(classPath,"io.gomint.server.inventory.item");
    }

    public String getMaterial(int itemId) {
        return this.itemIdToBlockId.get(itemId);
    }

    public <T extends ItemStack> T create(String id, short data, byte amount, NBTTagCompound nbt) {
        Generator<io.gomint.server.inventory.item.ItemStack> itemGenerator = this.generators.getGenerator(id);
        if (itemGenerator == null) {
            LOGGER.error("Unknown item generator for id {}", id);
            return null;
        }

        // Cleanup NBT tag, root must be empty string
        if (nbt != null && !nbt.getName().isEmpty()) {
            nbt = nbt.deepClone("");
        }

        io.gomint.server.inventory.item.ItemStack itemStack = itemGenerator.generate();
        itemStack.setNbtData(nbt).setData(data);

        if (amount > 0) {
            return (T) itemStack.setAmount(amount);
        }

        return (T) itemStack;
    }

    /**
     * Create a new item stack based on a id
     *
     * @param id     of the type for this item stack
     * @param data   for this item stack
     * @param amount in this item stack
     * @param nbt    additional data for this item stack
     * @param <T>    type of item stack
     * @return generated item stack
     */
    public <T extends ItemStack> T create(int id, short data, byte amount, NBTTagCompound nbt) {
        if (id == 0) {
            return null;
        }

        // Resolve the item id and create as ever
        return this.create(this.getMaterial(id), data, amount, nbt);
    }

    private void generate(String templateFile, String outputFile, Map<String, String> data) {
        try {
            String template = Files.readString(Paths.get(templateFile));
            for (Map.Entry<String, String> entry : data.entrySet()) {
                template = template.replaceAll("%" + entry.getKey() + "%", entry.getValue());
            }

            Files.writeString(Paths.get(outputFile), template);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new item stack based on a api interface
     *
     * @param itemClass which defines what item to use
     * @param amount    in this item stack
     * @param <T>       type of item stack
     * @return generated item stack
     */
    public <T extends ItemStack> T create(Class<T> itemClass, byte amount) {
        Generator<io.gomint.server.inventory.item.ItemStack> itemGenerator = this.generators.getGenerator(itemClass);
        if (itemGenerator == null) {
            return null;
        }

        io.gomint.server.inventory.item.ItemStack itemStack = itemGenerator.generate();
        if (amount > 0) {
            itemStack.setAmount(amount);
        }

        return (T) itemStack.setData((short) 0);
    }

    public void initItemIDs(List<StringShortPair> itemIDs) {
        PacketBuffer buffer = new PacketBuffer(itemIDs.size() * 32);
        buffer.writeUnsignedVarInt(itemIDs.size());
        for (StringShortPair itemID : itemIDs) {
            buffer.writeString(itemID.getBlockId());
            buffer.writeLShort(itemID.getData());
            buffer.writeBoolean(false);

            Generator<io.gomint.server.inventory.item.ItemStack> item = this.generators.getGenerator(itemID.getBlockId());
            if (item == null) {
                LOGGER.warn("Unknown item {} ({})", itemID.getData(), itemID.getBlockId());

                // Try to generate the implementation
                String blockId = itemID.getBlockId().split(":")[1];
                String className = WordUtils.capitalize(blockId, '_', '.').replaceAll("_", "").replaceAll("\\.", "");

                Map<String, String> replace = new HashMap<>();
                replace.put("NAME", className);
                replace.put("BLOCK_ID", itemID.getBlockId());
                replace.put("ITEM_ID", String.valueOf(itemID.getData()));
                replace.put("ENUM", blockId.replaceAll("\\.", "").toUpperCase());
                generate("generator/src/main/resources/item_api.txt", "gomint-api/src/main/java/io/gomint/inventory/item/Item" + className + ".java", replace);
                generate("generator/src/main/resources/item_implementation.txt", "gomint-server/src/main/java/io/gomint/server/inventory/item/Item" + className + ".java", replace);
            }

            String internBlockId = itemID.getBlockId().intern();
            this.blockIdToItemId.put(internBlockId, itemID.getData());
            this.itemIdToBlockId.put(itemID.getData(), internBlockId);
        }

        this.packetCache = buffer;
    }

    public PacketBuffer getPacketCache() {
        return packetCache;
    }

    public int getRuntimeId(String material) {
        return this.blockIdToItemId.getInt(material);
    }

    public void setBlocks(Blocks blocks) {
        this.blocks = blocks;
    }

}
