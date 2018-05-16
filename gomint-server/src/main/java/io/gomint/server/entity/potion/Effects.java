/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.potion;

import io.gomint.server.GoMintServer;
import io.gomint.server.entity.potion.effect.*;
import io.gomint.server.entity.potion.generator.EffectGenerator;
import io.gomint.server.player.EffectManager;
import io.gomint.server.registry.GeneratorCallback;
import io.gomint.server.registry.Registry;
import io.gomint.server.entity.potion.generator.*;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Effects {

    private final Registry<EffectGenerator> generators;

    public Effects( GoMintServer server ) {
        this.generators = new Registry<>( server, clazz -> {
            System.out.println( "this.generators.register( " + clazz.getSimpleName() + ".class, new " + clazz.getSimpleName() + "Generator() );");

            try {
                // Use the same code source as the Gomint JAR
                return (EffectGenerator) ClassLoader.getSystemClassLoader().loadClass( "io.gomint.server.entity.potion.generator." + clazz.getSimpleName() + "Generator" ).newInstance();
            } catch ( InstantiationException | IllegalAccessException | ClassNotFoundException e ) {
                e.printStackTrace();
            }

            return null;
        } );

        // this.generators.register( "io.gomint.server.entity.potion.effect" );

        this.generators.register( Absorption.class, new AbsorptionGenerator() );
        this.generators.register( DamageReduction.class, new DamageReductionGenerator() );
        this.generators.register( Harm.class, new HarmGenerator() );
        this.generators.register( Haste.class, new HasteGenerator() );
        this.generators.register( Healing.class, new HealingGenerator() );
        this.generators.register( Invisibility.class, new InvisibilityGenerator() );
        this.generators.register( Jump.class, new JumpGenerator() );
        this.generators.register( MiningFatigue.class, new MiningFatigueGenerator() );
        this.generators.register( Nausea.class, new NauseaGenerator() );
        this.generators.register( Regeneration.class, new RegenerationGenerator() );
        this.generators.register( Slowness.class, new SlownessGenerator() );
        this.generators.register( Speed.class, new SpeedGenerator() );
        this.generators.register( Strength.class, new StrengthGenerator() );
        this.generators.register( WaterBreathing.class, new WaterBreathingGenerator() );
    }

    public Effect generate( int id, int amplifier, long lengthInMS, EffectManager manager ) {
        EffectGenerator instance = this.generators.getGenerator( id );
        if ( instance != null ) {
            return instance.generate( manager, amplifier, lengthInMS );
        }

        return null;
    }

}
