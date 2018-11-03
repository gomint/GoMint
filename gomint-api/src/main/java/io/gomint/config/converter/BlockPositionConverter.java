/*
 * Copyright (c) 2018 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import io.gomint.config.ConfigSection;
import io.gomint.math.BlockPosition;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class BlockPositionConverter extends BaseConverter {

    @Override
    public Object toConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) {
        BlockPosition location = (BlockPosition) object;
        Map<String, Object> saveMap = new HashMap<>();

        saveMap.put( "x", location.getX() );
        saveMap.put( "y", location.getY() );
        saveMap.put( "z", location.getZ() );

        return saveMap;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Object fromConfig( Class type, Object object, ParameterizedType parameterizedType ) {
        Map<String, Object> locationMap;

        if ( object instanceof Map ) {
            locationMap = (Map<String, Object>) object;
        } else {
            locationMap = (Map<String, Object>) ( (ConfigSection) object ).getRawMap();
        }

        return new BlockPosition(
            super.asInteger( locationMap.get( "x" ) ),
            super.asInteger( locationMap.get( "y" ) ),
            super.asInteger( locationMap.get( "z" ) )
        );
    }

    @Override
    public boolean supports( Class<?> type ) {
        return BlockPosition.class.isAssignableFrom( type );
    }

}
