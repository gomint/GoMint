/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator;

import io.gomint.world.WorldType;
import io.gomint.world.generator.integrated.NormalGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author geNAZt
 * @version 1.0
 */
@Accessors( fluent = true )
@Setter
@Getter
public class CreateOptions {

    private WorldType worldType = WorldType.PERSISTENT;
    private Class<? extends ChunkGenerator> generator = NormalGenerator.class;

}
