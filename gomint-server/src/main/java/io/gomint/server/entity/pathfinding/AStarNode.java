package io.gomint.server.entity.pathfinding;

import io.gomint.math.BlockPosition;

/**
 * Very simplistic node structure as required by the A* algorithm for its
 * set of nodes that have been discovered.
 *
 * @author BlackyPaw
 * @version 1.0
 */
class AStarNode implements Comparable<AStarNode> {

    private final BlockPosition blockPosition;
    private double g;
    private double f;
    private int k;
    private AStarNode predecessor;

    /**
     * Constructs a new A* node holding a specific block's position.
     *
     * @param blockPosition The position of the block
     */
    public AStarNode( BlockPosition blockPosition ) {
        this.blockPosition = blockPosition;
        this.f = Float.MAX_VALUE;
    }

    /**
     * Constructs a new A* node holding a specific block's position.
     *
     * @param blockPosition The position of the block
     * @param f             The node's initial f value
     */
    public AStarNode( BlockPosition blockPosition, float f ) {
        this.blockPosition = blockPosition;
        this.f = f;
    }

    /**
     * Gets the position of the block the node is representing.
     *
     * @return The position of the block the node is representing
     */
    public BlockPosition getBlockPosition() {
        return this.blockPosition;
    }

    /**
     * Gets the g value assigned to the node.
     *
     * @return The node's g value
     */
    public double getG() {
        return this.g;
    }

    /**
     * Assigns a new g value to the node
     *
     * @param g The g value to assign
     */
    public void setG( double g ) {
        this.g = g;
    }

    /**
     * Gets the f value assigned to the node.
     *
     * @return The node's f value
     */
    public double getF() {
        return this.f;
    }

    /**
     * Assigns a new f value to the node
     *
     * @param f The f value to assign
     */
    public void setF( double f ) {
        this.f = f;
    }

    /**
     * Gets the k value assigned to the node. K value denotes how many predecessors
     * a note possesses.
     *
     * @return The node's k value
     */
    public int getK() {
        return this.k;
    }

    /**
     * Assigns a new k value to the node
     *
     * @param k The k value to assign
     */
    public void setK( int k ) {
        this.k = k;
    }

    /**
     * Checks whether or not the node is the start node.
     *
     * @return Whether or not the node is the start node
     */
    public boolean isStart() {
        return ( this.predecessor == this );
    }

    /**
     * Gets the predecessor of the node. Used to reconstruct the path once
     * the shortest path was found. May return this node itself if this
     * node is the start node.
     *
     * @return The node's predecessor
     */
    public AStarNode getPredecessor() {
        return this.predecessor;
    }

    /**
     * Sets the predecessor of the node. Used to reconstruct the path once the
     * shortest path was found.
     *
     * @param predecessor The predecessor to set
     */
    public void setPredecessor( AStarNode predecessor ) {
        this.predecessor = predecessor;
    }

    @Override
    public boolean equals( Object other ) {
        if ( this == other ) {
            return true;
        }

        if ( !( other instanceof AStarNode ) ) {
            return false;
        }

        // Only consider the block position an important field to that
        // no two nodes will refer to the same block whilst the algorithm
        // is running:
        AStarNode o = (AStarNode) other;
        return ( this.blockPosition.equals( o.blockPosition ) );
    }

    @Override
    public int hashCode() {
        // Only block position is significant for equality:
        return this.blockPosition.hashCode();
    }

    @Override
    public int compareTo( AStarNode other ) {
        // Natural sorting order depends on f value instead of block position:
        return Double.compare( this.f, other.f );
    }

}
