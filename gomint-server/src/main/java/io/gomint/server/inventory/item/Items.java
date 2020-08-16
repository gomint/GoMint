package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.assets.AssetsLibrary;
import io.gomint.server.registry.Generator;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.registry.Registry;
import io.gomint.server.util.ClassPath;
import io.gomint.server.util.StringShortPair;
import io.gomint.server.util.performance.LambdaConstructionFactory;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
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

    private static final IntSet ALREADY_WARNED = new IntArraySet();
    private static final Logger LOGGER = LoggerFactory.getLogger(Items.class);
    private final Registry<io.gomint.server.inventory.item.ItemStack> generators;
    private final Object2IntMap<String> blockIdToItemId = new Object2IntOpenHashMap<>();
    private final Int2ObjectMap<String> itemIdToBlockId = new Int2ObjectOpenHashMap<>();

    private PacketBuffer packetCache;
    private AssetsLibrary assets;

    /**
     * Create a new item registry
     *
     * @param classPath  which builds this registry
     */
    public Items(ClassPath classPath) {
        this.generators = new Registry<>(classPath, (clazz, id) -> {
            LambdaConstructionFactory<io.gomint.server.inventory.item.ItemStack> factory = new LambdaConstructionFactory<>(clazz);

            int material = 0;

            RegisterInfo[] info = clazz.getAnnotationsByType(RegisterInfo.class);
            for (RegisterInfo registerInfo : info) {
                if (registerInfo.def() || material == 0) {
                    material = registerInfo.id();
                }

                if (registerInfo.sId().length() > 0) {
                    this.itemIdToBlockId.put(registerInfo.id(), registerInfo.sId());
                    this.blockIdToItemId.put(registerInfo.sId(), registerInfo.id());
                }
            }

            int finalMaterial = material;
            return in -> {
                io.gomint.server.inventory.item.ItemStack itemStack = factory.newInstance();
                itemStack.setMaterial(finalMaterial).setItems(this);
                return itemStack;
            };
        });

        this.generators.register("io.gomint.server.inventory.item");
        this.generators.cleanup();
    }

    public String getBlockId(int itemId) {
        return this.itemIdToBlockId.get(itemId);
    }

    public int getMaterial(String blockId) {
        return this.blockIdToItemId.getInt(blockId);
    }

    public <T extends ItemStack> T create(String id, short data, byte amount, NBTTagCompound nbt) {
        // Resolve the item id and create as ever
        return this.create(this.getMaterial(id), data, amount, nbt);
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
        Generator<io.gomint.server.inventory.item.ItemStack> itemGenerator = this.generators.getGenerator(id);
        if (itemGenerator == null) {
            if (!ALREADY_WARNED.contains(id)) {
                LOGGER.warn("Unknown item {} ({}) / total unknown {}", id, this.assets.getItemID(id), ALREADY_WARNED.size());

                // Try to generate the implementation
                String blockId = this.assets.getItemID(id).split(":")[1];
                String className = WordUtils.capitalize(blockId, '_').replaceAll("_", "");

                Map<String, String> replace = new HashMap<>();
                replace.put("NAME", className);
                replace.put("BLOCK_ID", this.assets.getItemID(id));
                replace.put("ITEM_ID", String.valueOf(id));
                replace.put("ENUM", blockId.toUpperCase());
                generate("gen/item_api.txt", "gen/api/Item" + className + ".java", replace);
                generate("gen/item_implementation.txt", "gen/impl/Item" + className + ".java", replace);

                ALREADY_WARNED.add(id);
            }

            return null;
        }

        // Cleanup NBT tag, root must be empty string
        if (nbt != null && !nbt.getName().isEmpty()) {
            nbt = nbt.deepClone("");
        }

        io.gomint.server.inventory.item.ItemStack itemStack = itemGenerator.generate();
        itemStack.setNbtData(nbt).setMaterial(id).setData(data);

        if (amount > 0) {
            return (T) itemStack.setAmount(amount);
        }

        return (T) itemStack;
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

    public void setAssets(AssetsLibrary assetsLibrary) {
        this.assets = assetsLibrary;
    }

    public void initItemIDs(List<StringShortPair> itemIDs) {
        PacketBuffer buffer = new PacketBuffer(itemIDs.size() * 32);
        buffer.writeUnsignedVarInt( itemIDs.size() );
        for (StringShortPair itemID : itemIDs) {
            buffer.writeString(itemID.getBlockId());
            buffer.writeLShort(itemID.getData());
        }

        this.packetCache = buffer;
    }

    public PacketBuffer getPacketCache() {
        return packetCache;
    }

}
