package io.gomint.server.util.random;

import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;

import java.util.Iterator;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @param <V> type of value
 *            <p>
 *            This implementation takes in weighted items and does generate a weighted random value when asked
 *            via {@link #next()}.
 * @author geNAZt
 * @version 1.0
 */
public class WeightedRandom<V> {

    private final Random random;
    private final NavigableMap<Double, V> map = new TreeMap<>();
    private final Object2DoubleMap<V> weights = new Object2DoubleOpenHashMap<>();

    private boolean removed = false;
    private double total = 0;

    public WeightedRandom(Random random) {
        this.random = random;
    }

    /**
     * Add a new entry to the randomizer
     *
     * @param weight of the new item, needs to be positive
     * @param value  which should be added
     * @return this instance for chaining
     */
    public WeightedRandom<V> add(double weight, V value) {
        if (weight <= 0) {
            return this;
        }

        this.total += weight;
        this.map.put(this.total, value);
        this.weights.put(value, weight);
        return this;
    }

    /**
     * Get a random value out of this randomizer
     *
     * @return random value out of values inputted via {@link #add(double, Object)}
     */
    public V next() {
        if (this.removed) {
            this.recalc();
        }

        double value = this.random.nextDouble() * this.total;
        return this.map.higherEntry(value).getValue();
    }

    private void recalc() {
        this.map.clear();
        this.total = 0;

        for (Object2DoubleMap.Entry<V> entry : this.weights.object2DoubleEntrySet()) {
            this.total += entry.getDoubleValue();
            this.map.put(this.total, entry.getKey());
        }

        this.removed = false;
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public Iterator<V> iterator() {
        Iterator<V> it = this.map.values().iterator();
        final V[] current = (V[]) new Object[]{null};

        return new Iterator<V>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public V next() {
                return current[0] = it.next();
            }

            @Override
            public void remove() {
                it.remove();
                weights.removeDouble(current[0]);
                removed = true;
            }
        };
    }

}
