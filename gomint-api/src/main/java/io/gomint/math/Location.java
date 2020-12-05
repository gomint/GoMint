/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.math;

import io.gomint.world.World;
import io.gomint.world.block.Block;

import java.util.Objects;

/**
 * <p>
 * A Location defines a point a world with three coordinates relative to a
 * specific world it is placed in.
 * </p>
 *
 * @author Digot
 * @author geNAZt
 * @author BlackyPaw
 * @version 2.0
 * @stability 3
 */
public class Location extends Vector {

    private World world;

    private float yaw;
    private float headYaw;
    private float pitch;

    public Location(World world ) {
        this.setWorld( world );
    }

    public Location( World world, float x, float y, float z ) {
        super( x, y, z );
        this.world = world;
    }

    public Location( World world, Vector vector ) {
        super( vector.getX(), vector.getY(), vector.getZ() );
        this.world = world;
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

    public Location(World world, BlockPosition position) {
        this( world, position.getX(), position.getY(), position.getZ());
    }

    public void setWorld( World world ) {
        this.world = world;
    }

    @Override
    public Location add( float x, float y, float z ) {
        return new Location( this.world, this.x + x, this.y + y, this.z + z, this.headYaw, this.yaw, this.pitch );
    }

    @Override
    public Location add( Vector v ) {
        return this.add( v.x, v.y, v.z );
    }

    @Override
    public Location subtract( float x, float y, float z ) {
        return new Location( this.world, this.x - x, this.y - y, this.z - z, this.headYaw, this.yaw, this.pitch );
    }

    @Override
    public Location subtract( Vector v ) {
        return this.subtract( v.x, v.y, v.z );
    }

    @Override
    public Location multiply( float x, float y, float z ) {
        return new Location( this.world, this.x * x, this.y * y, this.z * z, this.headYaw, this.yaw, this.pitch );
    }

    @Override
    public Location multiply( Vector v ) {
        return this.multiply( v.x, v.y, v.z );
    }

    @Override
    public Location divide( float x, float y, float z ) {
        return new Location( this.world, this.x / x, this.y / y, this.z / z, this.headYaw, this.yaw, this.pitch );
    }

    @Override
    public Location divide( Vector v ) {
        return this.divide( v.x, v.y, v.z );
    }

    @Override
    public Location multiply( float scalar ) {
        return this.multiply( scalar, scalar, scalar );
    }

    public <T extends Block> T getBlock() {
        return this.world.getBlockAt( MathUtils.fastFloor( this.x ), MathUtils.fastFloor( this.y ), MathUtils.fastFloor( this.z ) );
    }


    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setHeadYaw(float headYaw) {
        this.headYaw = headYaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public World getWorld() {
        return world;
    }

    public float getYaw() {
        return yaw;
    }

    public float getHeadYaw() {
        return headYaw;
    }

    public float getPitch() {
        return pitch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Location location = (Location) o;
        return Float.compare(location.yaw, yaw) == 0 &&
            Float.compare(location.headYaw, headYaw) == 0 &&
            Float.compare(location.pitch, pitch) == 0 &&
            Objects.equals(world, location.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), world, yaw, headYaw, pitch);
    }

    @Override
    public String toString() {
        return "{\"_class\":\"Location\", " +
            "\"world\":" + (world == null ? "null" : world) + ", " +
            "\"yaw\":\"" + yaw + "\"" + ", " +
            "\"headYaw\":\"" + headYaw + "\"" + ", " +
            "\"pitch\":\"" + pitch + "\"" + ", " +
            "\"x\":\"" + x + "\"" + ", " +
            "\"y\":\"" + y + "\"" + ", " +
            "\"z\":\"" + z + "\"" +
            "}";
    }

}
