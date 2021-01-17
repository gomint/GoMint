package io.gomint.server.registry;

import io.gomint.server.util.ClassPath;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Registry<R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Registry.class);

    private final GeneratorCallback<R, String> generatorCallback;

    private Generator<R>[] generators;
    private Generator<R>[] negativeGenerators;

    private final Object2IntMap<Class<?>> apiReferences = new Object2IntOpenHashMap<>();

    /**
     * Build a new generator registry
     *
     * @param callback which is used to generate a generator for each found element
     */
    public Registry(GeneratorCallback<R, String> callback) {
        this.generatorCallback = callback;
        this.generators = (Generator<R>[]) new Generator[16];
        this.negativeGenerators = (Generator<R>[]) new Generator[2];
    }

    /**
     * Register all classes which can be found in given path
     *
     * @param classPath which should be searched
     */
    public void register(ClassPath classPathSearcher, String classPath) {
        LOGGER.debug("Going to scan: {}", classPath);

        classPathSearcher.getTopLevelClasses(classPath, classInfo -> register((Class<? extends R>) classInfo.load()));
    }

    private void register(Class<? extends R> clazz) {
        for (RegisterInfo info : clazz.getAnnotationsByType(RegisterInfo.class)) {
            Generator<R> generator = this.generatorCallback.generate(clazz, info.sId());
            if (generator != null) {
                int id = info.id();
                this.storeGeneratorForId(id, generator);

                // Check for API interfaces
                for (Class<?> apiInter : clazz.getInterfaces()) {
                    this.apiReferences.put(apiInter, id);
                }

                this.apiReferences.put(clazz, id);
            }
        }
    }

    private void storeGeneratorForId(int id, Generator<R> generator) {
        boolean negative = false;
        if (id < 0) {
            id = Math.abs(id);
            negative = true;
        }

        Generator<R>[] array = this.ensureArraySize(negative, id);
        array[id] = generator;
    }

    private Generator<R>[] ensureArraySize(boolean negative, int id) {
        // Check if we need to grow the array
        Generator<R>[] array = (negative) ? this.negativeGenerators : this.generators;
        if (array.length < id + 16) {
            Generator<R>[] temp = (Generator<R>[]) new Generator[id + 16];
            System.arraycopy(array, 0, temp, 0, array.length);
            if (negative) {
                this.negativeGenerators = temp;
            } else {
                this.generators = temp;
            }
        }

        return (negative) ? this.negativeGenerators : this.generators;
    }

    public Generator<R> getGenerator(Class<?> clazz) {
        // Get the internal ID
        int id = apiReferences.getOrDefault(clazz, -1);
        if (id == -1) {
            return null;
        }

        return getGenerator(id);
    }

    public final Generator<R> getGenerator(int id) {
        if (id < 0) {
            id *= -1;
            return this.negativeGenerators.length <= id ? null : this.negativeGenerators[id];
        }

        return this.generators.length <= id ? null : this.generators[id];
    }

    public int getId(Class<?> clazz) {
        return this.apiReferences.getOrDefault(clazz, -1);
    }

    public List<R> generateAll() {
        List<R> all = new ArrayList<>();

        for (Generator<R> generator : this.generators) {
            if (generator != null) {
                all.add(generator.generate());
            }
        }

        for (Generator<R> generator : this.negativeGenerators) {
            if (generator != null) {
                all.add(generator.generate());
            }
        }

        return all;
    }

}
