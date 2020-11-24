/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.collection;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

public class FreezableSortedMap<K, V> extends Object2ObjectLinkedOpenHashMap<K, V> {

    private boolean cachedHashCode;
    private int hashCode;

    private boolean frozen;

    public FreezableSortedMap() {
        super();
    }

    @Override
    public V put(K k, V v) {
        if (this.frozen) {
            return this.get(k);
        }

        return super.put(k, v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        if (this.cachedHashCode) {
            return this.hashCode;
        }

        if (this.frozen) {
            this.hashCode = super.hashCode();
            this.cachedHashCode = true;
            return this.hashCode;
        }

        return super.hashCode();
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    @Override
    public String toString() {
        return "FreezableSortedMap{" +
            "cachedHashCode=" + cachedHashCode +
            ", hashCode=" + hashCode +
            ", frozen=" + frozen +
            '}';
    }

}
