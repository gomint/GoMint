/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.generator;

import io.gomint.server.entity.potion.effect.Haste;
import io.gomint.server.player.EffectManager;

/**
 * @author generated
 * @version 2.0
 */
public class HasteGenerator implements EffectGenerator {

    public Haste generate( EffectManager manager, int amplifier, long lengthInMS ) {
        return new Haste( manager, amplifier, lengthInMS );
    }

}
