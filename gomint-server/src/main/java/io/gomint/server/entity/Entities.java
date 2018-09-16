/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import com.google.common.reflect.ClassPath;
import io.gomint.entity.Entity;
import io.gomint.server.GoMintServer;
import io.gomint.server.registry.Generator;
import io.gomint.server.registry.Registry;
import io.gomint.server.registry.SkipRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
@SkipRegister
public class Entities {

    private static final Logger LOGGER = LoggerFactory.getLogger( Entities.class );
    private final Registry<io.gomint.server.entity.Entity> generators;

    public Entities( ClassPath classPath ) {
        this.generators = new Registry<>( classPath, clazz -> () -> {
            try {
                return (io.gomint.server.entity.Entity) clazz.newInstance();
            } catch ( InstantiationException | IllegalAccessException e ) {
                LOGGER.error( "Could not generate new entity", e );
            }

            return null;
        } );

        // Register all subgroups
        this.generators.register( "io.gomint.server.entity" );
        this.generators.register( "io.gomint.server.entity.active" );
        this.generators.register( "io.gomint.server.entity.monster" );
        this.generators.register( "io.gomint.server.entity.passive" );
        this.generators.register( "io.gomint.server.entity.projectile" );
    }

    public <T extends Entity> T create( Class<T> entityClass ) {
        Generator<io.gomint.server.entity.Entity> entityGenerator = this.generators.getGenerator( entityClass );
        if ( entityGenerator == null ) {
            return null;
        }

        return (T) entityGenerator.generate();
    }

    public <T extends Entity> T create( int entityId ) {
        Generator<io.gomint.server.entity.Entity> entityGenerator = this.generators.getGenerator( entityId );
        if ( entityGenerator == null ) {
            LOGGER.warn( "Could not find entity generator for id {}", entityId );
            return null;
        }

        return (T) entityGenerator.generate();
    }

}
