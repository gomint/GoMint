/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.plugin.generator;

import io.gomint.GoMint;
import io.gomint.math.BlockPosition;
import io.gomint.world.Chunk;
import io.gomint.world.World;
import io.gomint.world.block.*;
import io.gomint.world.generator.ChunkGenerator;
import io.gomint.world.generator.GeneratorContext;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlotChunkGenerator extends ChunkGenerator {

    private BlockDirt dirt;
    private BlockBedrock bedrock;
    private BlockGrassBlock grassBlock;
    private BlockStoneBrick stoneBrick;
    private BlockFence fence;

    /**
     * Create a new chunk generator
     *
     * @param world   for which this generator should generate chunks
     * @param context with which this generator should generate chunks
     */
    public PlotChunkGenerator( World world, GeneratorContext context ) {
        super( world, context );

        this.dirt = GoMint.instance().createBlock( BlockDirt.class );
        this.bedrock = GoMint.instance().createBlock( BlockBedrock.class );
        this.grassBlock = GoMint.instance().createBlock( BlockGrassBlock.class );
        this.stoneBrick = GoMint.instance().createBlock( BlockStoneBrick.class );
        this.fence = GoMint.instance().createBlock( BlockFence.class );
    }

    @Override
    public Chunk generate( int x, int z ) {
        int xChunk = x << 4;
        int zChunk = z << 4;

        Chunk chunk = this.world.generateEmptyChunk( x, z );

        for ( int xBlock = 0; xBlock < 16; xBlock++ ) {
            for ( int zBlock = 0; zBlock < 16; zBlock++ ) {
                for ( int y = 0; y < 86; y++ ) {
                    if ( y < 1 ) {
                        chunk.setBlock( xBlock, y, zBlock, this.bedrock );
                    } else if ( y < 84 ) {
                        chunk.setBlock( xBlock, y, zBlock, this.dirt );
                    } else if ( y < 85 ){
                        int moduloX = Math.abs( xChunk + xBlock ) % 30;
                        int moduloZ = Math.abs( zChunk + zBlock ) % 30;

                        if ( ( moduloX >= 2 && moduloX <= 4 ) ||
                            ( moduloZ >= 2 && moduloZ <= 4 ) ) {
                            chunk.setBlock( xBlock, y, zBlock, this.stoneBrick );
                        } else {
                            chunk.setBlock( xBlock, y, zBlock, this.grassBlock );
                        }
                    } else {
                        int moduloX = Math.abs( xChunk + xBlock ) % 30;
                        int moduloZ = Math.abs( zChunk + zBlock ) % 30;

                        if ( ( ( moduloX == 2 || moduloX == 4 ) && moduloZ != 3 ) ||
                            ( ( moduloZ == 2 || moduloZ == 4 ) && moduloX != 3 ) ) {
                            chunk.setBlock( xBlock, y, zBlock, this.fence );
                        }
                    }
                }
            }
        }

        return chunk;
    }

    @Override
    public BlockPosition getSpawnPoint() {
        return new BlockPosition( 10, 85, 10 );
    }

    @Override
    public void populate( Chunk chunk ) {

    }

}
