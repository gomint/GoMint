/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import io.gomint.config.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class SetConverter implements Converter {

    private InternalConverter internalConverter;

    public SetConverter( InternalConverter internalConverter ) {
        this.internalConverter = internalConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public Object toConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) throws Exception {
        Set<Object> values = (Set<Object>) object;
        List<Object> result = new ArrayList<>();

        for ( Object value : values ) {
            Converter converter = this.internalConverter.getConverter( value.getClass() );

            if ( converter != null ) {
                result.add( converter.toConfig( value.getClass(), value, null ) );
            } else {
                result.add( value );
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public Object fromConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) throws Exception {
        List<Object> values = (List<Object>) object;
        Set<Object> result = new HashSet<>();

        try {
            result = (Set<Object>) type.getDeclaredConstructor().newInstance();
        } catch ( Exception ignored ) {

        }

        if ( parameterizedType != null && parameterizedType.getActualTypeArguments()[0] instanceof Class ) {
            Class<?> actualTypeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            Converter converter = this.internalConverter.getConverter( actualTypeArgument );

            if ( converter != null ) {
                for ( Object value : values ) {
                    result.add( converter.fromConfig( actualTypeArgument, value, null ) );
                }
            } else {
                result.addAll( values );
            }
        } else {
            result.addAll( values );
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports( Class<?> type ) {
        return Set.class.isAssignableFrom( type );
    }

}
