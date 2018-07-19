/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import io.gomint.config.ConfigSection;
import io.gomint.config.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockPositionConverter implements Converter {

    public BlockPositionConverter( InternalConverter converter ) {
    }

    @Override
    public Object toConfig( Class<?> type, Object obj, ParameterizedType genericType ) throws Exception {
        io.gomint.math.BlockPosition location = (io.gomint.math.BlockPosition) obj;
        java.util.Map<String, Object> saveMap = new HashMap<>();

        saveMap.put( "x", location.getX() );
        saveMap.put( "y", location.getY() );
        saveMap.put( "z", location.getZ() );

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
        return new io.gomint.math.BlockPosition(
            getInteger( locationMap.get( "x" ) ),
            getInteger( locationMap.get( "y" ) ),
            getInteger( locationMap.get( "z" ) ) );
    }

    private int getInteger( Object obj ) {
        if ( obj instanceof Long ) {
            return ( (Long) obj ).intValue();
        }

        return (int) obj;
    }

    @Override
    public boolean supports( Class<?> type ) {
        return io.gomint.math.BlockPosition.class.isAssignableFrom( type );
    }

}
