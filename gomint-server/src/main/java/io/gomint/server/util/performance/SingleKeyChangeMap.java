/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.performance;

import io.gomint.server.util.collection.FreezableSortedMap;

import java.util.Objects;

public class SingleKeyChangeMap<K, V> extends ReadOnlyMap<K, V> {

    private final FreezableSortedMap<K, V> backing;
    private final int keyCode;
    private final V value;

    public SingleKeyChangeMap(FreezableSortedMap<K, V> backing, K key, V value) {
        this.backing = backing;
        this.keyCode = key.hashCode();
        this.value = value;
    }

    @Override
    public V get(Object k) {
        if (k != null && k.hashCode() == this.keyCode) {
            return this.value;
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
        if (k != null && k.hashCode() == this.keyCode) {
            return true;
        }

        return this.backing.containsKey(k);
    }

}
