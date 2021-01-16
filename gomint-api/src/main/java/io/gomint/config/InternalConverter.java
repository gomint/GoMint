/*
 * Copyright (c) 2020 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.config;

import io.gomint.config.annotation.PreserveStatic;
import io.gomint.config.converter.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public class InternalConverter {

    private final BaseConfig config;
    private final Set<Converter> converters;
    private final List<Class<?>> customConverters;

    public InternalConverter( BaseConfig config ) {
        this.config = config;
        this.converters = new LinkedHashSet<>();
        this.customConverters = new ArrayList<>();

        try {
            this.addConverters(
                PrimitiveConverter.class,
                ConfigConverter.class,
                ListConverter.class,
                MapConverter.class,
                ArrayConverter.class,
                SetConverter.class,
                LocationConverter.class,
                VectorConverter.class,
                BlockPositionConverter.class
            );
        } catch ( InvalidConverterException cause ) {
            throw new IllegalStateException( cause );
        }
    }

    public BaseConfig getConfig() {
        return config;
    }

    public InternalConverter addConverters(Class<?> ... classes ) throws InvalidConverterException {
        for ( Class<?> converterClass : classes ) {
            this.addConverter( converterClass );
        }

        return this;
    }

    public InternalConverter addConverter( Class<?> clazz ) throws InvalidConverterException {
        if ( !Converter.class.isAssignableFrom( clazz ) ) {
            throw new InvalidConverterException( clazz.getName() + " does not implement " + Converter.class );
        }

        try {
            this.converters.add( (Converter) clazz.getConstructor( InternalConverter.class ).newInstance( this ) );
        } catch ( NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException cause ) {
            if ( cause instanceof NoSuchMethodException ) {
                throw new InvalidConverterException( clazz.getName() + " is missing a constructor declaring only" +
                    " one parameter of type " + InternalConverter.class, cause );
            } else if ( cause instanceof InvocationTargetException ) {
                throw new InvalidConverterException( "Converter could not be invoked", cause );
            } else if ( cause instanceof InstantiationException ) {
                throw new InvalidConverterException( "Converter could not be instantiated", cause );
            } else if ( cause instanceof IllegalAccessException ) {
                throw new InvalidConverterException( clazz.getName() + " is missing a public constructor declaring " +
                    "only one parameter of type " + InternalConverter.class, cause );
            }
        }
        return this;
    }

    public Converter getConverter( Class<?> type ) {
        for ( Converter converter : converters ) {
            if ( converter.supports( type ) ) {
                return converter;
            }
        }

        return null;
    }

    public InternalConverter fromConfig( YamlConfig config, Field field, ConfigSection root, String path ) throws Exception {
        Converter converter;
        Object fieldValue = field.get( config );

        if ( fieldValue != null ) {
            converter = this.getConverter( fieldValue.getClass() );

            if ( converter != null ) {
                ParameterizedType parameterizedType = this.evalParameterizedField( field );
                Object value = converter.fromConfig( fieldValue.getClass(), root.get( path ), parameterizedType );

                // If we're trying to assign a value to a static variable
                // then assure there's the "PreserveStatic" annotation on there!
                if ( Modifier.isStatic( field.getModifiers() ) ) {
                    if ( !field.isAnnotationPresent( PreserveStatic.class ) ) {
                        return this;
                    }

                    if ( !field.getAnnotation( PreserveStatic.class ).value() ) {
                        return this;
                    }

                    if ( converter instanceof PrimitiveConverter && value == null ) {
                        return this;
                    }

                    field.set( null, value );
                    return this;
                }

                if ( converter instanceof PrimitiveConverter && value == null ) {
                    return this;
                }

                field.set( config, value );
                return this;
            } else {
                converter = this.getConverter( field.getType() );

                if ( converter != null ) {
                    ParameterizedType parameterizedType = this.evalParameterizedField( field );
                    Object value = converter.fromConfig( field.getType(), root.get( path ), parameterizedType );

                    // If we're trying to assign a value to a static variable
                    // then assure there's the "PreserveStatic" annotation on there!
                    if ( Modifier.isStatic( field.getModifiers() ) ) {
                        if ( !field.isAnnotationPresent( PreserveStatic.class ) ) {
                            return this;
                        }

                        if ( !field.getAnnotation( PreserveStatic.class ).value() ) {
                            return this;
                        }

                        if ( converter instanceof PrimitiveConverter && value == null ) {
                            return this;
                        }

                        field.set( null, value );
                        return this;
                    }

                    if ( converter instanceof PrimitiveConverter && value == null ) {
                        return this;
                    }

                    field.set( config, value );
                    return this;
                }
            }
        } else {
            converter = this.getConverter( field.getType() );

            if ( converter != null ) {
                ParameterizedType parameterizedType = this.evalParameterizedField( field );
                Object value = converter.fromConfig( field.getType(), root.get( path ), parameterizedType );

                // If we're trying to assign a value to a static variable
                // then assure there's the "PreserveStatic" annotation on there!
                if ( Modifier.isStatic( field.getModifiers() ) ) {
                    if ( !field.isAnnotationPresent( PreserveStatic.class ) ) {
                        return this;
                    }

                    if ( !field.getAnnotation( PreserveStatic.class ).value() ) {
                        return this;
                    }

                    if ( converter instanceof PrimitiveConverter && value == null ) {
                        return this;
                    }

                    field.set( null, value );
                    return this;
                }

                if ( converter instanceof PrimitiveConverter && value == null ) {
                    return this;
                }

                field.set( config, value );
                return this;
            }
        }

        // If we're trying to assign a value to a static variable
        // then assure there's the "PreserveStatic" annotation on there!
        if ( Modifier.isStatic( field.getModifiers() ) ) {
            if ( !field.isAnnotationPresent( PreserveStatic.class ) ) {
                return this;
            }

            if ( !field.getAnnotation( PreserveStatic.class ).value() ) {
                return this;
            }

            field.set( null, root.get( path ) );
            return this;
        }

        field.set( config, root.get( path ) );
        return this;
    }

    public InternalConverter toConfig( YamlConfig config, Field field, ConfigSection root, String path ) throws Exception {
        Converter converter;
        Object fieldValue = field.get( config );

        if ( fieldValue != null ) {
            config.resetCommentPrefix( path );

            converter = this.getConverter( fieldValue.getClass() );
            ParameterizedType parameterizedType = this.evalParameterizedField( field );

            if ( converter != null ) {
                root.set( path, converter.toConfig( fieldValue.getClass(), fieldValue, parameterizedType ) );
                return this;
            } else {
                converter = this.getConverter( field.getType() );
                if ( converter != null ) {
                    root.set( path, converter.toConfig( field.getType(), fieldValue, parameterizedType ) );
                    return this;
                }
            }
        }

        root.set( path, fieldValue );
        return this;
    }

    public List<Class<?>> getCustomConverters() {
        return Collections.unmodifiableList( this.customConverters );
    }

    public InternalConverter addCustomConverter( Class<?> addConverter ) throws InvalidConverterException {
        this.addConverter( addConverter );
        this.customConverters.add( addConverter );
        return this;
    }

    protected final ParameterizedType evalParameterizedField( Field field ) {
        return field.getGenericType() instanceof ParameterizedType ? (ParameterizedType) field.getGenericType() : null;
    }

}
