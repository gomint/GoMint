/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.collection;

import com.koloboke.collect.map.ByteObjCursor;
import com.koloboke.compile.KolobokeMap;
import io.gomint.server.entity.potion.effect.Effect;

/**
 * @author geNAZt
 * @version 1.0
 */
@KolobokeMap
public abstract class EffectIDMap {

    /**
     * Create a new map for chunk caches
     *
     * @param expectedSize for the new map
     * @return a new map which uses long as key and ChunkAdapter values
     */
    public static EffectIDMap withExpectedSize( int expectedSize ) {
        return new KolobokeEffectIDMap( expectedSize );
    }

    // CHECKSTYLE:OFF
    public abstract void justPut(byte key, Effect entity);
    public abstract Effect remove(byte key);
    public abstract Effect get(byte key);
    public abstract ByteObjCursor<Effect> cursor();
    // CHECKSTYLE:ON

}
