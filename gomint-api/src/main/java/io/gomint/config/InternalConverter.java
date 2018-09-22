package io.gomint.config;

import io.gomint.config.converter.*;
import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
public class InternalConverter {

    private Set<Converter> converters = new LinkedHashSet<>();
    private List<Class> customConverters = new ArrayList<>();
    @Getter
    private final BaseConfig config;

    public InternalConverter( BaseConfig config ) {
        this.config = config;

        try {
            addConverter( PrimitiveConverter.class );
            addConverter( ConfigConverter.class );
            addConverter( ListConverter.class );
            addConverter( MapConverter.class );
            addConverter( ArrayConverter.class );
            addConverter( SetConverter.class );
            addConverter( LocationConverter.class );
            addConverter( VectorConverter.class );
            addConverter( BlockPositionConverter.class );
        } catch ( InvalidConverterException e ) {
            throw new IllegalStateException( e );
        }
    }

    public void addConverter( Class converter ) throws InvalidConverterException {
        if ( !Converter.class.isAssignableFrom( converter ) ) {
            throw new InvalidConverterException( "converter does not implement the Interface converter" );
        }

        try {
            Converter converter1 = (Converter) converter.getConstructor( InternalConverter.class ).newInstance( this );
            converters.add( converter1 );
        } catch ( NoSuchMethodException e ) {
            throw new InvalidConverterException( "converter does not implement a Constructor which takes the InternalConverter instance", e );
        } catch ( InvocationTargetException e ) {
            throw new InvalidConverterException( "converter could not be invoked", e );
        } catch ( InstantiationException e ) {
            throw new InvalidConverterException( "converter could not be instantiated", e );
        } catch ( IllegalAccessException e ) {
            throw new InvalidConverterException( "converter does not implement a public Constructor which takes the InternalConverter instance", e );
        }
    }

    public Converter getConverter( Class type ) {
        for ( Converter converter : converters ) {
            if ( converter.supports( type ) ) {
                return converter;
            }
        }

        return null;
    }

    public void fromConfig( YamlConfig config, Field field, ConfigSection root, String path ) throws Exception {
        Object obj = field.get( config );

        Converter converter;

        if ( obj != null ) {
            converter = getConverter( obj.getClass() );

            if ( converter != null ) {
                Object val = converter.fromConfig( obj.getClass(), root.get( path ), ( field.getGenericType() instanceof ParameterizedType ) ? (ParameterizedType) field.getGenericType() : null );

				/*
					If we're trying to assign a value to a static variable
                    then assure there's the "PreserveStatic" annotation on there!
                     */
                if ( Modifier.isStatic( field.getModifiers() ) ) {
                    if ( !field.isAnnotationPresent( PreserveStatic.class ) ) {
                        return;
                    }

                    PreserveStatic staticConfig = field.getAnnotation( PreserveStatic.class );
                    if ( !staticConfig.value() ) {
                        return;
                    }

                    if ( converter instanceof PrimitiveConverter && val == null ) {
                        return;
                    }

                    field.set( null, val );
                    return;
                }

                if ( converter instanceof PrimitiveConverter && val == null ) {
                    return;
                }

                field.set( config, val );
                return;
            } else {
                converter = getConverter( field.getType() );
                if ( converter != null ) {
                    Object val = converter.fromConfig( field.getType(), root.get( path ), ( field.getGenericType() instanceof ParameterizedType ) ? (ParameterizedType) field.getGenericType() : null );

					/*
					If we're trying to assign a value to a static variable
                    then assure there's the "PreserveStatic" annotation on there!
                     */
                    if ( Modifier.isStatic( field.getModifiers() ) ) {
                        if ( !field.isAnnotationPresent( PreserveStatic.class ) ) {
                            return;
                        }

                        PreserveStatic staticConfig = field.getAnnotation( PreserveStatic.class );
                        if ( !staticConfig.value() ) {
                            return;
                        }

                        if ( converter instanceof PrimitiveConverter && val == null ) {
                            return;
                        }

                        field.set( null, val );
                        return;
                    }

                    if ( converter instanceof PrimitiveConverter && val == null ) {
                        return;
                    }

                    field.set( config, val );
                    return;
                }
            }
        } else {
            converter = getConverter( field.getType() );

            if ( converter != null ) {
                Object val = converter.fromConfig( field.getType(), root.get( path ), ( field.getGenericType() instanceof ParameterizedType ) ? (ParameterizedType) field.getGenericType() : null );

				/*
					If we're trying to assign a value to a static variable
                    then assure there's the "PreserveStatic" annotation on there!
                     */
                if ( Modifier.isStatic( field.getModifiers() ) ) {
                    if ( !field.isAnnotationPresent( PreserveStatic.class ) ) {
                        return;
                    }

                    PreserveStatic staticConfig = field.getAnnotation( PreserveStatic.class );
                    if ( !staticConfig.value() ) {
                        return;
                    }

                    if ( converter instanceof PrimitiveConverter && val == null ) {
                        return;
                    }

                    field.set( null, val );
                    return;
                }

                if ( converter instanceof PrimitiveConverter && val == null ) {
                    return;
                }

                field.set( config, val );
                return;
            }
        }

		/*
		If we're trying to assign a value to a static variable
		then assure there's the "PreserveStatic" annotation on there!
		 */
        if ( Modifier.isStatic( field.getModifiers() ) ) {
            if ( !field.isAnnotationPresent( PreserveStatic.class ) ) {
                return;
            }

            PreserveStatic staticConfig = field.getAnnotation( PreserveStatic.class );
            if ( !staticConfig.value() ) {
                return;
            }

            field.set( null, root.get( path ) );
            return;
        }

        field.set( config, root.get( path ) );
    }

    public void toConfig( YamlConfig config, Field field, ConfigSection root, String path ) throws Exception {
        Object obj = field.get( config );

        Converter converter;

        if ( obj != null ) {
            config.resetCommentPrefix( path );

            converter = getConverter( obj.getClass() );

            if ( converter != null ) {
                root.set( path, converter.toConfig( obj.getClass(), obj, ( field.getGenericType() instanceof ParameterizedType ) ? (ParameterizedType) field.getGenericType() : null ) );
                return;
            } else {
                converter = getConverter( field.getType() );
                if ( converter != null ) {
                    root.set( path, converter.toConfig( field.getType(), obj, ( field.getGenericType() instanceof ParameterizedType ) ? (ParameterizedType) field.getGenericType() : null ) );
                    return;
                }
            }
        }

        root.set( path, obj );
    }

    public List<Class> getCustomConverters() {
        return new ArrayList<>( this.customConverters );
    }

    public void addCustomConverter( Class addConverter ) throws InvalidConverterException {
        addConverter( addConverter );
        this.customConverters.add( addConverter );
    }

}
