/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.anvil.entity.v1_8;

import io.gomint.server.entity.Entity;
import io.gomint.server.world.anvil.entity.EntityConverter;
import io.gomint.taglib.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> type of the entity
 * @author geNAZt
 * @version 1.0
 */
public abstract class BaseConverter<T extends Entity> implements EntityConverter<T> {

    @Override
    public NBTTagCompound writeTo( T entity ) {
        NBTTagCompound compound = new NBTTagCompound( "" );

        // Store position
        List<Float> pos = new ArrayList<>();
        pos.add( entity.getPositionX() );
        pos.add( entity.getPositionY() );
        pos.add( entity.getPositionZ() );
        compound.addValue( "Pos", pos );

        // Store motion
        List<Float> motion = new ArrayList<>();
        motion.add( entity.getMotionX() );
        motion.add( entity.getMotionY() );
        motion.add( entity.getMotionZ() );
        compound.addValue( "Motion", motion );

        // Store rotation
        List<Float> rotation = new ArrayList<>();
        rotation.add( entity.getYaw() );
        rotation.add( entity.getPitch() );
        compound.addValue( "Rotation", rotation );

        // Fall distance
        compound.addValue( "FallDistance", entity.getFallDistance() );
        compound.addValue( "NoGravity", (byte) ( entity.isAffectedByGravity() ? 0 : 1 ) );
        compound.addValue( "OnGround", (byte) ( entity.isOnGround() ? 1 : 0 ) );

        return compound;
    }

    @Override
    public T readFrom( NBTTagCompound compound ) {
        // Create entity
        T entity = this.create();

        // Set rotation, position and motion
        this.setRotation( compound, entity );
        this.setPosition( compound, entity );
        this.setMotion( compound, entity );

        // Restore fall distance, no gravity and onground state
        entity.setFallDistance( compound.getFloat( "FallDistance", 0f ) );
        entity.setAffectedByGravity( compound.getByte( "NoGravity", (byte) 0 ) == 0 );
        entity.setOnGround( compound.getByte( "OnGround", (byte) 1 ) == 1 );

        return entity;
    }

    private void setRotation( NBTTagCompound compound, Entity entity ) {
        // Nukkit saves the position etc in doubles (for whatever reasons)
        List<Object> rotation = compound.getList( "Rotation", false );
        if ( rotation != null ) {
            float yaw = ensureFloat( rotation.get( 0 ) );
            float pitch = ensureFloat( rotation.get( 1 ) );

            entity.setYaw( yaw );
            entity.setPitch( pitch );
        }
    }

    private void setMotion( NBTTagCompound compound, Entity entity ) {
        // Nukkit saves the position etc in doubles (for whatever reasons)
        List<Object> motion = compound.getList( "Motion", false );
        if ( motion != null ) {
            float x = ensureFloat( motion.get( 0 ) );
            float y = ensureFloat( motion.get( 1 ) );
            float z = ensureFloat( motion.get( 2 ) );

            entity.getTransform().setMotion( x, y, z );
        }
    }

    private void setPosition( NBTTagCompound compound, Entity entity ) {
        // Nukkit saves the position etc in doubles (for whatever reasons)
        List<Object> pos = compound.getList( "Pos", false );
        if ( pos != null ) {
            float x = ensureFloat( pos.get( 0 ) );
            float y = ensureFloat( pos.get( 1 ) );
            float z = ensureFloat( pos.get( 2 ) );

            entity.setPosition( x, y, z );
        }
    }

    private float ensureFloat( Object o ) {
        if ( o instanceof Float ) {
            return (float) o;
        } else if ( o instanceof Double ) {
            return ( (Double) o ).floatValue();
        }

        return 0;
    }

}
