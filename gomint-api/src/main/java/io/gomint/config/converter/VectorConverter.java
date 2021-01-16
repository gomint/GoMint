/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import io.gomint.config.ConfigSection;
import io.gomint.config.InternalConverter;
import io.gomint.math.Vector;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class VectorConverter extends BaseConverter {

    // This constructor is needed to prevent InternalConverter throwing an exception
    // InternalConverter accesses this constructor with Reflection to create an instance
    // !!!! DO NOT REMOVE !!!!
    // It will compile but will fail at runtime
    public VectorConverter( InternalConverter internalConverter ) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object toConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) {
        Vector vector = (Vector) object;
        Map<String, Object> saveMap = new HashMap<>();

        saveMap.put( "x", vector.x() );
        saveMap.put( "y", vector.y() );
        saveMap.put( "z", vector.z() );

        return saveMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public Object fromConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) {
        Map<String, Object> vectorMap;

        if ( object instanceof Map ) {
            vectorMap = (Map<String, Object>) object;
        } else {
            vectorMap = (Map<String, Object>) ( (ConfigSection) object ).getRawMap();
        }

        return new Vector(
            super.asFloat( vectorMap.get( "x" ) ),
            super.asFloat( vectorMap.get( "y" ) ),
            super.asFloat( vectorMap.get( "z" ) )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports( Class<?> type ) {
        return Vector.class.isAssignableFrom( type );
    }

}
