/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import io.gomint.world.Gamemode;

/**
 * @author generated
 * @version 2.0
 */
public class EnumConverterFromGamemode implements EnumConverter {

    public Enum convert( Enum value ) {
        int id = value.ordinal();
        switch ( id ) {
            case 0:
                return Gamemode.SURVIVAL;
            case 1:
                return Gamemode.CREATIVE;
            case 2:
                return Gamemode.ADVENTURE;
            case 3:
                return Gamemode.SPECTATOR;
        }

        return null;
    }

}
