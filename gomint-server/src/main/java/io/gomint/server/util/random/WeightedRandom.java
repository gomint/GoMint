package io.gomint.server.util.random;

import io.gomint.util.random.FastRandom;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 * @param <V> type of value
 *
 * This implementation takes in weighted items and does generate a weighted random value when asked
 * via {@link #next()}.
 */
public class WeightedRandom<V> {

    private final NavigableMap<Double, V> map = new TreeMap<>();
    private double total = 0;

    /**
     * Add a new entry to the randomizer
     *
     * @param weight of the new item, needs to be positive
     * @param value which should be added
     * @return this instance for chaining
     */
    public WeightedRandom<V> add( double weight, V value ) {
        if ( weight <= 0 ) {
            return this;
        }

        this.total += weight;
        this.map.put( this.total, value );
        return this;
    }

    /**
     * Get a random value out of this randomizer
     *
     * @return random value out of values inputted via {@link #add(double, Object)}
     */
    public V next() {
        double value = ThreadLocalRandom.current().nextDouble() * this.total;
        return this.map.higherEntry( value ).getValue();
    }

}
