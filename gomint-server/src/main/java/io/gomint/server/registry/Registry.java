package io.gomint.server.registry;

import com.google.common.reflect.ClassPath;
import com.koloboke.collect.map.IntObjMap;
import com.koloboke.collect.map.ObjIntMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.koloboke.collect.map.hash.HashObjIntMaps;

import java.io.IOException;
import java.util.Collection;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Registry<R> {

    private final GeneratorCallback<R> generatorCallback;
    private final IntObjMap<R> generators = HashIntObjMaps.newMutableMap();
    private final ObjIntMap<Class<?>> apiReferences = HashObjIntMaps.newMutableMap();

    /**
     * Build a new generator registry
     *
     * @param callback which is used to generate a generator for each found element
     */
    public Registry( GeneratorCallback<R> callback ) {
        this.generatorCallback = callback;
    }

    /**
     * Register all classes which can be found in given path
     *
     * @param classPath which should be searched
     */
    public void register( String classPath ) {
        // Search io.gomint.server.inventory.item
        try {
            for ( ClassPath.ClassInfo classInfo : ClassPath.from( ClassLoader.getSystemClassLoader() )
                    .getTopLevelClasses( classPath) ) {
                register( classInfo.load() );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public void register( Class<?> clazz ) {
        // We need register info
        if ( !clazz.isAnnotationPresent( RegisterInfo.class ) ) {
            return;
        }

        int id = clazz.getAnnotation( RegisterInfo.class ).id();

        R generator = this.generatorCallback.generate( id, clazz );
        if ( generator != null ) {
            this.generators.put( id, generator );

            // Check for API interfaces
            for ( Class<?> apiInter : clazz.getInterfaces() ) {
                apiReferences.put( apiInter, id );
            }

            apiReferences.put( clazz, id );
        }
    }

    public R getGenerator( Class<?> clazz ) {
        // Get the internal ID
        int id = apiReferences.getOrDefault( clazz, -1 );
        if ( id == -1 ) {
            return null;
        }

        return getGenerator( id );
    }

    public R getGenerator( int id ) {
        return generators.get( id );
    }

    public Collection<Integer> getAll() {
        return generators.keySet();
    }

}
