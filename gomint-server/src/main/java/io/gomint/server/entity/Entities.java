/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import io.gomint.entity.Entity;
import io.gomint.server.registry.Generator;
import io.gomint.server.registry.StringRegistry;
import io.gomint.server.util.ClassPath;
import io.gomint.server.util.performance.LambdaConstructionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Entities {

    private static final Logger LOGGER = LoggerFactory.getLogger( Entities.class );
    private final StringRegistry<io.gomint.server.entity.Entity<?>> generators;

    public Entities( ClassPath classPath ) {
        this.generators = new StringRegistry<>( (clazz, id) -> {
            LambdaConstructionFactory<io.gomint.server.entity.Entity<?>> factory = new LambdaConstructionFactory<>(clazz);
            return in -> {
                return factory.newInstance();
            };
        } );

        // Register all subgroups
        this.generators.register( classPath,"io.gomint.server.entity" );
        this.generators.register( classPath,"io.gomint.server.entity.active" );
        this.generators.register( classPath,"io.gomint.server.entity.animal" );
        this.generators.register( classPath,"io.gomint.server.entity.monster" );
        this.generators.register( classPath,"io.gomint.server.entity.passive" );
        this.generators.register( classPath,"io.gomint.server.entity.projectile" );
    }

    public <T extends Entity<T>> T create( Class<T> entityClass ) {
        Generator<T> entityGenerator = (Generator<T>) this.generators.getGenerator( entityClass );
        if ( entityGenerator == null ) {
            return null;
        }

        return (T) entityGenerator.generate();
    }

    public <T extends Entity<T>> T create( String entityId ) {
        Generator<T> entityGenerator = (Generator<T>) this.generators.getGenerator( entityId );
        if ( entityGenerator == null ) {
            LOGGER.warn( "Could not find entity generator for id {}", entityId );
            return null;
        }

        return (T) entityGenerator.generate();
    }

}
