/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.math;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * A mutable class representing a triple of float values resembling
 * the x, y and z coordinates of a point respectively.
 * </p>
 *
 * @author Digot
 * @author geNAZt
 * @author BlackyPaw
 * @version 1.2
 */
@EqualsAndHashCode
@ToString
public class Vector implements Cloneable {

    public static final Vector ZERO = new Vector( 0, 0, 0 );

    public static final Vector UP = new Vector( 0, 1, 0 );
    public static final Vector DOWN = new Vector( 0, -1, 0 );

    public static final Vector EAST = new Vector( 1, 0, 0 );
    public static final Vector WEST = new Vector( -1, 0, 0 );
    public static final Vector NORTH = new Vector( 0, 0, -1 );
    public static final Vector SOUTH = new Vector( 0, 0, 1 );

    @Getter
    @Setter
    protected float x;
    @Getter
    @Setter
    protected float y;
    @Getter
    @Setter
    protected float z;

    public Vector() {

    }

    public Vector( float x, float y, float z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector add( float x, float y, float z ) {
        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

    public Vector add( Vector v ) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;

        return this;
    }

    public Vector subtract( float x, float y, float z ) {
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return this;
    }

    public Vector subtract( Vector v ) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;

        return this;
    }

    public Vector multiply( float x, float y, float z ) {
        this.x *= x;
        this.y *= y;
        this.z *= z;

        return this;
    }

    public Vector multiply( Vector v ) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;

        return this;
    }

    public Vector divide( float x, float y, float z ) {
        this.x /= x;
        this.y /= y;
        this.z /= z;

        return this;
    }

    public Vector divide( Vector v ) {
        this.x /= v.x;
        this.y /= v.y;
        this.z /= v.z;

        return this;
    }

    public Vector multiply( float value ) {
        this.x *= value;
        this.y *= value;
        this.z *= value;

        return this;
    }

    public float length() {
        return MathUtils.sqrt( this.x * this.x + this.y * this.y + this.z * this.z );
    }

    public Vector normalize() {
        float mag = this.length();
        if ( mag == 0.0 ) {
            return this;
        }

        this.x /= mag;
        this.y /= mag;
        this.z /= mag;

        return this;
    }

    @Override
    public Vector clone() {
        try {
            Vector vector = (Vector) super.clone();
            vector.x = this.x;
            vector.y = this.y;
            vector.z = this.z;
            return vector;
        } catch ( CloneNotSupportedException e ) {
            throw new AssertionError( "Failed to clone vector!" );
        }
    }

    public float distanceSquared( Vector position ) {
        return MathUtils.square( x - position.x ) + MathUtils.square( y - position.y ) + MathUtils.square( z - position.z );
    }

    public float distance( Vector position ) {
        return MathUtils.sqrt( distanceSquared( position ) );
    }

    public BlockPosition toBlockPosition() {
        return new BlockPosition( MathUtils.fastFloor( x ), MathUtils.fastFloor( y ), MathUtils.fastFloor( z ) );
    }

    /**
     * Get a new vector with the x axis set to the x parameter when the x parameter is
     * on a line with this and the vector other
     *
     * @param other to check with
     * @param x     which may on the line or not
     * @return vector with x set or null when x is not on a line with this and the other vector
     */
    public Vector getVectorWhenXIsOnLine( Vector other, float x ) {
        float xDiff = other.x - this.x;
        float yDiff = other.y - this.y;
        float zDiff = other.z - this.z;

        float f = ( x - this.x ) / xDiff;
        return ( f >= 0F && f <= 1F ) ? new Vector( this.x + xDiff * f, this.y + yDiff * f, this.z + zDiff * f ) : null;
    }

    /**
     * Get a new vector with the y axis set to the y parameter when the y parameter is
     * on a line with this and the vector other
     *
     * @param other to check with
     * @param y     which may on the line or not
     * @return vector with y set or null when y is not on a line with this and the other vector
     */
    public Vector getVectorWhenYIsOnLine( Vector other, float y ) {
        float xDiff = other.x - this.x;
        float yDiff = other.y - this.y;
        float zDiff = other.z - this.z;

        float f = ( y - this.y ) / yDiff;
        return ( f >= 0F && f <= 1F ) ? new Vector( this.x + xDiff * f, this.y + yDiff * f, this.z + zDiff * f ) : null;
    }

    /**
     * Get a new vector with the z axis set to the z parameter when the z parameter is
     * on a line with this and the vector other
     *
     * @param other to check with
     * @param z     which may on the line or not
     * @return vector with y set or null when y is not on a line with this and the other vector
     */
    public Vector getVectorWhenZIsOnLine( Vector other, float z ) {
        float xDiff = other.x - this.x;
        float yDiff = other.y - this.y;
        float zDiff = other.z - this.z;

        float f = ( z - this.z ) / zDiff;
        return ( f >= 0F && f <= 1F ) ? new Vector( this.x + xDiff * f, this.y + yDiff * f, this.z + zDiff * f ) : null;
    }

}
