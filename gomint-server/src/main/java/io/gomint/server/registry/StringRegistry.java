/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.registry;

import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class StringRegistry<R> {

    private static final Logger LOGGER = LoggerFactory.getLogger( StringRegistry.class );

    private final ClassPath classPath;
    private final GeneratorCallback<R> generatorCallback;

    private final Map<String, Generator<R>> generators = new HashMap<>();
    private final Map<Class<?>, String> apiReferences = new HashMap<>();

    /**
     * Build a new generator registry
     *
     * @param classPath which reflects the current classes
     * @param callback  which is used to generate a generator for each found element
     */
    public StringRegistry( ClassPath classPath, GeneratorCallback<R> callback ) {
        this.classPath = classPath;
        this.generatorCallback = callback;
    }

    /**
     * Register all classes which can be found in given path
     *
     * @param classPath which should be searched
     */
    public void register( String classPath ) {
        LOGGER.debug( "Going to scan: {}", classPath );

        for ( ClassPath.ClassInfo classInfo : this.classPath.getTopLevelClasses( classPath ) ) {
            register( classInfo.load() );
        }
    }

    private void register( Class<?> clazz ) {
        // Check if we skip registration
        if ( clazz.isAnnotationPresent( SkipRegister.class ) ) {
            return;
        }

        // We need register info
        if ( !clazz.isAnnotationPresent( RegisterInfo.class ) && !clazz.isAnnotationPresent( RegisterInfos.class ) ) {
            LOGGER.warn( "No register info annotation present: {}", clazz.getName() );
            return;
        }


        if ( clazz.isAnnotationPresent( RegisterInfo.class ) ) {
            String id = clazz.getAnnotation( RegisterInfo.class ).sId();
            Generator<R> generator = this.generatorCallback.generate( clazz );
            if ( generator != null ) {
                this.storeGeneratorForId( id, generator );

                // Check for API interfaces
                for ( Class<?> apiInter : clazz.getInterfaces() ) {
                    this.apiReferences.put( apiInter, id );
                }

                this.apiReferences.put( clazz, id );
            }
        } else {
            Generator<R> generator = this.generatorCallback.generate( clazz );
            if ( generator != null ) {
                RegisterInfo[] infos = clazz.getAnnotation( RegisterInfos.class ).value();
                for ( RegisterInfo info : infos ) {
                    String id = info.sId();
                    this.storeGeneratorForId( id, generator );

                    // Check for API interfaces
                    for ( Class<?> apiInter : clazz.getInterfaces() ) {
                        this.apiReferences.put( apiInter, id );
                    }

                    this.apiReferences.put( clazz, id );
                }
            }
        }
    }

    private void storeGeneratorForId( String id, Generator<R> generator ) {
        this.generators.put( id, generator );
    }

    public Generator<R> getGenerator( Class<?> clazz ) {
        // Get the internal ID
        String id = this.apiReferences.getOrDefault( clazz, null );
        if ( id == null ) {
            return null;
        }

        return getGenerator( id );
    }

    public final Generator<R> getGenerator( String id ) {
        return this.generators.get( id );
    }

    public String getId( Class<?> clazz ) {
        return this.apiReferences.getOrDefault( clazz, null );
    }

}
