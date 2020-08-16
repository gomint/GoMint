package io.gomint.math;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockPosition implements Cloneable {

    public static final BlockPosition UP = new BlockPosition( 0, 1, 0 );
    public static final BlockPosition DOWN = new BlockPosition( 0, -1, 0 );

    public static final BlockPosition EAST = new BlockPosition( 1, 0, 0 );
    public static final BlockPosition WEST = new BlockPosition( -1, 0, 0 );
    public static final BlockPosition NORTH = new BlockPosition( 0, 0, -1 );
    public static final BlockPosition SOUTH = new BlockPosition( 0, 0, 1 );

    private int x, y, z;

    public BlockPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Vector toVector() {
        return new Vector( this.x, this.y, this.z );
    }

    public BlockPosition add( BlockPosition other ) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public BlockPosition add( int x, int y, int z ) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public BlockPosition clone() {
        try {
            BlockPosition blockPosition = (BlockPosition) super.clone();
            blockPosition.x = this.x;
            blockPosition.y = this.y;
            blockPosition.z = this.z;
            return blockPosition;
        } catch ( CloneNotSupportedException e ) {
            throw new AssertionError( "Failed to clone block position!" );
        }
    }

}
