package io.gomint.world.generator.integrated;

import io.gomint.math.BlockPosition;
import io.gomint.world.Chunk;
import io.gomint.world.World;
import io.gomint.world.generator.ChunkGenerator;
import io.gomint.world.generator.GeneratorContext;

/**
 * @author geNAZt
 * @version 1.0
 */
public class VoidGenerator extends ChunkGenerator {

    /**
     * Create a new chunk generator
     *
     * @param world   for which this generator should generate chunks
     * @param context with which this generator should generate chunks
     */
    public VoidGenerator( World world, GeneratorContext context ) {
        super( world, context );
    }

    @Override
    public Chunk generate( int x, int z ) {
        return this.world.generateEmptyChunk( x, z );
    }

    @Override
    public BlockPosition getSpawnPoint() {
        return new BlockPosition( 0, 20, 0 );
    }

    @Override
    public void populate( Chunk chunk ) {

    }

}
