/*
 * Copyright (c) 2018 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config;

import io.gomint.config.annotation.Path;
import io.gomint.config.converter.Converter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ConfigMapper extends BaseConfigMapper {

    @SuppressWarnings( "unchecked" )
    public Map<String, Object> saveToMap( Class clazz ) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();

        if ( !clazz.getSuperclass().equals( YamlConfig.class ) && !clazz.getSuperclass().equals( Object.class ) ) {
            Map<String, Object> map = this.saveToMap( clazz.getSuperclass() );

            for ( Map.Entry<String, Object> entry : map.entrySet() ) {
                returnMap.put( entry.getKey(), entry.getValue() );
            }
        }

        for ( Field field : clazz.getDeclaredFields() ) {
            if ( this.doSkip( field ) ) {
                continue;
            }

            String path;

            switch (configMode) {
                case PATH_BY_UNDERSCORE:
                    path = field.getName().replace( "_", "." );
                    break;
                case FIELD_IS_KEY:
                    path = field.getName();
                    break;
                case DEFAULT:
                default:
                    if ( field.getName().contains( "_" ) ) {
                        path = field.getName().replace( "_", "." );
                    } else {
                        path = field.getName();
                    }

                    break;
            }

            if ( field.isAnnotationPresent( Path.class ) ) {
                path = field.getAnnotation( Path.class ).value();
            }

            if ( Modifier.isPrivate( field.getModifiers() ) ) {
                field.setAccessible( true );
            }

            try {
                returnMap.put( path, field.get( this ) );
            } catch ( IllegalAccessException ignored ) {

            }
        }

        Converter mapConverter = converter.getConverter( Map.class );
        return (Map<String, Object>) mapConverter.toConfig( HashMap.class, returnMap, null );
    }

    public void loadFromMap( Map section, Class clazz ) throws Exception {
        if ( !clazz.getSuperclass().equals( YamlConfig.class ) && !clazz.getSuperclass().equals( YamlConfig.class ) ) {
            this.loadFromMap( section, clazz.getSuperclass() );
        }

        for ( Field field : clazz.getDeclaredFields() ) {
            if ( this.doSkip( field ) ) {
                continue;
            }

            String path;

            switch (configMode) {
                case PATH_BY_UNDERSCORE:
                    path = field.getName().replace( "_", "." );
                    break;
                case FIELD_IS_KEY:
                    path = field.getName();
                    break;
                case DEFAULT:
                default:
                    if ( field.getName().contains( "_" ) ) {
                        path = field.getName().replace( "_", "." );
                    } else {
                        path = field.getName();
                    }

                    break;
            }

            if ( field.isAnnotationPresent( Path.class ) ) {
                path = field.getAnnotation( Path.class ).value();
            }

            if ( Modifier.isPrivate( field.getModifiers() ) ) {
                field.setAccessible( true );
            }

            converter.fromConfig( (YamlConfig) this, field, ConfigSection.convertFromMap( section ), path );
        }
    }

}
