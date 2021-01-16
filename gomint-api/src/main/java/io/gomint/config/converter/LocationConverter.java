/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import io.gomint.GoMint;
import io.gomint.config.ConfigSection;
import io.gomint.config.InternalConverter;
import io.gomint.math.Location;
import io.gomint.world.World;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class LocationConverter extends BaseConverter {

    // This constructor is needed to prevent InternalConverter throwing an exception
    // InternalConverter accesses this constructor with Reflection to create an instance
    // !!!! DO NOT REMOVE !!!!
    // It will compile but will fail at runtime
    public LocationConverter( InternalConverter internalConverter ) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object toConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) {
        Location location = (Location) object;
        Map<String, Object> saveMap = new HashMap<>();

        if ( location.world() != null ) {
            saveMap.put( "world", location.world().folder() );
        }

        saveMap.put( "x", location.x() );
        saveMap.put( "y", location.y() );
        saveMap.put( "z", location.z() );
        saveMap.put( "yaw", location.yaw() );
        saveMap.put( "pitch", location.pitch() );

        return saveMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public Object fromConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) {
        World world = null;
        Float headYaw = null;
        Map<String, Object> locationMap;

        if ( object instanceof Map ) {
            locationMap = (Map<String, Object>) object;
        } else {
            locationMap = (Map<String, Object>) ( (ConfigSection) object ).getRawMap();
        }

        float x = super.asFloat( locationMap.get( "x" ) );
        float y = super.asFloat( locationMap.get( "y" ) );
        float z = super.asFloat( locationMap.get( "z" ) );
        float yaw = super.asFloat( locationMap.get( "yaw" ) );
        float pitch = super.asFloat( locationMap.get( "pitch" ) );

        if ( locationMap.containsKey( "world" ) ) {
            world = GoMint.instance().world((String) locationMap.get("world"));
        }

        if ( locationMap.containsKey( "headYaw" ) ) {
            headYaw = (Float) locationMap.get( "headYaw" );
        }

        headYaw = headYaw == null ? yaw : headYaw;

        return new Location( world, x, y, z, headYaw, yaw, pitch );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports( Class<?> type ) {
        return Location.class.isAssignableFrom( type );
    }

}
