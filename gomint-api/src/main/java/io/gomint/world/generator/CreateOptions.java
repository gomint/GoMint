/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator;

import io.gomint.world.WorldType;
import io.gomint.world.generator.integrated.NormalGenerator;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class CreateOptions {

    private WorldType worldType = WorldType.PERSISTENT;
    private Class<? extends ChunkGenerator> generator = NormalGenerator.class;

    public CreateOptions worldType(WorldType worldType) {
        this.worldType = worldType;
        return this;
    }

    public CreateOptions generator(Class<? extends ChunkGenerator> generator) {
        this.generator = generator;
        return this;
    }

    public WorldType worldType() {
        return this.worldType;
    }

    public Class<? extends ChunkGenerator> generator() {
        return this.generator;
    }

}
