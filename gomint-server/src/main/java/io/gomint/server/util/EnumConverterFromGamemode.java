/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

/**
 * @author generated
 * @version 2.0
 */
public class EnumConverterFromGamemode implements EnumConverter {

    public Enum convert( Enum value ) {
        int id = value.ordinal();
        switch ( id ) {
            case 0:
                return io.gomint.world.Gamemode.SURVIVAL;
            case 1:
                return io.gomint.world.Gamemode.CREATIVE;
            case 2:
                return io.gomint.world.Gamemode.ADVENTURE;
            case 3:
                return io.gomint.world.Gamemode.SPECTATOR;
        }

        return null;
    }

}
