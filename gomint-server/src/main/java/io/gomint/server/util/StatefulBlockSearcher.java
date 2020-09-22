package io.gomint.server.util;

import io.gomint.math.BlockPosition;
import io.gomint.world.World;
import io.gomint.world.block.Block;

import java.util.function.Predicate;

/**
 * @author geNAZt
 * @version 1.0
 */
public class StatefulBlockSearcher {

    // This value stores the first position found
    private BlockPosition foundPosition;

    // Predicate which decides if we hit a block
    private Predicate<Block> predicate;

    // Start and end
    private BlockPosition start, end;

    // World we search in
    private World world;

    /**
     * Construct a new StatefulBlockSearcher
     *
     * @param world in which we want to search
     * @param start from where we want to search
     * @param end until we want to search
     * @param predicate to use when testing if the block is correct
     */
    public StatefulBlockSearcher( World world, BlockPosition start, BlockPosition end, Predicate<Block> predicate ) {
        this.world = world;
        this.start = start;
        this.end = end;
        this.predicate = predicate;
    }

    /**
     * Validate the block and search a new one if needed
     *
     * @return the found block position
     */
    public BlockPosition validate() {
        // Check if we have a result
        if ( this.foundPosition == null ) {
            // Search again
            this.iterate();
            return this.foundPosition;
        }

        // Check if the found block is still valid
        Block block = this.world.getBlockAt( this.foundPosition );
        if ( !this.predicate.test( block ) ) {
            this.foundPosition = null;

            // Iterate
            this.iterate();

            return this.foundPosition;
        }

        return this.foundPosition;
    }

    private void iterate() {
        // Now iterate until we hit the end
        for ( int x = this.start.getX(); x <= this.end.getX(); x++ ) {
            for ( int y = this.start.getY(); y <= this.end.getY(); y++ ) {
                for ( int z = this.start.getZ(); z <= this.end.getZ(); z++ ) {
                    // Get the block instance
                    Block block = this.world.getBlockAt( x, y, z );
                    if ( this.predicate.test( block ) ) {
                        this.foundPosition = block.getPosition();
                        return;
                    }
                }
            }
        }
    }

}
