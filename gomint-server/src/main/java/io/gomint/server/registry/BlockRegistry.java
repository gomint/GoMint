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

    private final GeneratorCallback<Block> generatorCallback;

    public BlockRegistry(List<BlockIdentifier> blockIdentifiers, GeneratorCallback<Block> callback) {
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

        classPathSearcher.getTopLevelClasses(classPath, classInfo -> register(classInfo.load()));
    }

    private void register(Class<? extends Block> clazz) {
        for (RegisterInfo info : clazz.getAnnotationsByType(RegisterInfo.class)) {
            String id = info.sId();

            Generator<Block> generator = this.generatorCallback.generate(clazz, id);
            if (generator != null) {
                int runtimeId = -1;
                for (BlockIdentifier blockIdentifier : this.blockIdentifiers) {
                    if (blockIdentifier.getBlockId().equals(id)) {
                        if (runtimeId == -1) {
                            runtimeId = blockIdentifier.getRuntimeId();
                        }

                        this.generators[blockIdentifier.getRuntimeId()] = generator;
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
