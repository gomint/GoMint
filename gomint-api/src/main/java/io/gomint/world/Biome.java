/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world;

import io.gomint.GoMint;
import io.gomint.math.MathUtils;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockGrassBlock;
import io.gomint.world.block.BlockGravel;
import io.gomint.world.generator.DefinedBlocks;
import io.gomint.world.generator.object.BirchTree;
import io.gomint.world.generator.object.OakTree;
import io.gomint.world.generator.populator.Populator;
import io.gomint.world.generator.populator.TallGrassPopulator;
import io.gomint.world.generator.populator.TreePopulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author BlackyPaw
 * @version 1.0
 * @stability 0
 */
public enum Biome {

    // ==================================== BIOMES ==================================== //
    OCEAN( 0, "Ocean", 0.5D, 0.5D, 46, 58 ) {
        @Override
        public List<Block> ground() {
            return Collections.unmodifiableList( Arrays.asList(
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class )
            ) );
        }

        @Override
        public List<Populator> populators() {
            TallGrassPopulator populator = new TallGrassPopulator();
            populator.baseAmount( 5 );
            return Collections.singletonList( populator );
        }
    },
    PLAINS( 1, "Plains", 0.8D, 0.4D, 63, 68 ) {
        @Override
        public List<Block> ground() {
            return Collections.unmodifiableList( Arrays.asList(
                GoMint.instance().createBlock( BlockGrassBlock.class ),
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT
            ) );
        }

        @Override
        public List<Populator> populators() {
            TallGrassPopulator populator = new TallGrassPopulator();
            populator.baseAmount( 12 );
            return Collections.singletonList( populator );
        }
    },
    DESERT( 2, "Desert", 2.0D, 0.0D ),
    EXTREME_HILLS( 3, "Extreme Hills", 0.2D, 0.3D ),
    FOREST( 4, "Forest", 0.7D, 0.8D, 63, 81 ) {
        @Override
        public List<Block> ground() {
            return Collections.unmodifiableList( Arrays.asList(
                GoMint.instance().createBlock( BlockGrassBlock.class ),
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT
            ) );
        }

        @Override
        public List<Populator> populators() {
            TallGrassPopulator populator = new TallGrassPopulator();
            populator.baseAmount( 3 );

            TreePopulator treePopulator = new TreePopulator( new OakTree() );
            treePopulator.baseAmount( 5 );
            return Collections.unmodifiableList( Arrays.asList( populator, treePopulator ) );
        }
    },
    TAIGA( 5, "Taiga", 0.05D, 0.8D ),
    SWAMPLAND( 6, "Swampland", 0.8D, 0.9D ) {
        @Override
        public int colorRGB(boolean grass, int height ) {
            // TODO: Implement Perlin noise!
            return 0x4C763C;
        }
    },
    RIVER( 7, "River", 0.5D, 0.5D, 58, 62 ) {
        @Override
        public List<Block> ground() {
            return Collections.unmodifiableList( Arrays.asList(
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT
            ) );
        }

        @Override
        public List<Populator> populators() {
            TallGrassPopulator populator = new TallGrassPopulator();
            populator.baseAmount( 5 );
            return Collections.singletonList( populator );
        }
    },
    NETHER( 8, "Nether", 2.0D, 0.0D ),
    END( 9, "End", 0.5D, 0.5D ),
    FROZEN_OCEAN( 10, "Frozen Ocean", 0.0D, 0.5D ),
    FROZEN_RIVER( 11, "Frozen River", 0.0D, 0.5D ),
    ICE_PLAINS( 12, "Ice Plains", 0.0D, 0.5D ),
    ICE_MOUNTAINS( 13, "Ice Mountains", 0.0D, 0.5D ),
    MUSHROOM_ISLAND( 14, "Mushroom Island", 0.9D, 1.0D ),
    MUSHROOM_ISLAND_SHORE( 15, "Mushroom Island Shore", 0.9D, 1.0D ),
    BEACH( 16, "Beach", 0.8D, 0.4D ),
    DESERT_HILLS( 17, "Desert Hills", 2.0D, 0.0D ),
    FOREST_HILLS( 18, "Forest Hills", 0.7D, 0.8D ),
    TAIGA_HILLS( 19, "Taiga Hills", 0.2D, 0.7D ),
    EXTREME_HILLS_EDGE( 20, "Extreme Hills Edge", 0.2D, 0.3D ),
    JUNGLE( 21, "Jungle", 1.2D, 0.9D ),
    JUNGLE_HILLS( 22, "Jungle Hills", 1.2D, 0.9D ),
    JUNGLE_EDGE( 23, "Jungle Edge", 0.5D, 0.8D ),
    DEEP_OCEAN( 24, "Deep Ocean", 0.5D, 0.5D ),
    STONE_BEACH( 25, "Stone Beach", 0.2D, 0.3D ),
    COLD_BEACH( 26, "Cold Beach", 0.5D, 0.3D ),
    BIRCH_FOREST( 27, "Birch Forest", 0.6D, 0.6D, 63, 81 ) {
        @Override
        public List<Block> ground() {
            return Collections.unmodifiableList( Arrays.asList(
                GoMint.instance().createBlock( BlockGrassBlock.class ),
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT,
                DefinedBlocks.DIRT
            ) );
        }

        @Override
        public List<Populator> populators() {
            TallGrassPopulator populator = new TallGrassPopulator();
            populator.baseAmount( 3 );

            TreePopulator treePopulator = new TreePopulator( new BirchTree() );
            treePopulator.baseAmount( 5 );
            return Collections.unmodifiableList( Arrays.asList( populator, treePopulator ) );
        }
    },
    BIRCH_FOREST_HILLS( 28, "Birch Forest Hills", 0.6D, 0.6D ),
    ROOFED_FOREST( 29, "Roofed Forest", 0.7D, 0.8D ) {
        @Override
        public int colorRGB(boolean grass, int height ) {
            int color = super.colorRGB( grass, height );
            return ( grass ? ( ( ( color & 0xFEFEFE ) + ROOFED_FOREST_MODIFIER ) / 2 ) : color );
        }
    },
    COlD_TAIGA( 30, "Cold Taiga", -0.5D, 0.4D ),
    COLD_TAIGA_HILLS( 31, "Cold Taiga Hills", -0.5D, 0.4D ),
    MEGA_TAIGA( 32, "Mega Taiga", 0.3D, 0.8D ) {
        @Override
        public int colorRGB(boolean grass, int height ) {
            return ( grass ? MESA_GRASS_COLOR : MESA_FOLIAGE_COLOR );
        }
    },
    MEGA_TAIGA_HILLS( 33, "Mega Taiga Hills", 0.3D, 0.8D ) {
        @Override
        public int colorRGB(boolean grass, int height ) {
            return ( grass ? MESA_GRASS_COLOR : MESA_FOLIAGE_COLOR );
        }
    },
    EXTREME_HILLS_PLUS( 34, "Extreme Hills+", 0.2D, 0.3D ),
    SAVANNA( 35, "Savanna", 1.2D, 0.0D ),
    SAVANNA_PLATEAU( 36, "Savanna Plateau", 1.0D, 0.0D ),
    MESA( 37, "Mesa", 2.0D, 0.0D ),
    MESA_PLATEAU_F( 38, "Mesa Plateau D", 2.0D, 0.0D ),
    MESA_PLATEAU( 39, "Mesa Plateau", 2.0D, 0.0D ),
    DEEP_COLD_OCEAN( 45, "Deep Cold Ocean", -0.5D, 0.5D, 46, 58 ) {
        @Override
        public List<Block> ground() {
            return Collections.unmodifiableList( Arrays.asList(
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class ),
                GoMint.instance().createBlock( BlockGravel.class )
            ) );
        }

        @Override
        public List<Populator> populators() {
            TallGrassPopulator populator = new TallGrassPopulator();
            populator.baseAmount( 5 );
            return Collections.singletonList( populator );
        }
    },
    SUNFLOWER_PLAINS( 51, "Sunflower Plains", 0.8D, 0.4D ),
    DESERT_M( 58, "Desert M", 2.0D, 0.0D ),
    EXTREME_HILLS_M( 69, "Extreme Hills M", 0.2D, 0.3D ),
    FLOWER_FOREST( 59, "Flower Forest", 0.7D, 0.8D ),
    TAIGA_M( 60, "Taiga M", 0.5D, 0.8D ),
    SWAMPLAND_M( 52, "Swampland M", 0.8D, 0.9D ) {
        @Override
        public int colorRGB(boolean grass, int height ) {
            // TODO: Implement Perlin noise!
            return 0x4C763C;
        }
    },
    /*
"warm_ocean" 40
"deep_warm_ocean" 41
"lukewarm_ocean" 42
"deep_lukewarm_ocean" 43
"cold_ocean" 44
"deep_cold_ocean" 45
"frozen_ocean" 46
"deep_frozen_ocean" 47
"bamboo_jungle" 48
"bamboo_jungle_hills" 49
"redwood_taiga_mutated" 68
"redwood_taiga_hills_mutated" 71
"soulsand_valley" 72
"crimson_forest" 73
"warped_forest" 74
"basalt_deltas" 75
 */
    ICE_PLAINS_SPIKES( 53, "Ice Plains Spikes", 0.0D, 0.5D ),
    JUNGLE_M( 61, "Jungle M", 1.2D, 0.9D ),
    JUNGLE_EDGE_M( 62, "Jungle Edge M", 0.5D, 0.8D ),
    BIRCH_FOREST_M( 66, "Birch Forest M", 0.6D, 0.6D ),
    BIRCH_FOREST_HILLS_M( 67, "Birch Forest Hills M", 0.6D, 0.6D ),
    ROOFED_FOREST_M( 57, "Roofed Forest M", 0.7D, 0.8D ) {
        @Override
        public int colorRGB(boolean grass, int height ) {
            int color = super.colorRGB( grass, height );
            return ( grass ? ( ( ( color & 0xFEFEFE ) + ROOFED_FOREST_MODIFIER ) / 2 ) : color );
        }
    },
    COLD_TAIGA_M( 54, "Cold Taiga M", -0.5D, 0.4D ),
    EXTREME_HILLS_PLUS_M( 70, "Extreme Hills+ M", 0.2D, 0.3D ),
    SAVANNA_M( 55, "Savanna M", 1.2D, 0.0D ),
    SAVANNA_PLATEAU_M( 56, "Savanna Plateau M", 1.0D, 0.0D ),
    MESA_BRYCE( 63, "Mesa (Bryce)", 2.0D, 0.0D ) {
        @Override
        public int colorRGB(boolean grass, int height ) {
            return ( grass ? MESA_GRASS_COLOR : MESA_FOLIAGE_COLOR );
        }
    },
    MESA_PLATEAU_F_M( 64, "Mesa Plateau F M", 2.0D, 0.0D ) {
        @Override
        public int colorRGB(boolean grass, int height ) {
            return ( grass ? MESA_GRASS_COLOR : MESA_FOLIAGE_COLOR );
        }
    },
    MESA_PLATEAU_M( 65, "Mesa Plateau M", 2.0D, 0.0D ) {
        @Override
        public int colorRGB(boolean grass, int height ) {
            return ( grass ? MESA_GRASS_COLOR : MESA_FOLIAGE_COLOR );
        }
    };

    // ==================================== CONSTANTS ==================================== //
    public static final int ROOFED_FOREST_MODIFIER = 0x28340A;
    public static final int MESA_GRASS_COLOR = 0x90814D;
    public static final int MESA_FOLIAGE_COLOR = 0x9E814D;

    private static final double[] GRASS_INTERPOLATION_COLORS = new double[]{ 0.5D, 0.703125D, 0.58984375D,               // Bottom right corner
        0.74609375D, 0.71484375D, 0.33203125D,      // Bottom left corner
        0.27734375D, 0.80078125D, 0.19921875D       // Upper left corner
    };

    private static final double[] FOLIAGE_INTERPOLATION_COLORS = new double[]{ 0.375D, 0.62890625D, 0.48046875D,           // Bottom right corner
        0.6796875D, 0.640625D, 0.1640625D,          // Bottom left corner
        0.1015625D, 0.74609375D, 0.0D               // Upper left corner
    };

    // ==================================== FIELDS ==================================== //
    private static final Map<Integer, Biome> BIOMES_BY_ID = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(Biome.class);

    static {
        for ( Biome biome : Biome.values() ) {
            BIOMES_BY_ID.put( biome.id(), biome );
        }
    }

    private final int id;
    private final String name;
    private final double temperature;
    private final double downfall;

    private final int minElevation;
    private final int maxElevation;


    Biome( int id, String name, double temperature, double downfall ) {
        this( id, name, temperature, downfall, 58, 74 );
    }

    Biome( int id, String name, double temperature, double downfall, int minElevation, int maxElevation ) {
        this.id = id;
        this.name = name;
        this.temperature = temperature;
        this.downfall = MathUtils.clamp( downfall, 0.0D, 1.0D );
        this.minElevation = minElevation;
        this.maxElevation = maxElevation;
    }

    /**
     * Attempts to get a biome given its id.
     *
     * @param id The ID of the biome
     * @return The biome if found or null otherwise
     */
    public static Biome byId(int id ) {
        Biome biome = BIOMES_BY_ID.get( id );
        if (biome == null) {
            LOGGER.warn("Unknown biome: {}", id);
        }

        return biome;
    }

    /**
     * Gets the unique ID of the biome.
     *
     * @return The biome's unique ID
     */
    public int id() {
        return this.id;
    }

    /**
     * Gets the name of the biome.
     *
     * @return The biome's name
     */
    public String biomeName() {
        return this.name;
    }

    /**
     * Gets the biome's temperature.
     *
     * @return The biome's temperature
     */
    public double temperature() {
        return this.temperature;
    }

    /**
     * Gets the biome's downfall rate.
     *
     * @return The biome's downfall rate
     */
    public double downfall() {
        return this.downfall;
    }

    /**
     * Gets a biome's RGB color given the height of a block.
     * <p>
     * A biome's color is calculated using a linearly interpolating image called grass.png or foliage.png
     * depending on whether the color is to be applied on grass or other things such as leaves. The coordinates
     * to be used on that image are calculated by taking into account the temperature and the downfall
     * rate. The temperature decreases naturally by 0.00166667 every meter above the water level (=64).
     * The downfall value then gets multiplied by the temperature. Afterwards the temperature will resemble
     * the U-coordinate, the downfall value will resemble the V-coordinate.
     * </p>
     *
     * @param grass  Whether or not the color is to be applied on grass or not (foliage if set to false)
     * @param height The height of the block to get the biome's color for
     * @return An integer encoding the biome color
     */
    public int colorRGB(boolean grass, int height ) {
        // Calculate temperature and downfall rate:
        double temperature = MathUtils.clamp( this.temperature() - ( height > 64 ? ( height - 64 ) * 0.00166667D : 0.0D ), 0.0D, 1.0D );
        double tempDownfall = downfall * temperature;

        // Interpolate on triangle:
        double r = 0.0D, g = 0.0D, b = 0.0D;
        double[] lambda = new double[3];
        lambda[0] = tempDownfall;
        lambda[1] = temperature - tempDownfall;
        lambda[2] = 1.0D - temperature;

        double[] colors = ( grass ? GRASS_INTERPOLATION_COLORS : FOLIAGE_INTERPOLATION_COLORS );
        for ( int i = 0; i < 3; ++i ) {
            r += lambda[i] * colors[i * 3];
            g += lambda[i] * colors[i * 3 + 1];
            b += lambda[i] * colors[i * 3 + 2];
        }

        // Clamp resulting color:
        int ri = MathUtils.clamp( (int) ( r * 255.0D ), 0, 255 );
        int gi = MathUtils.clamp( (int) ( g * 255.0D ), 0, 255 );
        int bi = MathUtils.clamp( (int) ( b * 255.0D ), 0, 255 );
        return ( ri << 16 ) | ( gi << 8 ) | bi;
    }

    public int minElevation() {
        return this.minElevation;
    }

    public int maxElevation() {
        return this.maxElevation;
    }

    public List<Block> ground() {
        return null;
    }

    public List<Populator> populators() {
        return null;
    }

}
