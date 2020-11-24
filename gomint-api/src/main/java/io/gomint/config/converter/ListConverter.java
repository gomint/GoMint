/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import io.gomint.config.BaseConfigMapper;
import io.gomint.config.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class ListConverter implements Converter {

    private InternalConverter internalConverter;

    public ListConverter( InternalConverter internalConverter ) {
        this.internalConverter = internalConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public Object toConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) throws Exception {
        List values = (List) object;
        List converted = new ArrayList();

        if ( this.internalConverter.getConfig() instanceof BaseConfigMapper ) {
            BaseConfigMapper baseConfigMapper = (BaseConfigMapper) this.internalConverter.getConfig();
            baseConfigMapper.addCommentPrefix( "-" );
        }

        for ( Object value : values ) {
            Converter converter = this.internalConverter.getConverter( value.getClass() );

            if ( converter != null ) {
                converted.add( converter.toConfig( value.getClass(), value, null ) );
            } else {
                converted.add( value );
            }
        }

        if ( this.internalConverter.getConfig() instanceof BaseConfigMapper ) {
            BaseConfigMapper baseConfigMapper = (BaseConfigMapper) this.internalConverter.getConfig();
            baseConfigMapper.removeCommentPrefix( "-" );
        }

        return converted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public Object fromConfig( Class type, Object object, ParameterizedType parameterizedType ) throws Exception {
        List converted = new ArrayList();

        try {
            converted = ( (List) type.getDeclaredConstructor().newInstance() );
        } catch ( Exception ignored ) {

        }

        List values = (List) object;

        if ( parameterizedType != null && parameterizedType.getActualTypeArguments()[0] instanceof Class ) {
            Class actualTypeArgument = (Class) parameterizedType.getActualTypeArguments()[0];
            Converter converter = this.internalConverter.getConverter( actualTypeArgument );

            if ( converter != null ) {
                for ( Object value : values ) {
                    converted.add( converter.fromConfig( actualTypeArgument, value, null ) );
                }
            } else {
                converted = values;
            }
        } else {
            converted = values;
        }

        return converted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports( Class<?> type ) {
        return List.class.isAssignableFrom( type );
    }

}
