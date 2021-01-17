/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util.collection;

import io.gomint.taglib.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FixedReadOnlyMap extends ReadOnlyMap<String, Object> {

    private final String[] keys;
    private final Object[] values;

    private long hashKey;
    private long hashValue;

    public FixedReadOnlyMap(Set<Entry<String, Object>> entries) {
        this.keys = new String[entries.size()];
        this.values = new Object[entries.size()];

        // We need to intern the strings
        List<String> keysToSort = new ArrayList<>();
        for (Entry<String, Object> entry : entries) {
            keysToSort.add(entry.getKey().intern());
        }

        // Since those are interned its fasted to sort them by hashCode
        keysToSort.sort(Comparator.comparingInt(String::hashCode));

        int index = 0;
        for (String s : keysToSort) {
            this.keys[index++] = s;
        }

        for (Entry<String, Object> entry : entries) {
            int keyIndex = getKeyIndex(entry.getKey());
            if (keyIndex != -1) {
                this.values[keyIndex] = entry.getValue() instanceof String ? ((String) entry.getValue()).intern() : entry.getValue();
            }
        }

        // Since we have filled everything we can precalc the hashes
        for (int i = 0; i < this.keys.length; i++) {
            this.hashKey = 31 * this.hashKey + this.keys[i].hashCode();
            this.hashValue = 31 * this.hashValue + this.values[i].hashCode();
        }
    }

    private int getKeyIndex(String key) {
        String interned = key.intern();
        for (int i = 0; i < this.keys.length; i++) {
            if (this.keys[i] == interned) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int size() {
        return this.keys.length;
    }

    @Override
    public boolean isEmpty() {
        return size() != 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (!(key instanceof String)) {
            return false;
        }

        return getKeyIndex((String) key) != -1;
    }

    @Override
    public Object get(Object key) {
        if (!(key instanceof String)) {
            return null;
        }

        int keyIndex = getKeyIndex((String) key);
        if (keyIndex == -1) {
            return null;
        }

        return this.values[keyIndex];
    }

    @Override
    public boolean containsValue(Object value) {
        for (Object o : this.values) {
            if (value.equals(o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedReadOnlyMap that = (FixedReadOnlyMap) o;
        return hashKey == that.hashKey && hashValue == that.hashValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashKey, hashValue);
    }

    @Override
    public Set<String> keySet() {
        return new HashSet<>(Arrays.asList(this.keys));
    }

    public NBTTagCompound toNBT(String name) {
        NBTTagCompound compound = new NBTTagCompound(name);

        for (int i = 0; i < this.keys.length; i++) {
            compound.addValue(this.keys[i], this.values[i]);
        }

        return compound;
    }

}
