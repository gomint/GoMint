/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.math;

import io.gomint.world.World;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * A Location defines a point a world with three coordinates relative to a
 * specific world it is placed in.
 * </p>
 *
 * @author Digot
 * @author geNAZt
 * @author BlackyPaw
 * @version 1.2
 */
@EqualsAndHashCode( callSuper = true )
@ToString( callSuper = true )
public class Location extends Vector implements Cloneable {

    @Getter private World world;

    @Getter @Setter private float yaw;
    @Getter @Setter private float headYaw;
    @Getter @Setter private float pitch;

    public Location( World world ) {
        this.setWorld( world );
    }

    public Location( World world, float x, float y, float z ) {
        super( x, y, z );
        this.setWorld( world );
    }

    public Location( World world, Vector vector ) {
        super( vector.getX(), vector.getY(), vector.getZ() );
        this.setWorld( world );
    }

    public Location( World world, float x, float y, float z, float yaw, float pitch ) {
        super( x, y, z );
        this.setWorld( world );
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location( World world, Vector vector, float yaw, float pitch ) {
        super( vector.getX(), vector.getY(), vector.getZ() );
        this.setWorld( world );
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location( World world, float x, float y, float z, float headYaw, float yaw, float pitch ) {
        this( world, x, y, z, yaw, pitch );
        this.headYaw = headYaw;
    }

    public void setWorld( World world ) {
        if ( world == null ) {
            throw new NullPointerException( "Cannot set world of location to null!" );
        }
        this.world = world;
    }

    @Override
    public Location add( float x, float y, float z ) {
        super.add( x, y, z );
        return this;
    }

    @Override
    public Location add( Vector v ) {
        super.add( v );
        return this;
    }

    @Override
    public Location subtract( float x, float y, float z ) {
        super.subtract( x, y, z );
        return this;
    }

    @Override
    public Location subtract( Vector v ) {
        super.subtract( v );
        return this;
    }

    @Override
    public Location multiply( float x, float y, float z ) {
        super.multiply( x, y, z );
        return this;
    }

    @Override
    public Location multiply( Vector v ) {
        super.multiply( v );
        return this;
    }

    @Override
    public Location divide( float x, float y, float z ) {
        super.divide( x, y, z );
        return this;
    }

    @Override
    public Location divide( Vector v ) {
        super.divide( v );
        return this;
    }

    @Override
    public Location multiply( float scalar ) {
        super.multiply( scalar );
        return this;
    }

    @Override
    public Location clone() {
        Location location = (Location) super.clone();
        location.world = this.world;
        location.yaw = this.yaw;
        location.pitch = this.pitch;
        return location;
    }

    public Vector toVector() {
        return new Vector( this.x, this.y, this.z );
    }

}
