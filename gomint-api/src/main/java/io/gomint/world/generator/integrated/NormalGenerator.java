/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.integrated;

import io.gomint.GoMint;
import io.gomint.math.BlockPosition;
import io.gomint.util.random.FastRandom;
import io.gomint.world.Biome;
import io.gomint.world.Chunk;
import io.gomint.world.World;
import io.gomint.world.WorldLayer;
import io.gomint.world.block.BlockCoalOre;
import io.gomint.world.block.BlockDiamondOre;
import io.gomint.world.block.BlockDirt;
import io.gomint.world.block.BlockGoldOre;
import io.gomint.world.block.BlockGravel;
import io.gomint.world.block.BlockIronOre;
import io.gomint.world.block.BlockLapisLazuliOre;
import io.gomint.world.block.BlockRedstoneOre;
import io.gomint.world.generator.ChunkGenerator;
import io.gomint.world.generator.DefinedBlocks;
import io.gomint.world.generator.GeneratorContext;
import io.gomint.world.generator.biome.BiomeSelector;
import io.gomint.world.generator.noise.Simplex;
import io.gomint.world.generator.object.OreType;
import io.gomint.world.generator.populator.GroundPopulator;
import io.gomint.world.generator.populator.OrePopulator;
import io.gomint.world.generator.populator.Populator;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
public class NormalGenerator extends ChunkGenerator {

    private static final int SMOOTH_SIZE = 2;
    private static final double[][] GAUSSIAN = new double[SMOOTH_SIZE * 2 + 1][SMOOTH_SIZE * 2 + 1];

    public static final String NAME = "normal";

    static {
        double bellSize = 1 / (double) SMOOTH_SIZE;
        double bellHeight = 2 * (double) SMOOTH_SIZE;

        for ( int sx = -SMOOTH_SIZE; sx <= SMOOTH_SIZE; ++sx ) {
            for ( int sz = -SMOOTH_SIZE; sz <= SMOOTH_SIZE; ++sz ) {
                double bx = bellSize * sx;
                double bz = bellSize * sz;
                GAUSSIAN[sx + SMOOTH_SIZE][sz + SMOOTH_SIZE] = bellHeight * Math.exp( -( bx * bx + bz * bz ) / (double) 2 );
            }
        }
    }

    private List<Populator> populators = new ArrayList<>();
    private List<Populator> generationPopulators = new ArrayList<>();
    private int waterHeight = 62;

    private FastRandom random;
    private Simplex noise;
    private long seed;

    private BiomeSelector selector;

    /**
     * Create a new chunk generator
     *
     * @param world   for which this generator should generate chunks
     * @param context with which this generator should generate chunks
     */
    public NormalGenerator( World world, GeneratorContext context ) {
        super( world, context );

        // Check if we have a seed
        if ( context.contains( "seed" ) ) {
            this.seed = context.get( "seed" );
        } else {
            this.seed = ThreadLocalRandom.current().nextLong();
            context.put( "seed", this.seed );
        }

        this.random = new FastRandom( this.seed );
        this.noise = new Simplex( this.random, 4, 0.25, 0.03125 );

        // Reset random after noise init
        this.random.setSeed( this.seed );

        this.selector = new BiomeSelector( this.random ) {
            @Override
            public Biome lookup( double temperature, double downfall, Biome current ) {
                if ( downfall < 0.25 ) {
                    if ( temperature < 0.7 ) {
                        return Biome.OCEAN;
                    } else {
                        return Biome.RIVER;
                    }
                } else if ( downfall < 0.6 ) {
                    return Biome.PLAINS;
                } else if ( downfall < 0.8 ) {
                    if ( temperature < 0.75 ) {
                        return Biome.FOREST;
                    } else {
                        return Biome.BIRCH_FOREST;
                    }
                } else {
                    return Biome.RIVER;
                }
            }
        };

        // Add needed populators
        this.generationPopulators.add( new GroundPopulator() );

        this.populators.add( new OrePopulator( new OreType[]{
            new OreType( GoMint.instance().createBlock( BlockCoalOre.class ), 20, 16, 0, 128 ),
            new OreType( GoMint.instance().createBlock( BlockIronOre.class ), 20, 8, 0, 64 ),
            new OreType( GoMint.instance().createBlock( BlockRedstoneOre.class ), 8, 7, 0, 16 ),
            new OreType( GoMint.instance().createBlock( BlockLapisLazuliOre.class ), 1, 6, 0, 32 ),
            new OreType( GoMint.instance().createBlock( BlockGoldOre.class ), 2, 8, 0, 32 ),
            new OreType( GoMint.instance().createBlock( BlockDiamondOre.class ), 1, 7, 0, 16 ),
            new OreType( GoMint.instance().createBlock( BlockDirt.class ), 20, 32, 0, 128 ),
            new OreType( GoMint.instance().createBlock( BlockGravel.class ), 10, 16, 0, 128 )
        } ) );
    }

    @Override
    public Chunk generate( int chunkX, int chunkZ ) {
        this.random.setSeed( 0xdeadbeef ^ ( chunkX << 8 ) ^ chunkZ ^ this.seed );

        Chunk chunk = this.world.generateEmptyChunk( chunkX, chunkZ );

        Long2ObjectMap<Biome> biomeCache = new Long2ObjectOpenHashMap<>();
        double[][] smoothHeight = new double[16][16];
        double[][] minHeight = new double[16][16];

        double noiseMax = 0;
        for ( int x = 0; x < 16; ++x ) {
            for ( int z = 0; z < 16; ++z ) {
                // Pick biome and calc the weight of it to check for height limits of the biome
                double minSum = 0;
                double maxSum = 0;
                double weightSum = 0;

                Biome biome = this.pickBiome( chunkX * 16 + x, chunkZ * 16 + z ); // For testing we only use plains now
                chunk.setBiome( x, z, biome );

                for ( int sx = -SMOOTH_SIZE; sx <= SMOOTH_SIZE; ++sx ) {
                    for ( int sz = -SMOOTH_SIZE; sz <= SMOOTH_SIZE; ++sz ) {
                        double weight = GAUSSIAN[sx + SMOOTH_SIZE][sz + SMOOTH_SIZE];

                        Biome adjacent = null;
                        if ( sx == 0 && sz == 0 ) {
                            adjacent = biome;
                        } else {
                            long index = this.chunkHash( chunkX * 16 + x + sx, chunkZ * 16 + z + sz );

                            if ( biomeCache.containsKey( index ) ) {
                                adjacent = biomeCache.get( index );
                            } else {
                                biomeCache.put( index, adjacent = this.pickBiome( chunkX * 16 + x + sx, chunkZ * 16 + z + sz ) );
                            }
                        }

                        minSum += ( adjacent.getMinElevation() - 1 ) * weight;
                        maxSum += adjacent.getMaxElevation() * weight;
                        weightSum += weight;
                    }
                }

                minSum /= weightSum;
                maxSum /= weightSum;

                if ( maxSum > noiseMax ) {
                    noiseMax = maxSum;
                }

                smoothHeight[x][z] = ( maxSum - minSum ) / 2;
                minHeight[x][z] = minSum;
            }
        }

        int correctedMax = (int) noiseMax + 1;
        correctedMax += 8 - ( correctedMax % 8 );
        if ( correctedMax > 256 ) {
            correctedMax = 256;
        }

        double[][][] noiseValues = this.noise.getFastNoise3D( 16, correctedMax, 16, 4, 8, 4, chunkX * 16, 0, chunkZ * 16 );

        for ( int x = 0; x < 16; x++ ) {
            for ( int z = 0; z < 16; z++ ) {
                // We currently only care about noise generation with stones
                for ( int y = 0; y < correctedMax; ++y ) {
                    if ( y == 0 ) {
                        chunk.setBlock( x, y, z, WorldLayer.NORMAL, DefinedBlocks.BEDROCK);
                        continue;
                    }

                    double noiseValue = noiseValues[x][z][y] - 1 / smoothHeight[x][z] * ( y - smoothHeight[x][z] - minHeight[x][z] );
                    if ( noiseValue > 0 ) {
                        chunk.setBlock( x, y, z, DefinedBlocks.STONE );
                    } else if ( y <= this.waterHeight ) {
                        chunk.setBlock( x, y, z, DefinedBlocks.WATER );
                    }
                }
            }
        }

        // Let the generation populators work
        for ( Populator popluator : this.generationPopulators ) {
            popluator.populate( this.world, chunk, this.random );
        }

        return chunk;
    }

    @Override
    public void populate( Chunk chunk ) {
        // Reset the seed
        this.random.setSeed( 0xdeadbeef ^ ( chunk.getX() << 8 ) ^ chunk.getZ() ^ this.seed );

        // Let the normal populators work
        for ( Populator populator : this.populators ) {
            populator.populate( this.world, chunk, this.random );
        }

        // Let the biome populators work
        Biome toWorkFor = chunk.getBiome( 7, 7 );
        List<Populator> biomePopulators = toWorkFor.getPopulators();
        if ( biomePopulators != null ) {
            for ( Populator populator : biomePopulators ) {
                populator.populate( this.world, chunk, this.random );
            }
        }
    }

    private Biome pickBiome( int x, int z ) {
        long hash = x * 2345803 ^ z * 9236449 ^ this.seed;
        hash *= hash + 223;

        byte xNoise = (byte) ( hash >> 20 & 3 );
        byte zNoise = (byte) ( hash >> 22 & 3 );

        if ( xNoise == 3 ) {
            xNoise = 1;
        }

        if ( zNoise == 3 ) {
            zNoise = 1;
        }

        return this.selector.select( x + xNoise - 1, z + zNoise - 1, null );
    }

    private long chunkHash( int x, int z ) {
        return ( (long) x << 32 ) + z - Integer.MIN_VALUE;
    }

    @Override
    public BlockPosition getSpawnPoint() {
        return new BlockPosition( 150, 75, 150 );
    }

}
