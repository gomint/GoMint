package io.gomint.server.registry;

import io.gomint.server.util.BlockIdentifier;
import io.gomint.server.util.ClassPath;

import io.gomint.server.world.block.Block;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockRegistry.class);

    private List<BlockIdentifier> blockIdentifiers;
    private final Generator<Block>[] generators;

    private final Map<Class<?>, Generator<Block>> apiReferences = new HashMap<>();
    private final Map<Class<?>, String> blockIDs = new HashMap<>();
    private final Object2IntMap<String> idToRuntime = new Object2IntOpenHashMap<>();

    private final GeneratorCallback<Block, BlockIdentifierPredicate> generatorCallback;

    public BlockRegistry(List<BlockIdentifier> blockIdentifiers, GeneratorCallback<Block, BlockIdentifierPredicate> callback) {
        this.generatorCallback = callback;
        this.generators = new Generator[blockIdentifiers.size()];
        this.blockIdentifiers = blockIdentifiers;
    }

    /**
     * Register all classes which can be found in given path
     *
     * @param classPath which should be searched
     */
    public void register(ClassPath classPathSearcher, String classPath) {
        LOGGER.debug("Going to scan: {}", classPath);

        classPathSearcher.getTopLevelClasses(classPath, classInfo -> {
            Class<?> clz = classInfo.load();
            if (Block.class.isAssignableFrom(clz)) {
                register((Class<? extends Block>) clz);
            }
        });
    }

    private void register(Class<? extends Block> clazz) {
        for (RegisterInfo info : clazz.getAnnotationsByType(RegisterInfo.class)) {
            String id = info.sId();

            BlockIdentifierPredicate predicate = this.parseId(id);

            Generator<Block> generator = this.generatorCallback.generate(clazz, predicate);
            if (generator != null) {
                int runtimeId = -1;
                for (BlockIdentifier blockIdentifier : this.blockIdentifiers) {
                    if (this.generators[blockIdentifier.runtimeId()] != null &&
                        !predicate.testsStates()) {
                        continue;
                    }

                    if (predicate.test(blockIdentifier)) {
                        if (runtimeId == -1) {
                            runtimeId = blockIdentifier.runtimeId();
                        }

                        this.generators[blockIdentifier.runtimeId()] = generator;
                    }
                }

                this.idToRuntime.put(id, runtimeId);

                // Check for API interfaces
                for (Class<?> apiInter : clazz.getInterfaces()) {
                    this.apiReferences.put(apiInter, generator);
                    this.blockIDs.put(apiInter, id);
                }

                this.apiReferences.put(clazz, generator);
                this.blockIDs.put(clazz, id);
            }
        }
    }

    /**
     * This method should parse additional information out of the given id. A id can contain additional state selectors
     * the following formats:
     * <p>
     * - minecraft:stone_slab2[stone_slab_type_2=prismarine_rough,prismarine_dark,prismarine_brick]
     * - minecraft:stone_slab2[stone_slab_type_2=purpur;direction=x]
     * - minecraft:stone_slab2
     *
     * @param id which should be parsed
     * @return a predicate which decides which runtime id it wants to bind to
     */
    private BlockIdentifierPredicate parseId(String id) {
        // Check if we have a [
        int index = id.indexOf("[");
        if (index == -1) {
            return new BlockIdentifierPredicate(id);
        }

        String blockId = id.substring(0, index);
        if (blockId.length() == id.length()) {
            return new BlockIdentifierPredicate(blockId);
        }

        // We assume that the last char is a ]
        if (!id.endsWith("]")) {
            throw new RuntimeException("We found a state selector but it doesn't end with ]");
        }

        BlockIdentifierPredicate predicate = new BlockIdentifierPredicate(blockId);
        String[] keyValues = id.substring(index + 1, id.length() - 1).split(";");
        for (String keyValue : keyValues) {
            String[] keyAndValue = keyValue.split("=");
            String key = keyAndValue[0];
            String[] values = keyAndValue[1].split(",");
            predicate.keyValues(key, values);
        }

        return predicate;
    }

    public void cleanup() {
        this.blockIdentifiers = null;
    }

    public Generator<Block> getGenerator(int runtimeId) {
        return this.generators[runtimeId];
    }

    public Generator<Block> getGenerator(Class<?> apiInterface) {
        return this.apiReferences.get(apiInterface);
    }

    public String getID(Class<?> apiInterface) {
        return this.blockIDs.get(apiInterface);
    }

    public Generator<Block> getGenerator(String id) {
        return this.getGenerator(this.idToRuntime.getInt(id));
    }

}
