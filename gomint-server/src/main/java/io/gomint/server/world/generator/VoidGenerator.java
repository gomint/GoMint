/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.generator;

import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
public class VoidGenerator extends ChunkGenerator {

    public VoidGenerator( WorldAdapter worldAdapter ) {
        super( worldAdapter );
    }

    @Override
    public ChunkAdapter generate( int x, int z ) {
        return generateEmpty( x, z );
    }

}
