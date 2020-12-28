/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

import io.gomint.server.registry.Generator;
import io.gomint.server.registry.Registry;
import io.gomint.server.util.ClassPath;
import io.gomint.server.util.performance.LambdaConstructionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Enchantments {

    private static final Logger LOGGER = LoggerFactory.getLogger(Enchantments.class);
    private final Registry<Enchantment> generators;

    public Enchantments(ClassPath classPath) {
        this.generators = new Registry<>((clazz, id) -> {
            LambdaConstructionFactory<Enchantment> factory = new LambdaConstructionFactory<>(clazz);
            return in -> {
                return factory.newInstance();
            };
        });

        this.generators.register(classPath, "io.gomint.server.enchant");
    }

    /**
     * Create enchantment
     *
     * @param id  of the enchantment
     * @param lvl of the enchantment
     * @return new enchantment instance which contains level data
     */
    public Enchantment create(short id, short lvl) {
        Generator<Enchantment> enchantmentGenerator = this.generators.getGenerator(id);
        if (enchantmentGenerator == null) {
            LOGGER.warn("Unknown enchant {}", id);
            return null;
        }

        Enchantment enchantment = enchantmentGenerator.generate();
        enchantment.setLevel(lvl);
        return enchantment;
    }

    public Enchantment create(Class<? extends io.gomint.enchant.Enchantment> clazz, short lvl) {
        Generator<Enchantment> enchantmentGenerator = this.generators.getGenerator(this.getId(clazz));
        if (enchantmentGenerator == null) {
            LOGGER.warn("Unknown enchant {}", clazz.getName());
            return null;
        }

        Enchantment enchantment = enchantmentGenerator.generate();
        enchantment.setLevel(lvl);
        return enchantment;
    }

    public short getId(Class<? extends io.gomint.enchant.Enchantment> clazz) {
        return (short) this.generators.getId(clazz);
    }

    /**
     * Create every enchantment once and return a list of them
     *
     * @return
     */
    public List<Enchantment> all() {
        return this.generators.generateAll();
    }

}
