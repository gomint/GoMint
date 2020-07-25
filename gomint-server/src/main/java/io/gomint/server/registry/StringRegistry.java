/*
 * Copyright (c) 2018, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.registry;

import io.gomint.server.event.EventProxy;
import io.gomint.server.util.ClassPath;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.Label;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.cglib.core.Constants;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class StringRegistry<R> {

    private static final Logger LOGGER = LoggerFactory.getLogger( StringRegistry.class );

    private ClassPath classPath;
    private final GeneratorCallback<R> generatorCallback;

    private final Object2ObjectMap<String, Generator<R>> generators = new Object2ObjectOpenHashMap<>();
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

        this.classPath.getTopLevelClasses( classPath, classInfo -> register( classInfo.load() ) );
    }

    private void register( Class<? extends R> clazz ) {
        for (RegisterInfo info : clazz.getAnnotationsByType(RegisterInfo.class)) {
            Generator<R> generator = this.generatorCallback.generate( clazz, info.sId() );
            if ( generator != null ) {
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

    private void storeGeneratorForId( String id, Generator<R> generator ) {
        if ( this.generators.containsKey( id ) ) {
            LOGGER.warn( "Detected hash collision for {}", id );
        } else {
            this.generators.put( id, generator );
        }
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

    public void cleanup() {
        this.classPath = null;
    }

}
