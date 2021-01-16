/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import io.gomint.config.InternalConverter;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author geNAZt
 * @author bibo38
 * @version 1.1
 * @stability 3
 */
public class ArrayConverter implements Converter {

    private InternalConverter internalConverter;

    public ArrayConverter( InternalConverter internalConverter ) {
        this.internalConverter = internalConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object toConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) throws Exception {
        Class<?> singleType = type.getComponentType();
        Converter converter = this.internalConverter.getConverter( singleType );

        if ( converter == null ) {
            return object;
        }

        Object[] result = new Object[Array.getLength( object )];

        for ( int index = 0; index < result.length; index++ ) {
            result[index] = converter.toConfig( singleType, Array.get( object, index ), parameterizedType );
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public Object fromConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) throws Exception {
        Class<?> singleType = type.getComponentType();
        List<?> values = object instanceof List ? (List<?>) object : new ArrayList<>( Arrays.asList( (Object[]) object ) );
        Object result = Array.newInstance( singleType, values.size() );
        Converter converter = this.internalConverter.getConverter( singleType );

        if ( converter == null ) {
            return values.toArray( (Object[]) result );
        }

        for ( int index = 0; index < values.size(); index++ ) {
            Array.set( result, index, converter.fromConfig( singleType, values.get( index ), parameterizedType ) );
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports( Class<?> type ) {
        return type.isArray();
    }

}
