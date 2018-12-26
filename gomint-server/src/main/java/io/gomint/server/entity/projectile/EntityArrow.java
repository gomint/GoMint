/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.projectile;

import io.gomint.entity.EntityLiving;
import io.gomint.entity.potion.PotionEffect;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.entity.projectile.ProjectileHitBlocksEvent;
import io.gomint.event.player.PlayerPickupItemEvent;
import io.gomint.inventory.item.ItemArrow;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.util.random.FastRandom;
import io.gomint.world.block.Block;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:arrow" )
public class EntityArrow extends EntityProjectile implements io.gomint.entity.projectile.EntityArrow {

    private boolean firedHitEvent = false;
    private boolean isReset = false;

    private boolean canBePickedup;
    private boolean critical;
    private float lastUpdateDT;

    private int powerModifier;
    private int punchModifier;
    private int flameModifier;

    // Ids for the item arrow to apply given effects if the arrow collides with a player
    private static final int NORMAL = 0;
    private static final int NIGHT_VISION = 6;
    private static final int NIGHT_VISION_LONG = 7;
    private static final int INVISIBILITY = 8;
    private static final int INVISIBILITY_LONG = 9;
    private static final int LEAPING = 10;
    private static final int LEAPING_LONG = 11;
    private static final int LEAPING_II = 12;
    private static final int FIRE_RESISTANCE = 13;
    private static final int FIRE_RESISTANCE_LONG = 14;
    private static final int SWIFTNESS = 15;
    private static final int SWIFTNESS_LONG = 16;
    private static final int SWIFTNESS_II = 17;
    private static final int SLOWNESS = 18;
    private static final int SLOWNESS_LONG = 19;
    private static final int WATER_BREATHING = 20;
    private static final int WATER_BREATHING_LONG = 21;
    private static final int HEALING = 22;
    private static final int HEALING_II = 23;
    private static final int HARMING = 24;
    private static final int HARMING_II = 25;
    private static final int POISON = 26;
    private static final int POISON_LONG = 27;
    private static final int POISON_II = 28;
    private static final int REGENERATION = 29;
    private static final int REGENERATION_LONG = 30;
    private static final int REGENERATION_II = 31;
    private static final int STRENGTH = 32;
    private static final int STRENGTH_LONG = 33;
    private static final int STRENGTH_II = 34;
    private static final int WEAKNESS = 35;
    private static final int WEAKNESS_LONG = 36;
    private static final int DECAY = 37;
    private static final int TURTLE_MASTER = 38;
    private static final int TURTLE_MASTER_LONG = 39;
    private static final int TURTLE_MASTER_II = 40;

    /**
     * Create entity for API
     */
    public EntityArrow() {
        super( null, EntityType.ARROW, null );
        this.setSize( 0.5f, 0.5f );
    }

    /**
     * Construct a new Entity
     *
     * @param player        which spawned this hook
     * @param world         The world in which this entity is in
     * @param force         with which the player shoot the bow
     * @param powerModifier modifier for damage of this arrow
     * @param punchModifier modifier for knockback of this arrow
     * @param flameModifier modifier for burning arrows
     */
    public EntityArrow( EntityPlayer player, WorldAdapter world, float force, int powerModifier, int punchModifier, int flameModifier ) {
        super( player, EntityType.ARROW, world );
        this.setSize( 0.5f, 0.5f );

        this.powerModifier = powerModifier;
        this.punchModifier = punchModifier;
        this.flameModifier = flameModifier;

        this.critical = force == 1.0f;
        float applyForce = force * 2;

        Location position = this.setPositionFromShooter();

        // Calculate motion
        Vector motion = new Vector(
            (float) ( -Math.sin( position.getYaw() / 180.0F * Math.PI ) * Math.cos( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f ),
            (float) ( -Math.sin( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f ),
            (float) ( Math.cos( position.getYaw() / 180.0F * Math.PI ) * Math.cos( position.getPitch() / 180.0F * (float) Math.PI ) * 0.4f )
        );

        float distanceTravel = (float) Math.sqrt( MathUtils.square( motion.getX() ) + MathUtils.square( motion.getY() ) + MathUtils.square( motion.getZ() ) );
        motion.setX( (float) ( ( ( motion.getX() / distanceTravel ) + ( FastRandom.current().nextDouble() * ( FastRandom.current().nextBoolean() ? -1 : 1 ) * 0.0075f ) ) * ( applyForce * 1.5f ) ) );
        motion.setY( (float) ( ( ( motion.getY() / distanceTravel ) + ( FastRandom.current().nextDouble() * ( FastRandom.current().nextBoolean() ? -1 : 1 ) * 0.0075f ) ) * ( applyForce * 1.5f ) ) );
        motion.setZ( (float) ( ( ( motion.getZ() / distanceTravel ) + ( FastRandom.current().nextDouble() * ( FastRandom.current().nextBoolean() ? -1 : 1 ) * 0.0075f ) ) * ( applyForce * 1.5f ) ) );
        this.setVelocity( motion );

        // Calculate correct yaw / pitch
        double motionDistance = MathUtils.square( motion.getX() ) + MathUtils.square( motion.getZ() );
        float motionForce = (float) Math.sqrt( motionDistance );

        float yaw = (float) ( Math.atan2( motion.getX(), motion.getZ() ) * 180.0D / Math.PI );
        float pitch = (float) ( Math.atan2( motion.getY(), (double) motionForce ) * 180.0D / Math.PI );

        this.setYaw( yaw );
        this.setHeadYaw( yaw );
        this.setPitch( pitch );

        // Set owning entity
        this.metadataContainer.putLong( 5, player.getEntityId() );
    }

    @Override
    public boolean isCritical() {
        return this.critical;
    }

    @Override
    protected void applyCustomKnockback( Entity hitEntity ) {
        if ( this.punchModifier > 0 ) {
            float sqrtMotion = (float) Math.sqrt( this.getMotionX() * this.getMotionX() + this.getMotionZ() * this.getMotionZ() );
            if ( sqrtMotion > 0.0F ) {
                Vector toAdd = new Vector(
                    this.getMotionX() * this.punchModifier * 0.6f / sqrtMotion,
                    0.1f,
                    this.getMotionZ() * this.punchModifier * 0.6f / sqrtMotion
                );

                hitEntity.setVelocity( hitEntity.getVelocity().add( toAdd ) );
            }
        }
    }

    @Override
    protected void applyCustomDamageEffects( Entity hitEntity ) {
        if ( this.flameModifier > 0 && hitEntity instanceof EntityLiving ) {
            ( (EntityLiving) hitEntity ).setBurning( 5, TimeUnit.SECONDS );
        }
    }

    @Override
    public float getDamage() {
        if ( this.powerModifier > 0 ) {
            return 2 + ( this.powerModifier * 0.5f + 0.5f );
        }

        return 2;
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );

        // Arrows which hit are gone
        if ( this.hitEntity != null ) {
            this.despawn();
        }

        this.lastUpdateDT += dT;
        if ( Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON ) {
            if ( this.isCollided && !this.canBePickedup && !this.firedHitEvent ) { // this.canBePickedup indicates if a event got cancelled
                // Remap
                Set<Block> blocks = new HashSet<>( this.collidedWith );
                ProjectileHitBlocksEvent hitBlockEvent = new ProjectileHitBlocksEvent( blocks, this );
                this.world.getServer().getPluginManager().callEvent( hitBlockEvent );
                if ( !hitBlockEvent.isCancelled() ) {
                    this.canBePickedup = true;
                }
            }

            if ( this.canBePickedup && !this.isReset && this.getVelocity().length() < 0.0025 ) {
                this.setVelocity( Vector.ZERO );
                this.isReset = true;
            }

            // Despawn after 1200 ticks ( 1 minute )
            if ( this.age >= 1200 ) {
                this.despawn();
            }

            this.lastUpdateDT = 0;
        }
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        if ( this.canBePickedup && !this.isDead() ) {
            ItemArrow arrow = ItemArrow.create( 1 );

            switch ( arrow.getData() ) {
                case NORMAL:
                    break;
                case NIGHT_VISION:
                    arrow.setData( (short) NIGHT_VISION );
                    player.addEffect( PotionEffect.NIGHT_VISION, 0, 22, TimeUnit.SECONDS );
                    break;
                case NIGHT_VISION_LONG:
                    arrow.setData( (short) NIGHT_VISION_LONG );
                    player.addEffect( PotionEffect.NIGHT_VISION, 0, 1, TimeUnit.MINUTES );
                    break;
                case INVISIBILITY:
                    arrow.setData( (short) INVISIBILITY );
                    player.addEffect( PotionEffect.INVISIBILITY, 0, 22, TimeUnit.SECONDS );
                    break;
                case INVISIBILITY_LONG:
                    arrow.setData( (short) INVISIBILITY_LONG );
                    player.addEffect( PotionEffect.INVISIBILITY, 0, 1, TimeUnit.MINUTES );
                    break;
                case LEAPING:
                    arrow.setData( (short) LEAPING );
                    player.addEffect( PotionEffect.JUMP, 0, 22, TimeUnit.SECONDS );
                    break;
                case LEAPING_LONG:
                    arrow.setData( (short) LEAPING_LONG );
                    player.addEffect( PotionEffect.JUMP, 0, 1, TimeUnit.MINUTES );
                    break;
                case LEAPING_II:
                    arrow.setData( (short) LEAPING_II );
                    player.addEffect( PotionEffect.JUMP, 1, 11, TimeUnit.SECONDS );
                    break;
                case FIRE_RESISTANCE:
                    arrow.setData( (short) FIRE_RESISTANCE );
                    player.addEffect( PotionEffect.FIRE_RESISTANCE, 0, 22, TimeUnit.SECONDS );
                    break;
                case FIRE_RESISTANCE_LONG:
                    arrow.setData( (short) FIRE_RESISTANCE_LONG );
                    player.addEffect( PotionEffect.FIRE_RESISTANCE, 0, 1, TimeUnit.MINUTES );
                    break;
                case SWIFTNESS:
                    arrow.setData( (short) SWIFTNESS );
                    player.addEffect( PotionEffect.SPEED, 0, 22, TimeUnit.SECONDS );
                    break;
                case SWIFTNESS_LONG:
                    arrow.setData( (short) SWIFTNESS_LONG );
                    player.addEffect( PotionEffect.SPEED, 0, 1, TimeUnit.MINUTES );
                    break;
                case SWIFTNESS_II:
                    arrow.setData( (short) SWIFTNESS_II );
                    player.addEffect( PotionEffect.SPEED, 1, 11, TimeUnit.SECONDS );
                    break;
                case SLOWNESS:
                    arrow.setData( (short) SLOWNESS );
                    player.addEffect( PotionEffect.SLOWNESS, 0, 11, TimeUnit.SECONDS );
                    break;
                case SLOWNESS_LONG:
                    arrow.setData( (short) SLOWNESS_LONG );
                    player.addEffect( PotionEffect.SLOWNESS, 0, 30, TimeUnit.SECONDS );
                    break;
                case WATER_BREATHING:
                    arrow.setData( (short) WATER_BREATHING );
                    player.addEffect( PotionEffect.WATER_BREATHING, 0, 22, TimeUnit.SECONDS );
                    break;
                case WATER_BREATHING_LONG:
                    arrow.setData( (short) WATER_BREATHING_LONG );
                    player.addEffect( PotionEffect.WATER_BREATHING, 0, 1, TimeUnit.MINUTES );
                    break;
                case HEALING:
                    arrow.setData( (short) HEALING );
                    player.setHealth( player.getHealth() + 8 );
                    break;
                case HEALING_II:
                    arrow.setData( (short) HEALING_II );
                    player.setHealth( player.getHealth() + 16 );
                    break;
                case HARMING:
                    arrow.setData( (short) HARMING );
                    player.attack( 8, EntityDamageEvent.DamageSource.HARM_EFFECT );
                    break;
                case HARMING_II:
                    arrow.setData( (short) HARMING_II );
                    player.attack( 16, EntityDamageEvent.DamageSource.HARM_EFFECT );
                    break;
                case POISON:
                    arrow.setData( (short) POISON );
                    player.addEffect( PotionEffect.POISON, 0, 5, TimeUnit.SECONDS );
                    break;
                case POISON_LONG:
                    arrow.setData( (short) POISON_LONG );
                    player.addEffect( PotionEffect.POISON, 0, 15, TimeUnit.SECONDS );
                    break;
                case POISON_II:
                    arrow.setData( (short) POISON_II );
                    player.addEffect( PotionEffect.POISON, 1, 2, TimeUnit.SECONDS );
                    break;
                case REGENERATION:
                    arrow.setData( (short) REGENERATION );
                    player.addEffect( PotionEffect.REGENERATION, 0, 5, TimeUnit.SECONDS );
                    break;
                case REGENERATION_LONG:
                    arrow.setData( (short) REGENERATION_LONG );
                    player.addEffect( PotionEffect.REGENERATION, 0, 15, TimeUnit.SECONDS );
                    break;
                case REGENERATION_II:
                    arrow.setData( (short) REGENERATION_II );
                    player.addEffect( PotionEffect.REGENERATION, 1, 2, TimeUnit.SECONDS );
                    break;
                case STRENGTH:
                    arrow.setData( (short) STRENGTH );
                    player.addEffect( PotionEffect.STRENGTH, 0, 22, TimeUnit.SECONDS );
                    break;
                case STRENGTH_LONG:
                    arrow.setData( (short) STRENGTH_LONG );
                    player.addEffect( PotionEffect.STRENGTH, 0, 1, TimeUnit.MINUTES );
                    break;
                case STRENGTH_II:
                    arrow.setData( (short) STRENGTH_II );
                    player.addEffect( PotionEffect.STRENGTH, 1, 11, TimeUnit.SECONDS );
                    break;
                case WEAKNESS:
                    arrow.setData( (short) WEAKNESS );
                    player.addEffect( PotionEffect.WEAKNESS, 0, 11, TimeUnit.SECONDS );
                    break;
                case WEAKNESS_LONG:
                    arrow.setData( (short) WEAKNESS_LONG );
                    player.addEffect( PotionEffect.WEAKNESS, 0, 30, TimeUnit.SECONDS );
                    break;
                case DECAY:
                    arrow.setData( (short) DECAY );
                    player.addEffect( PotionEffect.WITHER, 0, 5, TimeUnit.SECONDS );
                    break;
                case TURTLE_MASTER:
                    arrow.setData( (short) TURTLE_MASTER );
                    player.addEffect( PotionEffect.SLOWNESS, 3, 2, TimeUnit.SECONDS );
                    player.addEffect( PotionEffect.DAMAGE_RESISTANCE, 2, 2, TimeUnit.SECONDS );
                    break;
                case TURTLE_MASTER_LONG:
                    arrow.setData( (short) TURTLE_MASTER_LONG );
                    player.addEffect( PotionEffect.SLOWNESS, 3, 5, TimeUnit.SECONDS );
                    player.addEffect( PotionEffect.DAMAGE_RESISTANCE, 2, 5, TimeUnit.SECONDS );
                    break;
                case TURTLE_MASTER_II:
                    arrow.setData( (short) TURTLE_MASTER_II );
                    player.addEffect( PotionEffect.SLOWNESS, 5, 2, TimeUnit.SECONDS );
                    player.addEffect( PotionEffect.DAMAGE_RESISTANCE, 3, 2, TimeUnit.SECONDS );
                    break;
            }

            // Check if we have place in out inventory to store this item
            if ( !player.getInventory().hasPlaceFor( arrow ) ) {
                return;
            }

            PlayerPickupItemEvent pickupItemEvent = new PlayerPickupItemEvent( player, this, arrow );
            player.getWorld().getServer().getPluginManager().callEvent( pickupItemEvent );
            if ( pickupItemEvent.isCancelled() ) {
                return;
            }

            player.getInventory().addItem( arrow );
            this.despawn();
        }
    }

}
