/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.registry;

import io.gomint.server.util.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author geNAZt
 * @version 1.0
 */
public class StringRegistry<R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringRegistry.class);

    private static final class Lookup {
        private final String id;
        private final int arguments;

        public Lookup(@Nonnull String id, int arguments) {
            this.id = id;
            this.arguments = arguments;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Lookup lookup = (Lookup) o;
            return arguments == lookup.arguments &&
                Objects.equals(id, lookup.id);
        }

        @Override
        public int hashCode() {
            int h = 1;
            h = 31 * h + id.hashCode();
            h = 31 * h + arguments;
            return h;
        }
    }

    private final GeneratorCallback<R> generatorCallback;

    private final Map<Lookup, Generator<R>> generators = new HashMap<>();
    private final Map<Class<?>, String> apiReferences = new HashMap<>();

    /**
     * Build a new generator registry
     *
     * @param callback  which is used to generate a generator for each found element
     */
    public StringRegistry(GeneratorCallback<R> callback) {
        this.generatorCallback = callback;
    }

    /**
     * Register all classes which can be found in given path
     *
     * @param classPathSearcher which should be used to search classes
     * @param classPath which should be searched
     */
    public void register(ClassPath classPathSearcher, String classPath) {
        LOGGER.debug("Going to scan: {}", classPath);

        classPathSearcher.getTopLevelClasses(classPath, classInfo -> register((Class<? extends R>) classInfo.load()));
    }

    public void registerAdditionalConstructor(String id, int parameterCount, Function<Object[], R> generator) {
        this.generators.put(new Lookup(id, parameterCount), generator::apply);
    }

    private void register(Class<? extends R> clazz) {
        String defId = null;
        for (RegisterInfo info : clazz.getAnnotationsByType(RegisterInfo.class)) {
            String id = info.sId();

            if (defId == null || info.def()) {
                defId = id;
            }

            Generator<R> generator = this.generatorCallback.generate(clazz, id);
            if (generator != null) {
                this.storeGeneratorForId(id, generator);
            }
        }

        // Check for API interfaces
        for (Class<?> apiInter : clazz.getInterfaces()) {
            this.apiReferences.put(apiInter, defId);
        }

        this.apiReferences.put(clazz, defId);
    }

    private void storeGeneratorForId(String id, Generator<R> generator) {
        Lookup lookup = new Lookup(id, 0);
        if (this.generators.containsKey(lookup)) {
            LOGGER.warn("Detected hash collision for {}", id);
        } else {
            this.generators.put(lookup, generator);
        }
    }

    public final Generator<R> getGenerator(Class<?> clazz, int amountOfParameters) {
        // Get the internal ID
        String id = this.apiReferences.getOrDefault(clazz, null);
        if (id == null) {
            return null;
        }

        return getGenerator(id, amountOfParameters);
    }

    public final Generator<R> getGenerator(Class<?> clazz) {
        return this.getGenerator(clazz, 0);
    }

    public final Generator<R> getGenerator(String id) {
        return this.getGenerator(id, 0);
    }

    public final Generator<R> getGenerator(String id, int amountOfParameters) {
        Lookup lookup = new Lookup(id, amountOfParameters);
        return this.generators.get(lookup);
    }

    public String getId(Class<?> clazz) {
        return this.apiReferences.getOrDefault(clazz, null);
    }

}
