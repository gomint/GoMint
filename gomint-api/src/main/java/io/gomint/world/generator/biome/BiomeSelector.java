/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.biome;

import io.gomint.util.random.FastRandom;
import io.gomint.world.Biome;
import io.gomint.world.generator.noise.Simplex;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class BiomeSelector {

    private Simplex temperature;
    private Simplex downfall;

    private Biome[] lookup = new Biome[4096];

    public BiomeSelector( FastRandom random ) {
        this.temperature = new Simplex( random, 2, 0.0625, 0.001953125 );   // These two noises are different because the random seed changes!
        this.downfall = new Simplex( random, 2, 0.0625, 0.001953125 );

        for ( int i = 0; i < 64; ++i ) {
            for ( int j = 0; j < 64; ++j ) {
                Biome biome = this.lookup( i / (double) 63, j / (double) 63, null );
                this.lookup[i + ( j << 6 )] = biome;
            }
        }
    }

    public abstract Biome lookup( double temperature, double downfall, Biome current );

    public Biome select( int x, int z, Biome current ) {
        int temp = (int) ( ( ( this.temperature.noise2D( x, z, true ) + 1 ) / 2 ) * 63 );
        int down = (int) ( ( ( this.downfall.noise2D( x, z, true ) + 1 ) / 2 ) * 63 );

        return this.lookup[temp + ( down << 6 )];
    }

}
