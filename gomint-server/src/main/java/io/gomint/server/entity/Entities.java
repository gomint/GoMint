/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import io.gomint.entity.Entity;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.generator.EntityGenerator;
import io.gomint.server.registry.GeneratorCallback;
import io.gomint.server.registry.Registry;
import io.gomint.server.entity.generator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Entities {

    private static final Logger LOGGER = LoggerFactory.getLogger( Entities.class );
    private final Registry<EntityGenerator> generators;

    public Entities( GoMintServer server ) {
        this.generators = new Registry<>( server, new GeneratorCallback<EntityGenerator>() {
            @Override
            public EntityGenerator generate( Class<?> clazz ) {
                System.out.println( "this.generators.register( " + clazz.getName() + ".class, new " + clazz.getSimpleName() + "Generator() );");


                try {
                    return (EntityGenerator) ClassLoader.getSystemClassLoader().loadClass( "io.gomint.server.entity.generator." + clazz.getSimpleName() + "Generator" ).newInstance();
                } catch ( InstantiationException | IllegalAccessException | ClassNotFoundException e ) {
                    e.printStackTrace();
                }

                return null;
            }
        } );

        //this.generators.register( "io.gomint.server.entity" );
        //this.generators.register( "io.gomint.server.entity.active" );
        //this.generators.register( "io.gomint.server.entity.monster" );
        //this.generators.register( "io.gomint.server.entity.passive" );
        //this.generators.register( "io.gomint.server.entity.projectile" );

        this.generators.register( io.gomint.server.entity.active.EntityPrimedTNT.class, new EntityPrimedTNTGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityBlaze.class, new EntityBlazeGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityCaveSpider.class, new EntityCaveSpiderGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityCreeper.class, new EntityCreeperGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityElderGuardian.class, new EntityElderGuardianGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityEnderDragon.class, new EntityEnderDragonGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityEnderman.class, new EntityEndermanGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityEndermite.class, new EntityEndermiteGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityGhast.class, new EntityGhastGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityGuardian.class, new EntityGuardianGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityHusk.class, new EntityHuskGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityMagmaCube.class, new EntityMagmaCubeGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityPolarBear.class, new EntityPolarBearGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityShulker.class, new EntityShulkerGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntitySilverfish.class, new EntitySilverfishGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntitySkeleton.class, new EntitySkeletonGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntitySlime.class, new EntitySlimeGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntitySpider.class, new EntitySpiderGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityStray.class, new EntityStrayGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityVex.class, new EntityVexGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityWitch.class, new EntityWitchGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityWither.class, new EntityWitherGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityWitherSkeleton.class, new EntityWitherSkeletonGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityZombie.class, new EntityZombieGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityZombiePigman.class, new EntityZombiePigmanGenerator() );
        this.generators.register( io.gomint.server.entity.monster.EntityZombieVillager.class, new EntityZombieVillagerGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityArmorStand.class, new EntityArmorStandGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityBat.class, new EntityBatGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityChicken.class, new EntityChickenGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityCow.class, new EntityCowGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityDonkey.class, new EntityDonkeyGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityFallingBlock.class, new EntityFallingBlockGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityHorse.class, new EntityHorseGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityHuman.class, new EntityHumanGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityLama.class, new EntityLamaGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityMooshroom.class, new EntityMooshroomGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityMule.class, new EntityMuleGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityOcelot.class, new EntityOcelotGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityPig.class, new EntityPigGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityRabbit.class, new EntityRabbitGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntitySheep.class, new EntitySheepGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntitySkeletonHorse.class, new EntitySkeletonHorseGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntitySquid.class, new EntitySquidGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityVillager.class, new EntityVillagerGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityWolf.class, new EntityWolfGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityXPOrb.class, new EntityXPOrbGenerator() );
        this.generators.register( io.gomint.server.entity.passive.EntityZombieHorse.class, new EntityZombieHorseGenerator() );
        this.generators.register( io.gomint.server.entity.projectile.EntityExpBottle.class, new EntityExpBottleGenerator() );
    }

    public <T extends Entity> T create( Class<T> entityClass ) {
        EntityGenerator entityGenerator = this.generators.getGenerator( entityClass );
        if ( entityGenerator == null ) {
            return null;
        }

        return entityGenerator.generate();
    }

    public <T extends Entity> T create( int entityId ) {
        EntityGenerator entityGenerator = this.generators.getGenerator( entityId );
        if ( entityGenerator == null ) {
            LOGGER.warn( "Could not find entity generator for id {}", entityId );
            return null;
        }

        return entityGenerator.generate();
    }

}
