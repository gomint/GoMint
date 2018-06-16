package io.gomint.world.generator;

import io.gomint.math.BlockPosition;
import io.gomint.world.Chunk;
import io.gomint.world.World;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ChunkGenerator {

    protected World world;
    @Getter
    protected GeneratorContext context;

    /**
     * Create a new chunk generator
     *
     * @param world for which this generator should generate chunks
     * @param context with which this generator should generate chunks
     */
    public ChunkGenerator( World world, GeneratorContext context ) {
        this.world = world;
        this.context = context;
    }

    /**
     * Generate a chunk at the given coordinates. You have to return a fully built chunk, you can request one with
     * {@link io.gomint.world.World#generateEmptyChunk(int,int)}
     *
     * @param x coordinate of the chunk
     * @param z coordinate of the chunk
     * @return proper populated chunk
     */
    public abstract Chunk generate( int x, int z );

    /**
     * When you generate a new world using this chunk generator this method returns the worlds spawn. At this stage
     * no blocks are loaded.
     *
     * @return block position of the spawn of this new world
     */
    public abstract BlockPosition getSpawnPoint();

    /**
     * Populate (generate additional objects) for the given chunk
     *
     * @param chunk which should be populated
     */
    public abstract void populate( Chunk chunk );

}
