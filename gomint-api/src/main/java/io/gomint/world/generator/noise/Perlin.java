/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.noise;

import io.gomint.util.random.FastRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Perlin extends Noise {

    public Perlin( FastRandom random, double octaves, double persistence ) {
        this( random, octaves, persistence, 1 );
    }

    public Perlin( FastRandom random, double octaves, double persistence, double expansion ) {
        this.octaves = octaves;
        this.persistence = persistence;
        this.expansion = expansion;
        this.offsetX = random.nextFloat() * 256;
        this.offsetY = random.nextFloat() * 256;
        this.offsetZ = random.nextFloat() * 256;
        this.perm = new int[512];

        for ( int i = 0; i < 256; ++i ) {
            this.perm[i] = random.nextInt( 256 );
        }

        for ( int i = 0; i < 256; ++i ) {
            int pos = random.nextInt( 256 - i ) + i;
            int old = this.perm[i];
            this.perm[i] = this.perm[pos];
            this.perm[pos] = old;
            this.perm[i + 256] = this.perm[i];
        }
    }

    @Override
    public double getNoise2D( double x, double y ) {
        return this.getNoise3D( x, y, 0 );
    }

    @Override
    public double getNoise3D( double x, double y, double z ) {
        x += this.offsetX;
        y += this.offsetY;
        z += this.offsetZ;

        int floorX = (int) x;
        int floorY = (int) y;
        int floorZ = (int) z;

        int cappedX = floorX & 0xFF;
        int cappedY = floorY & 0xFF;
        int cappedZ = floorZ & 0xFF;

        x -= floorX;
        y -= floorY;
        z -= floorZ;

        // Fade curves
        double fX = x * x * x * ( x * ( x * 6 - 15 ) + 10 );
        double fY = y * y * y * ( y * ( y * 6 - 15 ) + 10 );
        double fZ = z * z * z * ( z * ( z * 6 - 15 ) + 10 );

        //Cube corners
        int a = this.perm[cappedX] + cappedY;
        int b = this.perm[cappedX + 1] + cappedY;

        int aa = this.perm[a] + cappedZ;
        int ab = this.perm[a + 1] + cappedZ;
        int ba = this.perm[b] + cappedZ;
        int bb = this.perm[b + 1] + cappedZ;

        double aa1 = grad( this.perm[aa], x, y, z );
        double ba1 = grad( this.perm[ba], x - 1, y, z );
        double ab1 = grad( this.perm[ab], x, y - 1, z );
        double bb1 = grad( this.perm[bb], x - 1, y - 1, z );
        double aa2 = grad( this.perm[aa + 1], x, y, z - 1 );
        double ba2 = grad( this.perm[ba + 1], x - 1, y, z - 1 );
        double ab2 = grad( this.perm[ab + 1], x, y - 1, z - 1 );
        double bb2 = grad( this.perm[bb + 1], x - 1, y - 1, z - 1 );

        double xLerp11 = aa1 + fX * ( ba1 - aa1 );
        double zLerp1 = xLerp11 + fY * ( ab1 + fX * ( bb1 - ab1 ) - xLerp11 );
        double xLerp21 = aa2 + fX * ( ba2 - aa2 );
        return zLerp1 + fZ * ( xLerp21 + fY * ( ab2 + fX * ( bb2 - ab2 ) - xLerp21 ) - zLerp1 );
    }

}
