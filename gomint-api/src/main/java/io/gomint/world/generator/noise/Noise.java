/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.noise;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class Noise {

    protected int[] perm;
    protected double offsetX = 0;
    protected double offsetY = 0;
    protected double offsetZ = 0;
    protected double octaves = 8;
    protected double persistence;
    protected double expansion;

    public static double grad( int hash, double x, double y, double z ) {
        hash &= 15;
        double u = hash < 8 ? x : y;
        double v = hash < 4 ? y : ( ( hash == 12 || hash == 14 ) ? x : z );
        return ( ( hash & 1 ) == 0 ? u : -u ) + ( ( hash & 2 ) == 0 ? v : -v );
    }

    public abstract double getNoise2D( double x, double z );

    public abstract double getNoise3D( double x, double y, double z );

    public double noise2D( double x, double z ) {
        return noise2D( x, z, false );
    }

    public double noise2D( double x, double z, boolean normalized ) {
        double result = 0;
        double amp = 1;
        double freq = 1;
        double max = 0;

        x *= this.expansion;
        z *= this.expansion;

        for ( int i = 0; i < this.octaves; ++i ) {
            result += this.getNoise2D( x * freq, z * freq ) * amp;
            max += amp;
            freq *= 2;
            amp *= this.persistence;
        }

        if ( normalized ) {
            result /= max;
        }

        return result;
    }

    public double noise3D( double x, double y, double z ) {
        return noise3D( x, y, z, false );
    }

    public double noise3D( double x, double y, double z, boolean normalized ) {
        double result = 0;
        double amp = 1;
        double freq = 1;
        double max = 0;

        x *= this.expansion;
        y *= this.expansion;
        z *= this.expansion;

        for ( int i = 0; i < this.octaves; ++i ) {
            result += this.getNoise3D( x * freq, y * freq, z * freq ) * amp;
            max += amp;
            freq *= 2;
            amp *= this.persistence;
        }

        if ( normalized ) {
            result /= max;
        }

        return result;
    }

    public void setOffset( double x, double y, double z ) {
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
    }


    private double linearLerp( double x, double x1, double x2, double q0, double q1 ) {
        return ( ( x2 - x ) / ( x2 - x1 ) ) * q0 + ( ( x - x1 ) / ( x2 - x1 ) ) * q1;
    }

    private double bilinearLerp( double x, double y, double q00, double q01, double q10, double q11, double x1, double x2, double y1, double y2 ) {
        double dx1 = ( ( x2 - x ) / ( x2 - x1 ) );
        double dx2 = ( ( x - x1 ) / ( x2 - x1 ) );

        return ( ( y2 - y ) / ( y2 - y1 ) ) * (
            dx1 * q00 + dx2 * q10
        ) + ( ( y - y1 ) / ( y2 - y1 ) ) * (
            dx1 * q01 + dx2 * q11
        );
    }

    public double[] getFastNoise1D( Noise noise, int xSize, int samplingRate, int x, int y, int z ) {
        if ( samplingRate == 0 ) {
            throw new IllegalArgumentException( "samplingRate cannot be 0" );
        }
        if ( xSize % samplingRate != 0 ) {
            throw new IllegalArgumentException( "xSize % samplingRate must return 0" );
        }
        double[] noiseArray = new double[xSize + 1];

        for ( int xx = 0; xx <= xSize; xx += samplingRate ) {
            noiseArray[xx] = noise.noise3D( xx + x, y, z );
        }

        for ( int xx = 0; xx < xSize; ++xx ) {
            if ( xx % samplingRate != 0 ) {
                int nx = xx / samplingRate * samplingRate;
                noiseArray[nx] = this.linearLerp( xx, nx, nx + samplingRate, noiseArray[nx], noiseArray[nx + samplingRate] );
            }
        }

        return noiseArray;
    }

    public double[][] getFastNoise2D( int xSize, int zSize, int samplingRate, int x, int y, int z, int xZoom, int zZoom ) {
        if ( samplingRate == 0 ) {
            throw new IllegalArgumentException( "samplingRate cannot be 0" );
        }
        if ( xSize % samplingRate != 0 ) {
            throw new IllegalArgumentException( "xSize % samplingRate must return 0" );
        }
        if ( zSize % samplingRate != 0 ) {
            throw new IllegalArgumentException( "zSize % samplingRate must return 0" );
        }

        double[][] noiseArray = new double[xSize + 1][zSize + 1];

        for ( int xx = 0; xx <= xSize; xx += samplingRate ) {
            noiseArray[xx] = new double[zSize + 1];
            for ( int zz = 0; zz <= zSize; zz += samplingRate ) {
                noiseArray[xx][zz] = this.noise3D( ( x + xx ) >> xZoom, y, ( z + zz ) >> zZoom );
            }
        }

        for ( int xx = 0; xx < xSize; ++xx ) {
            if ( xx % samplingRate != 0 ) {
                noiseArray[xx] = new double[zSize + 1];
            }

            for ( int zz = 0; zz < zSize; ++zz ) {
                if ( xx % samplingRate != 0 || zz % samplingRate != 0 ) {
                    int nx = xx / samplingRate * samplingRate;
                    int nz = zz / samplingRate * samplingRate;
                    noiseArray[xx][zz] = this.bilinearLerp(
                        xx, zz, noiseArray[nx][nz], noiseArray[nx][nz + samplingRate],
                        noiseArray[nx + samplingRate][nz], noiseArray[nx + samplingRate][nz + samplingRate],
                        nx, nx + samplingRate, nz, nz + samplingRate
                    );
                }
            }
        }
        return noiseArray;
    }

    public double[][] getFastNoise2D( int xSize, int zSize, int samplingRate, int x, int y, int z ) {
        return getFastNoise2D( xSize, zSize, samplingRate, x, y, z, 0, 0 );
    }

    public double[][][] getFastNoise3D( int xSize, int ySize, int zSize, int xSamplingRate, int ySamplingRate, int zSamplingRate, int x, int y, int z ) {
        if ( xSamplingRate == 0 ) {
            throw new IllegalArgumentException( "xSamplingRate cannot be 0" );
        }
        if ( zSamplingRate == 0 ) {
            throw new IllegalArgumentException( "zSamplingRate cannot be 0" );
        }
        if ( ySamplingRate == 0 ) {
            throw new IllegalArgumentException( "ySamplingRate cannot be 0" );
        }
        if ( xSize % xSamplingRate != 0 ) {
            throw new IllegalArgumentException( "xSize % xSamplingRate must return 0" );
        }
        if ( zSize % zSamplingRate != 0 ) {
            throw new IllegalArgumentException( "zSize % zSamplingRate must return 0" );
        }
        if ( ySize % ySamplingRate != 0 ) {
            throw new IllegalArgumentException( "ySize % ySamplingRate must return 0, got " + ySize + " for sample size " + ySamplingRate );
        }

        double[][][] noiseArray = new double[xSize + 1][zSize + 1][ySize + 1];
        for ( int xx = 0; xx <= xSize; xx += xSamplingRate ) {
            for ( int zz = 0; zz <= zSize; zz += zSamplingRate ) {
                for ( int yy = 0; yy <= ySize; yy += ySamplingRate ) {
                    noiseArray[xx][zz][yy] = this.noise3D( x + xx, y + yy, z + zz, true );
                }
            }
        }

        for ( int xx = 0; xx < xSize; ++xx ) {
            for ( int zz = 0; zz < zSize; ++zz ) {
                for ( int yy = 0; yy < ySize; ++yy ) {
                    if ( xx % xSamplingRate != 0 || zz % zSamplingRate != 0 || yy % ySamplingRate != 0 ) {
                        int nx = xx / xSamplingRate * xSamplingRate;
                        int ny = yy / ySamplingRate * ySamplingRate;
                        int nz = zz / zSamplingRate * zSamplingRate;

                        int nnx = nx + xSamplingRate;
                        int nny = ny + ySamplingRate;
                        int nnz = nz + zSamplingRate;

                        double dx1 = ( (double) ( nnx - xx ) / (double) ( nnx - nx ) );
                        double dx2 = ( (double) ( xx - nx ) / (double) ( nnx - nx ) );
                        double dy1 = ( (double) ( nny - yy ) / (double) ( nny - ny ) );
                        double dy2 = ( (double) ( yy - ny ) / (double) ( nny - ny ) );

                        noiseArray[xx][zz][yy] = ( (double) ( nnz - zz ) / (double) ( nnz - nz ) ) * (
                            dy1 * (
                                dx1 * noiseArray[nx][nz][ny] + dx2 * noiseArray[nnx][nz][ny]
                            ) + dy2 * (
                                dx1 * noiseArray[nx][nz][nny] + dx2 * noiseArray[nnx][nz][nny]
                            )
                        ) + ( (double) ( zz - nz ) / (double) ( nnz - nz ) ) * (
                            dy1 * (
                                dx1 * noiseArray[nx][nnz][ny] + dx2 * noiseArray[nnx][nnz][ny]
                            ) + dy2 * (
                                dx1 * noiseArray[nx][nnz][nny] + dx2 * noiseArray[nnx][nnz][nny]
                            )
                        );
                    }
                }
            }
        }

        return noiseArray;
    }

}
