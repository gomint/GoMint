/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.collection;

import java.util.Arrays;
import java.util.Set;

public class SingleKeyChangeMap extends ReadOnlyMap<String, Object> {

    private final FixedReadOnlyMap backing;
    private final int[] keyCode;
    private final String[] keys;
    private final Object value;

    public SingleKeyChangeMap(FixedReadOnlyMap backing, String[] key, Object value) {
        this.backing = backing;
        this.keyCode = new int[key.length];
        this.value = value;
        this.keys = key;

        for (int i = 0; i < key.length; i++) {
            this.keyCode[i] = key[i].hashCode();
        }
    }

    @Override
    public Object get(Object k) {
        if (k != null) {
            int hashCodeK = k.hashCode();
            for (int hashCode : this.keyCode) {
                if (hashCode == hashCodeK) {
                    return this.value;
                }
            }
        }

        return this.backing.get(k);
    }

    @Override
    public int size() {
        return this.backing.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object k) {
        if (k != null) {
            int hashCodeK = k.hashCode();
            for (int hashCode : this.keyCode) {
                if (hashCode == hashCodeK) {
                    return true;
                }
            }
        }

        return this.backing.containsKey(k);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.backing.containsValue(value);
    }

    @Override
    public Set<String> keySet() {
        Set<String> keys = this.backing.keySet();
        keys.addAll(Arrays.asList(this.keys));
        return keys;
    }

}
