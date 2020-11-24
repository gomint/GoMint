/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import io.gomint.config.ConfigSection;
import io.gomint.config.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class MapConverter implements Converter {

    private InternalConverter internalConverter;

    public MapConverter( InternalConverter internalConverter ) {
        this.internalConverter = internalConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public Object toConfig( Class<?> type, Object object, ParameterizedType genericType ) throws Exception {
        Map<Object, Object> result = (Map) object;

        for ( Map.Entry<Object, Object> entry : result.entrySet() ) {
            if ( entry.getValue() == null ) {
                continue;
            }

            Class clazz = entry.getValue().getClass();
            Converter converter = this.internalConverter.getConverter( clazz );
            Object value = entry.getValue();

            // Ternary operators have been stripped to if statements for readability purposes

            if ( converter != null ) {
                value = converter.toConfig( clazz, entry.getValue(), null );
            }

            result.put( entry.getKey(), value );
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public Object fromConfig( Class type, Object object, ParameterizedType genericType ) throws Exception {
        if ( genericType != null ) {
            Map result = new HashMap();

            try {
                result = (Map) ( (Class) genericType.getRawType() ).getDeclaredConstructor().newInstance();
            } catch ( InstantiationException ignored ) {

            }

            if ( genericType.getActualTypeArguments().length == 2 ) {
                Class keyClass = (Class) genericType.getActualTypeArguments()[0];

                if ( object == null ) {
                    object = new HashMap<>();
                }

                Map<?, ?> map = object instanceof Map ? (Map) object : ( (ConfigSection) object ).getRawMap();

                for ( Map.Entry<?, ?> entry : map.entrySet() ) {
                    Object key;
                    Class clazz;

                    if ( keyClass.equals( Integer.class ) && !( entry.getKey() instanceof Integer ) ) {
                        key = Integer.valueOf( (String) entry.getKey() );
                    } else if ( keyClass.equals( Short.class ) && !( entry.getKey() instanceof Short ) ) {
                        key = Short.valueOf( (String) entry.getKey() );
                    } else if ( keyClass.equals( Byte.class ) && !( entry.getKey() instanceof Byte ) ) {
                        key = Byte.valueOf( (String) entry.getKey() );
                    } else if ( keyClass.equals( Float.class ) && !( entry.getKey() instanceof Float ) ) {
                        key = Float.valueOf( (String) entry.getKey() );
                    } else if ( keyClass.equals( Double.class ) && !( entry.getKey() instanceof Double ) ) {
                        key = Double.valueOf( (String) entry.getKey() );
                    } else {
                        key = entry.getKey();
                    }

                    Type argument = genericType.getActualTypeArguments()[1];

                    if ( argument instanceof ParameterizedType ) {
                        clazz = (Class) ( (ParameterizedType) argument ).getRawType();
                    } else {
                        clazz = (Class) argument;
                    }

                    // Ternary operators have been stripped to if statements for readability purposes

                    Object value = entry.getValue();
                    ParameterizedType parameterizedType = null;
                    Converter converter = this.internalConverter.getConverter( clazz );

                    if ( argument instanceof ParameterizedType ) {
                        parameterizedType = (ParameterizedType) argument;
                    }

                    if ( converter != null ) {
                        value = converter.fromConfig( clazz, entry.getValue(), parameterizedType );
                    }

                    result.put( key, value );
                }
            } else {
                Converter converter = this.internalConverter.getConverter( (Class) genericType.getRawType() );

                if ( converter != null ) {
                    return converter.fromConfig( (Class) genericType.getRawType(), object, null );
                }

                return object instanceof Map ? (Map) object : ( (ConfigSection) object ).getRawMap();
            }

            return result;
        } else {
            return object;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports( Class<?> type ) {
        return Map.class.isAssignableFrom( type );
    }

}
