package io.gomint.server.util;

import io.gomint.math.Vector;

/**
 * Triple consisting of three 32-bit integer values.
 *
 * @author BlackyPaw
 * @version 1.0
 *
 * @deprecated Use {@link io.gomint.math.BlockPosition} instead
 */
public class IntTriple implements Cloneable {

    private int x;
    private int y;
    private int z;

    /**
     * Constructs a new integer triple.
     *
     * @param x The first value
     * @param y The second value
     * @param z The third value
     */
    public IntTriple( int x, int y, int z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gets the first value of the integer triple.
     *
     * @return The first value of the integer triple
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the second value of the integer triple.
     *
     * @return The second value of the integer triple
     */
    public int getY() {
        return this.y;
    }

    /**
     * Gets the third value of the integer triple.
     *
     * @return The third value of the integer triple
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Constructs a vector out of the triple's values.
     *
     * @return The vector holding the same values as this triple
     */
    public Vector toVector() {
        return new Vector( this.x, this.y, this.z );
    }

    @Override
    public boolean equals( Object other ) {
        if ( this == other ) {
            return true;
        }

        if ( !( other instanceof IntTriple ) ) {
            return false;
        }

        IntTriple o = (IntTriple) other;
        return ( this.x == o.x && this.y == o.y && this.z == o.z );
    }

    @Override
    public int hashCode() {
        int hash = 157;
        hash = hash * 31 + this.x;
        hash = hash * 31 + this.y;
        hash = hash * 31 + this.z;
        return hash;
    }

    @Override
    public String toString() {
        return String.format( "[x=%d, y=%d, z=%d]", this.x, this.y, this.z );
    }

    @Override
    public IntTriple clone() {
        try {
            IntTriple copy = (IntTriple) super.clone();
            copy.x = this.x;
            copy.y = this.y;
            copy.z = this.z;
            return copy;
        } catch ( CloneNotSupportedException e ) {
            throw new AssertionError( "Failed to clone IntTriple!" );
        }
    }

}
