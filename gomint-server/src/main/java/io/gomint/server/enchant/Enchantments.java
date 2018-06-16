/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

import io.gomint.server.GoMintServer;
import io.gomint.server.enchant.generator.EnchantmentGenerator;
import io.gomint.server.registry.Registry;
import io.gomint.server.enchant.generator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Enchantments {

    private static final Logger LOGGER = LoggerFactory.getLogger( Enchantments.class );
    private final Registry<EnchantmentGenerator> generators;

    public Enchantments( GoMintServer server ) {
        this.generators =  new Registry<>( server, clazz -> {
            System.out.println( "this.generators.register( " + clazz.getSimpleName() + ".class, new " + clazz.getSimpleName() + "Generator() );");

            try {
                return (EnchantmentGenerator)ClassLoader.getSystemClassLoader().loadClass( "io.gomint.server.enchant.generator." + clazz.getSimpleName() + "Generator" ).newInstance();
            } catch ( ClassNotFoundException | IllegalAccessException | InstantiationException e1 ) {
                LOGGER.error( "Could not use pre generated generator: ", e1 );
            }

            return null;
        } );

        // this.generators.register( "io.gomint.server.enchant" );

        this.generators.register( EnchantmentAquaAffinity.class, new EnchantmentAquaAffinityGenerator() );
        this.generators.register( EnchantmentBaneOfArthopods.class, new EnchantmentBaneOfArthopodsGenerator() );
        this.generators.register( EnchantmentBlastProtection.class, new EnchantmentBlastProtectionGenerator() );
        this.generators.register( EnchantmentDepthStrider.class, new EnchantmentDepthStriderGenerator() );
        this.generators.register( EnchantmentEfficiency.class, new EnchantmentEfficiencyGenerator() );
        this.generators.register( EnchantmentFeatherfalling.class, new EnchantmentFeatherfallingGenerator() );
        this.generators.register( EnchantmentFireAspect.class, new EnchantmentFireAspectGenerator() );
        this.generators.register( EnchantmentFireProtection.class, new EnchantmentFireProtectionGenerator() );
        this.generators.register( EnchantmentFlame.class, new EnchantmentFlameGenerator() );
        this.generators.register( EnchantmentFortune.class, new EnchantmentFortuneGenerator() );
        this.generators.register( EnchantmentFrostWalker.class, new EnchantmentFrostWalkerGenerator() );
        this.generators.register( EnchantmentInfinity.class, new EnchantmentInfinityGenerator() );
        this.generators.register( EnchantmentKnockback.class, new EnchantmentKnockbackGenerator() );
        this.generators.register( EnchantmentLooting.class, new EnchantmentLootingGenerator() );
        this.generators.register( EnchantmentLuckOfTheSea.class, new EnchantmentLuckOfTheSeaGenerator() );
        this.generators.register( EnchantmentLure.class, new EnchantmentLureGenerator() );
        this.generators.register( EnchantmentMending.class, new EnchantmentMendingGenerator() );
        this.generators.register( EnchantmentPower.class, new EnchantmentPowerGenerator() );
        this.generators.register( EnchantmentProjectileProtection.class, new EnchantmentProjectileProtectionGenerator() );
        this.generators.register( EnchantmentProtection.class, new EnchantmentProtectionGenerator() );
        this.generators.register( EnchantmentPunch.class, new EnchantmentPunchGenerator() );
        this.generators.register( EnchantmentRespiration.class, new EnchantmentRespirationGenerator() );
        this.generators.register( EnchantmentSharpness.class, new EnchantmentSharpnessGenerator() );
        this.generators.register( EnchantmentSilkTouch.class, new EnchantmentSilkTouchGenerator() );
        this.generators.register( EnchantmentSmite.class, new EnchantmentSmiteGenerator() );
        this.generators.register( EnchantmentThorns.class, new EnchantmentThornsGenerator() );
        this.generators.register( EnchantmentUnbreaking.class, new EnchantmentUnbreakingGenerator() );
    }

    /**
     * Create enchantment
     *
     * @param id  of the enchantment
     * @param lvl of the enchantment
     * @return new enchantment instance which contains level data
     */
    public Enchantment create( short id, short lvl ) {
        EnchantmentGenerator enchantmentGenerator = this.generators.getGenerator( id );
        if ( enchantmentGenerator == null ) {
            LOGGER.warn( "Unknown enchant {}", id );
            return null;
        }

        return enchantmentGenerator.generate( lvl );
    }

    public short getId( Class<? extends io.gomint.enchant.Enchantment> clazz ) {
        return (short) this.generators.getId( clazz );
    }

}
