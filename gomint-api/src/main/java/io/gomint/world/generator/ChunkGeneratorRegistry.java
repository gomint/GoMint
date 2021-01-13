/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator;

import java.util.Collection;

/**
 * @author Clockw1seLrd
 * @version 1.0
 * @stability 3
 */
public interface ChunkGeneratorRegistry {

    boolean registerGenerator( String name, Class<? extends ChunkGenerator> generatorClass );

    boolean isGeneratorAvailable( String name );

    Class<? extends ChunkGenerator> generatorClass(String name );

    Collection<String> registeredGenerators();

    Collection<Class<? extends ChunkGenerator>> registeredGeneratorClasses();

}
