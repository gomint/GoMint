package io.gomint.math;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
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

    public int x() {
        return x;
    }

    public BlockPosition x(int x) {
        this.x = x;
        return this;
    }

    public int y() {
        return y;
    }

    public BlockPosition y(int y) {
        this.y = y;
        return this;
    }

    public int z() {
        return z;
    }

    public BlockPosition z(int z) {
        this.z = z;
        return this;
    }

    public Vector toVector() {
        return new Vector( this.x, this.y, this.z );
    }

    public BlockPosition add( BlockPosition other ) {
        return new BlockPosition(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public BlockPosition add( int x, int y, int z ) {
        return new BlockPosition(this.x + x, this.y + y, this.z + z);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockPosition that = (BlockPosition) o;
        return x == that.x &&
            y == that.y &&
            z == that.z;
    }

    @Override
    public int hashCode() {
        int h = 1;
        h = 31 * h + x;
        h = 31 * h + y;
        h = 31 * h + z;
        return h;
    }

    @Override
    public String toString() {
        return "BlockPosition{" +
            "x=" + x +
            ", y=" + y +
            ", z=" + z +
            '}';
    }

}
