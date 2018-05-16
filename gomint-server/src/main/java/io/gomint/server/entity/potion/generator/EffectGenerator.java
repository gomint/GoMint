/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion.generator;

import io.gomint.server.entity.potion.effect.Effect;
import io.gomint.server.player.EffectManager;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EffectGenerator {

    /**
     * Create a new effect
     *
     * @param manager    which manages this effect
     * @param amplifier  strength of the effect
     * @param lengthInMS length of the effect given in milliseconds
     * @param <T>        type of effect
     */
    <T extends Effect> T generate( EffectManager manager, int amplifier, long lengthInMS );

}
