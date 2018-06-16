/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import io.gomint.server.world.SoundMagicNumbers;

/**
 * @author generated
 * @version 2.0
 */
public class EnumConverterFromSoundMagicNumbers implements EnumConverter {

    private SoundMagicNumbers[] values;

    public EnumConverterFromSoundMagicNumbers() {
        this.values = io.gomint.server.world.SoundMagicNumbers.values();
    }

    public Enum convert( Enum value ) {
        int id = value.ordinal();
        if ( id >= this.values.length ) {
            return null;
        }

        return this.values[id];
    }

}
