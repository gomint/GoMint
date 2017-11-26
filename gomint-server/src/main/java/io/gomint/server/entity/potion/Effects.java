/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion;

import io.gomint.server.entity.potion.effect.Effect;
import io.gomint.server.entity.potion.generator.EffectGenerator;
import io.gomint.server.registry.GeneratorCallback;
import io.gomint.server.registry.Registry;
import javassist.*;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Effects {

    private static final Registry<EffectGenerator> GENERATORS = new Registry<>( new GeneratorCallback<EffectGenerator>() {
        @Override
        public EffectGenerator generate( int id, Class<?> clazz ) {
            // Create generated Generator for this block
            ClassPool pool = ClassPool.getDefault();
            CtClass generatorCT = pool.makeClass( "io.gomint.server.entity.potion.generator." + clazz.getSimpleName() );

            try {
                generatorCT.setInterfaces( new CtClass[]{ pool.get( "io.gomint.server.entity.potion.generator.EffectGenerator" ) } );
            } catch ( NotFoundException e ) {
                e.printStackTrace();
                return null;
            }

            try {
                generatorCT.addMethod( CtNewMethod.make( "public io.gomint.server.entity.potion.effect.Effect generate( int amplifier, long lengthInMS ) { return new " + clazz.getName() + "( amplifier, lengthInMS ); }", generatorCT ) );
            } catch ( CannotCompileException e ) {
                e.printStackTrace();
                return null;
            }

            try {
                // Use the same code source as the Gomint JAR
                return (EffectGenerator) generatorCT.toClass( ClassLoader.getSystemClassLoader(), null ).newInstance();
            } catch ( InstantiationException | IllegalAccessException | CannotCompileException e ) {
                e.printStackTrace();
            }

            return null;
        }
    } );

    static {
        GENERATORS.register( "io.gomint.server.entity.potion.effect" );
    }

    public static Effect generate( int id, int amplifier, long lengthInMS ) {
        EffectGenerator instance = GENERATORS.getGenerator( id );
        if ( instance != null ) {
            return instance.generate( amplifier, lengthInMS );
        }

        return null;
    }

}
