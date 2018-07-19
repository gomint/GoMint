/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */
package io.gomint.math;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * A immutable class representing a triple of float values resembling
 * the x, y and z coordinates of a point respectively.
 * </p>
 *
 * @author Digot
 * @author geNAZt
 * @author BlackyPaw
 * @version 2.0
 */
@EqualsAndHashCode
@ToString
@Data
public class Vector {

    public static final Vector ZERO = new Vector( 0, 0, 0 );

    public static final Vector UP = new Vector( 0, 1, 0 );
    public static final Vector DOWN = new Vector( 0, -1, 0 );

    public static final Vector EAST = new Vector( 1, 0, 0 );
    public static final Vector WEST = new Vector( -1, 0, 0 );
    public static final Vector NORTH = new Vector( 0, 0, -1 );
    public static final Vector SOUTH = new Vector( 0, 0, 1 );

    protected float x;
    protected float y;
    protected float z;

    public Vector() {

    }

    public Vector( float x, float y, float z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector add( float x, float y, float z ) {
        return new Vector( this.x + x, this.y + y, this.z + z );
    }

    public Vector add( Vector v ) {
        return this.add( v.x, v.y, v.z );
    }

    public Vector subtract( float x, float y, float z ) {
        return new Vector( this.x - x, this.y - y, this.z - z );
    }

    public Vector subtract( Vector v ) {
        return this.subtract( v.x, v.y, v.z );
    }

    public Vector multiply( float x, float y, float z ) {
        return new Vector( this.x * x, this.y * y, this.z * z );
    }

    public Vector multiply( Vector v ) {
        return this.multiply( v.x, v.y, v.z );
    }

    public Vector divide( float x, float y, float z ) {
        return new Vector( this.x / x, this.y / y, this.z / z );
    }

    public Vector divide( Vector v ) {
        return this.divide( v.x, v.y, v.z );
    }

    public Vector multiply( float value ) {
        return this.multiply( value, value, value );
    }

    public float length() {
        return MathUtils.sqrt( this.x * this.x + this.y * this.y + this.z * this.z );
    }

    public Vector normalize() {
        float mag = this.length();
        if ( mag == 0.0 ) {
            return new Vector( this.x, this.y, this.z );
        }

        return new Vector( this.x / mag, this.y / mag, this.z / mag );
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
