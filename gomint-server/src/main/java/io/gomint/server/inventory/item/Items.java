package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.Generator;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.registry.Registry;
import io.gomint.server.util.ClassPath;
import io.gomint.server.util.Pair;
import io.gomint.server.util.performance.ObjectConstructionFactory;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Component
public class Items {

    private static final Logger LOGGER = LoggerFactory.getLogger(Items.class);
    private final Registry<io.gomint.server.inventory.item.ItemStack> generators;
    private final Int2ObjectMap<Pair<Integer, Integer>> itemMapper;
    private final Object2IntMap<String> blockIdToItemId = new Object2IntOpenHashMap<>();

    /**
     * Create a new item registry
     *
     * @param classPath  which builds this registry
     * @param mapperData data which may be used to map item ids
     */
    @Autowired
    public Items(ClassPath classPath, List<NBTTagCompound> mapperData) {
        this.generators = new Registry<>(classPath, clazz -> {
            ObjectConstructionFactory factory = new ObjectConstructionFactory(clazz);

            // We need one instance to get the string blockid
            io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) factory.newInstance();
            String blockId = itemStack.getBlockId();
            if (blockId != null) {
                int id = clazz.getAnnotation(RegisterInfo.class).id();
                this.blockIdToItemId.put(blockId, id);
            }

            return () -> (io.gomint.server.inventory.item.ItemStack) factory.newInstance();
        });

        this.generators.register("io.gomint.server.inventory.item");
        this.generators.cleanup();

        if (mapperData != null) {
            this.itemMapper = new Int2ObjectOpenHashMap<>();

            for (NBTTagCompound compound : mapperData) {
                int source = compound.getInteger("s", -1);
                int target = compound.getInteger("t", -1);
                int targetMeta = compound.getInteger("tm", -1);

                if (source != -1 && target != -1 && targetMeta != -1) {
                    this.itemMapper.put(source, new Pair<>(target, targetMeta));
                }
            }
        } else {
            this.itemMapper = null;
        }
    }

    public <T extends ItemStack> T create(String id, short data, byte amount, NBTTagCompound nbt) {
        // Resolve the item id and create as ever
        return this.create(this.blockIdToItemId.getInt(id), data, amount, nbt);
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
        // Lets check for a mapper
        if (this.itemMapper != null) {
            Pair<Integer, Integer> replacement = this.itemMapper.get(id);
            if (replacement != null) {
                id = replacement.getFirst();
                data = replacement.getSecond().shortValue();
            }
        }

        Generator<io.gomint.server.inventory.item.ItemStack> itemGenerator = this.generators.getGenerator(id);
        if (itemGenerator == null) {
            LOGGER.warn("Unknown item {}", id);
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
        itemStack.setMaterial(this.generators.getId(itemClass));

        if (amount > 0) {
            itemStack.setAmount(amount);
        }

        return (T) itemStack.setData((short) 0);
    }

}
