package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.Generator;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.registry.RegisterInfos;
import io.gomint.server.registry.Registry;
import io.gomint.server.util.ClassPath;
import io.gomint.server.util.Pair;
import io.gomint.server.util.performance.ObjectConstructionFactory;
import io.gomint.taglib.NBTTagCompound;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Component
public class Items {

    private static final IntSet ALREADY_WARNED = new IntArraySet();
    private static final Logger LOGGER = LoggerFactory.getLogger(Items.class);
    private final Registry<io.gomint.server.inventory.item.ItemStack> generators;
    private final Object2IntMap<String> blockIdToItemId = new Object2IntOpenHashMap<>();
    private final Int2ObjectMap<String> itemIdToBlockId = new Int2ObjectOpenHashMap<>();

    /**
     * Create a new item registry
     *
     * @param classPath  which builds this registry
     */
    @Autowired
    public Items(ClassPath classPath) {
        this.generators = new Registry<>(classPath, clazz -> {
            ObjectConstructionFactory factory = new ObjectConstructionFactory(clazz);

            RegisterInfo[] info = clazz.getAnnotationsByType(RegisterInfo.class);
            for (RegisterInfo registerInfo : info) {
                if (registerInfo.sId().length() > 0) {
                    this.itemIdToBlockId.put(registerInfo.id(), registerInfo.sId());
                    this.blockIdToItemId.put(registerInfo.sId(), registerInfo.id());
                }
            }

            return () -> {
                io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) factory.newInstance();
                itemStack.setItems(this);
                return itemStack;
            };
        });

        this.generators.register("io.gomint.server.inventory.item");
        this.generators.cleanup();
    }

    public String getBlockId(int itemId) {
        return this.itemIdToBlockId.get(itemId);
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
        Generator<io.gomint.server.inventory.item.ItemStack> itemGenerator = this.generators.getGenerator(id);
        if (itemGenerator == null) {
            if (!ALREADY_WARNED.contains(id)) {
                LOGGER.warn("Unknown item {} / total unknown {}", id, ALREADY_WARNED.size());
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
