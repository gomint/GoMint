package io.gomint.server.world.generator;

import io.gomint.server.world.ChunkAdapter;
import io.gomint.server.world.WorldAdapter;
import io.gomint.server.world.anvil.AnvilChunkAdapter;
import io.gomint.server.world.anvil.AnvilWorldAdapter;
import io.gomint.server.world.leveldb.LevelDBChunkAdapter;
import io.gomint.server.world.leveldb.LevelDBWorldAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public abstract class ChunkGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger( ChunkGenerator.class );

    protected WorldAdapter worldAdapter;

    public ChunkGenerator( WorldAdapter worldAdapter ) {
        this.worldAdapter = worldAdapter;
    }

    public abstract ChunkAdapter generate( int x, int z );

    /**
     * Generate a chunk in the correct adapter format
     *
     * @param x position of the chunk
     * @param z position of the chunk
     * @return a chunk with no data in it (all blocks air)
     */
    public ChunkAdapter generateEmpty( int x, int z ) {
        if ( this.worldAdapter instanceof LevelDBWorldAdapter ) {
            return new LevelDBChunkAdapter( this.worldAdapter, x, z );
        } else if ( this.worldAdapter instanceof AnvilWorldAdapter ) {
            return new AnvilChunkAdapter( (AnvilWorldAdapter) this.worldAdapter, x, z );
        }

        LOGGER.warn( "Creating temporary in memory chunk since the world adapter is from unknown implementation" );
        return new ChunkAdapter( this.worldAdapter, x, z );
    }

}
