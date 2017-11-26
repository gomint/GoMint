/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EnumConverter {

    /**
     * Convert a value from one enum to another enum
     *
     * @param v value from the enum
     * @return value from the other enum
     */
    Enum convert( Enum v );

}
