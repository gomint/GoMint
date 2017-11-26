/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import io.gomint.entity.Entity;
import io.gomint.server.entity.generator.EntityGenerator;
import io.gomint.server.registry.GeneratorCallback;
import io.gomint.server.registry.Registry;
import javassist.*;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Entities {

    private static final Registry<EntityGenerator> GENERATORS = new Registry<>( new GeneratorCallback<EntityGenerator>() {
        private ClassPool pool = ClassPool.getDefault();
        private CtClass inter;

        @Override
        public EntityGenerator generate( int id, Class<?> clazz ) {
            if ( this.inter == null ) {
                try {
                    this.inter = pool.get( "io.gomint.server.entity.generator.EntityGenerator" );
                } catch ( NotFoundException e ) {
                    e.printStackTrace();
                }
            }

            CtClass generatorClass = this.pool.makeClass( "io.gomint.server.entity.generator." + clazz.getSimpleName() );
            generatorClass.addInterface( this.inter );

            // Generate the generation method
            try {
                generatorClass.addMethod( CtNewMethod.make( "public io.gomint.entity.Entity generate() {" +
                    "return new " + clazz.getName() + "();" +
                    "}", generatorClass ) );
            } catch ( CannotCompileException e ) {
                e.printStackTrace();
                return null;
            }

            try {
                return (EntityGenerator) generatorClass.toClass().newInstance();
            } catch ( InstantiationException | IllegalAccessException | CannotCompileException e ) {
                e.printStackTrace();
            }

            return null;
        }
    } );

    static {
        GENERATORS.registerRecursive( "io.gomint.server.entity" );
    }

    public static <T extends Entity> T create( Class<T> entityClass ) {
        EntityGenerator entityGenerator = GENERATORS.getGenerator( entityClass );
        if ( entityGenerator == null ) {
            return null;
        }

        return entityGenerator.generate();
    }

}
