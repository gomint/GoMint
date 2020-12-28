/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.collection;

public class SingleKeyChangeMap extends ReadOnlyMap<String, Object> {

    private final FixedReadOnlyMap backing;
    private final int[] keyCode;
    private final Object value;

    public SingleKeyChangeMap(FixedReadOnlyMap backing, String[] key, Object value) {
        this.backing = backing;
        this.keyCode = new int[key.length];
        this.value = value;

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

}
