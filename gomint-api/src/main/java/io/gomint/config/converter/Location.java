/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import io.gomint.GoMint;
import io.gomint.config.ConfigSection;
import io.gomint.config.InternalConverter;
import io.gomint.world.World;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Location implements Converter {

    public Location( InternalConverter converter ) {
    }

    @Override
    public Object toConfig( Class<?> type, Object obj, ParameterizedType genericType ) throws Exception {
        io.gomint.math.Location location = (io.gomint.math.Location) obj;
        java.util.Map<String, Object> saveMap = new HashMap<>();
        if ( location.getWorld() != null ) {
            saveMap.put( "world", location.getWorld().getWorldName() );
        }

        saveMap.put( "x", location.getX() );
        saveMap.put( "y", location.getY() );
        saveMap.put( "z", location.getZ() );
        saveMap.put( "yaw", location.getYaw() );
        saveMap.put( "pitch", location.getPitch() );

        return saveMap;
    }

    @Override
    public Object fromConfig( Class type, Object section, ParameterizedType genericType ) throws Exception {
        java.util.Map<String, Object> locationMap;
        if ( section instanceof java.util.Map ) {
            locationMap = (java.util.Map<String, Object>) section;
        } else {
            locationMap = (java.util.Map<String, Object>) ( (ConfigSection) section ).getRawMap();
        }

        float yaw = getFloat( locationMap.get( "yaw" ) );
        float pitch = getFloat( locationMap.get( "pitch" ) );

        World world = null;
        if ( locationMap.containsKey( "world" ) ) {
            world = GoMint.instance().getWorld( (String) locationMap.get( "world" ) );
        }

        return new io.gomint.math.Location( world,
            getFloat( locationMap.get( "x" ) ),
            getFloat( locationMap.get( "y" ) ),
            getFloat( locationMap.get( "z" ) ),
            yaw,
            pitch );
    }

    private float getFloat( Object obj ) {
        if ( obj instanceof Double ) {
            return ( (Double) obj ).floatValue();
        } else if ( obj instanceof Integer ) {
            return ( (Integer) obj ).floatValue();
        } else if ( obj instanceof Long ) {
            return ( (Long) obj ).floatValue();
        }

        return (float) obj;
    }

    @Override
    public boolean supports( Class<?> type ) {
        return io.gomint.math.Location.class.isAssignableFrom( type );
    }

}
