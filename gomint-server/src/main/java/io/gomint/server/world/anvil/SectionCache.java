/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil;

import io.gomint.server.world.HeapNibbleArray;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class SectionCache {

    private int sectionY;
    private byte[] blocks;
    private HeapNibbleArray add;
    private HeapNibbleArray data;

    public boolean isAllAir() {
        for ( byte block : blocks ) {
            if ( block != 0 ) {
                return false;
            }
        }

        return true;
    }

}
