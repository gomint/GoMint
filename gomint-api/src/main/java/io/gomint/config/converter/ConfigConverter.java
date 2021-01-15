/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config.converter;

import io.gomint.config.*;
import io.gomint.config.annotation.Comment;
import io.gomint.config.annotation.Comments;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class ConfigConverter implements Converter {

    private InternalConverter internalConverter;

    public ConfigConverter( InternalConverter internalConverter ) {
        this.internalConverter = internalConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object toConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) throws Exception {
        if ( object instanceof Map ) {
            return object;
        }

        Map<String, String> comments = new LinkedHashMap<>();

        // We need to extract comments
        for ( Field field : type.getDeclaredFields() ) {
            StringBuilder commentBuilder = new StringBuilder();

            if ( field.isAnnotationPresent( Comment.class ) ) {
                commentBuilder = new StringBuilder( field.getAnnotation( Comment.class ).value() );
            } else if ( field.isAnnotationPresent( Comments.class ) ) {
                for ( Comment comment : field.getAnnotation( Comments.class ).value() ) {
                    commentBuilder.append( comment.value() ).append( "\n" );
                }
            }

            if ( commentBuilder.length() > 0 ) {
                comments.put( field.getName(), commentBuilder.toString() );
            }
        }

        if ( this.internalConverter.getConfig() instanceof BaseConfigMapper ) {
            BaseConfigMapper baseConfigMapper = (BaseConfigMapper) this.internalConverter.getConfig();
            baseConfigMapper.mergeComments( comments );
        }

        return ( (YamlConfig) object ).saveToMap( object.getClass() );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object fromConfig( Class<?> type, Object object, ParameterizedType parameterizedType ) throws Exception {
        YamlConfig yamlConfig = (YamlConfig) newInstance( type );

        // Inject converter stack into sub config
        for ( Class<?> customConverter : this.internalConverter.getCustomConverters() ) {
            yamlConfig.addConverter( customConverter );
        }

        yamlConfig.loadFromMap( object instanceof Map ? (Map<?, ?>) object : ( (ConfigSection) object ).getRawMap(), type );
        return yamlConfig;
    }


    /**
     * {@inheritDoc}
     */
    // recursively handles enclosed classes
    @Override
    public boolean supports( Class<?> type ) {
        return YamlConfig.class.isAssignableFrom( type );
    }

    private Object newInstance( Class<?> type ) throws Exception {
        Class<?> enclosingClass = type.getEnclosingClass();

        if ( enclosingClass != null ) {
            Object instanceOfEnclosingClass = newInstance( enclosingClass );
            return type.getConstructor( enclosingClass ).newInstance( instanceOfEnclosingClass );
        } else {
            return type.getDeclaredConstructor().newInstance();
        }
    }

}
