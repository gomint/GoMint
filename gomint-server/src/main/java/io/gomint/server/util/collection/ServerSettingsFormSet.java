/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.collection;

import com.koloboke.collect.IntCursor;
import com.koloboke.collect.LongCursor;
import com.koloboke.compile.ConcurrentModificationUnchecked;
import com.koloboke.compile.KolobokeSet;

/**
 * @author geNAZt
 * @version 1.0
 */
@KolobokeSet
@ConcurrentModificationUnchecked
public abstract class ServerSettingsFormSet {

    /**
     * Create a new set for invalid GUIDs
     *
     * @param expectedSize for the new set
     * @return a new set which holds GUIDs for connection removing
     */
    public static ServerSettingsFormSet withExpectedSize( int expectedSize ) {
        return new KolobokeServerSettingsFormSet( expectedSize );
    }

    public abstract IntCursor cursor();
    public abstract void clear();
    public abstract boolean add(int hash);
    public abstract boolean removeInt(int hash);
    public abstract boolean contains(int hash);

}
