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
public class VectorConverter implements Converter {

    public VectorConverter( InternalConverter converter ) {
    }

    @Override
    public Object toConfig( Class<?> type, Object obj, ParameterizedType genericType ) throws Exception {
        io.gomint.math.Vector vector = (io.gomint.math.Vector) obj;
        java.util.Map<String, Object> saveMap = new HashMap<>();

        saveMap.put( "x", vector.getX() );
        saveMap.put( "y", vector.getY() );
        saveMap.put( "z", vector.getZ() );

        return saveMap;
    }

    @Override
    public Object fromConfig( Class type, Object section, ParameterizedType genericType ) throws Exception {
        java.util.Map<String, Object> vectorMap;
        if ( section instanceof java.util.Map ) {
            vectorMap = (java.util.Map<String, Object>) section;
        } else {
            vectorMap = (java.util.Map<String, Object>) ( (ConfigSection) section ).getRawMap();
        }

        return new io.gomint.math.Vector( getFloat( vectorMap.get( "x" ) ), getFloat( vectorMap.get( "y" ) ), getFloat( vectorMap.get( "z" ) ) );
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
        return io.gomint.math.Vector.class.isAssignableFrom( type );
    }

}
