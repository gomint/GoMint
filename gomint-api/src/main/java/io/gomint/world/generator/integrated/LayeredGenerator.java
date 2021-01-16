/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.generator.integrated;

import io.gomint.GoMint;
import io.gomint.math.BlockPosition;
import io.gomint.world.Chunk;
import io.gomint.world.World;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockBedrock;
import io.gomint.world.block.BlockDirt;
import io.gomint.world.block.BlockGrassBlock;
import io.gomint.world.generator.ChunkGenerator;
import io.gomint.world.generator.GeneratorContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LayeredGenerator extends ChunkGenerator {

    public static final String NAME = "flat";
    private List<Block> layers;

    /**
     * Create a new chunk generator
     *
     * @param world   for which this generator should generate chunks
     * @param context with which this generator should generate chunks
     */
    public LayeredGenerator( World world, GeneratorContext context ) {
        super( world, context );

        this.layers = new ArrayList<>();

        // Is there a pre configured layer context?
        if ( context.contains( "amountOfLayers" ) ) {
            int amountOfLayers = context.get( "amountOfLayers" );
            for ( int i = 0; i < amountOfLayers; i++ ) {
                this.layers.add( context.get( "layer." + i ) );
            }
        } else {
            // Default layers are grass, dirt, dirt, bedrock
            Block bedRock = GoMint.instance().createBlock( BlockBedrock.class );
            Block dirt = GoMint.instance().createBlock( BlockDirt.class );
            Block grass = GoMint.instance().createBlock( BlockGrassBlock.class );

            // They need to be stored from bottom up
            this.layers.add( bedRock );
            this.layers.add( dirt );
            this.layers.add( dirt );
            this.layers.add( grass );
        }
    }

    public List<Block> layers() {
        return layers;
    }

    @Override
    public Chunk generate( int x, int z ) {
        Chunk chunk = this.world.generateEmptyChunk( x, z );

        // Layers are starting from the bottom
        int y = 0;
        for ( Block layer : this.layers ) {
            for ( int xBlock = 0; xBlock < 16; xBlock++ ) {
                for ( int zBlock = 0; zBlock < 16; zBlock++ ) {
                    chunk.block( xBlock, y, zBlock, layer );
                }
            }

            y++;
        }

        return chunk;
    }

    @Override
    public BlockPosition spawnPoint() {
        return new BlockPosition( (int) ( Math.random() * 2000 ), this.layers.size(), (int) ( Math.random() * 2000 ) );
    }

    @Override
    public void populate( Chunk chunk ) {

    }

}
