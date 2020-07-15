package io.gomint.server.entity.projectile;

import static io.gomint.math.MathUtils.sqrt;
import static io.gomint.math.MathUtils.square;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import io.gomint.event.entity.EntityDamageEvent.DamageSource;
import io.gomint.event.entity.projectile.ProjectileHitBlocksEvent;
import io.gomint.math.Location;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.Particle;
import io.gomint.world.block.Block;
import io.gomint.world.block.BlockType;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The actual implementation of {@link io.gomint.entity.projectile.EntitySnowball}. Currently adopting
 * gravity, drag and any other properties related to the {@link EntityEnderpearl} projectile.
 *
 * @see io.gomint.entity.projectile.EntitySnowball
 * @author Clockw1seLrd
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:snowball" )
// TODO Perhaps extend "EntityThrowable" instead of "EntityProjectile"
public class EntitySnowball extends EntityProjectile implements io.gomint.entity.projectile.EntitySnowball {

    private static final class Properties {

        private static final float GRAVITY = 0.03f;
        private static final float DRAG = 0.01f;
        private static final float WIDTH = 0.25f; // 0.25 meter
        private static final float HEIGHT = 0.25f; // 0.25 meter
        private static final int MAX_LIFETIME = 1200; // 1200 ticks (1 minute)

        private static void apply( EntitySnowball entity ) {
            entity.GRAVITY = GRAVITY;
            entity.DRAG = DRAG;

            entity.setSize( WIDTH, HEIGHT );
        }

    }

    private static final class VelocityCalculator {

        private static float calculateX( Location position ) {
            // Using local fields to improve readability
            float yaw = (float) -sin( position.getYaw() / 180.0F * PI );
            float pitch = (float) cos( position.getPitch() / 180.0F * (float) PI );

            return ( yaw * pitch ) * 0.4f;
        }

        private static float calculateY( Location position ) {
            // No local fields due to readability is fine
            return (float) ( -sin( position.getPitch() / 180.0F * (float) PI ) * 0.4f );
        }

        private static float calculateZ( Location position ) {
            // Using local fields to improve readability
            float yaw = (float) cos( position.getYaw() / 180.0F * PI );
            float pitch = (float) cos( position.getPitch() / 180.0F * (float) PI );

            return ( yaw * pitch ) * 0.4f;
        }

        private static void calculateDistanceTravelAndApply( Vector vec ) {
            float distanceTravel = sqrt( square( vec.getX() ) + square( vec.getY() ) + square( vec.getZ() ) );

            vec.setX( (float) ( ( ( vec.getX() / distanceTravel ) + ( ThreadLocalRandom.current().nextDouble() * 0.0075f ) ) * 1.5f ) );
            vec.setY( (float) ( ( ( vec.getY() / distanceTravel ) + ( ThreadLocalRandom.current().nextDouble() * 0.0075f ) ) * 1.5f ) );
            vec.setZ( (float) ( ( ( vec.getZ() / distanceTravel ) + ( ThreadLocalRandom.current().nextDouble() * 0.0075f ) ) * 1.5f ) );
        }

    }

    private float lastUpdatedTime;

    /**
     * Create entity for API
     */
    public EntitySnowball() {
        super( null, EntityType.SNOWBALL, null );
        super.setSize( Properties.WIDTH, Properties.HEIGHT );
    }

    /**
     * Constructs a new {@code EntitySnowball} projectile instance.
     *
     * @param shooter Shooter of this projectile
     * @param world   World in which the projectile is being spawned
     */
    public EntitySnowball( EntityLiving shooter, WorldAdapter world ) {
        super( shooter, EntityType.SNOWBALL, world );

        Location startPosition = super.setPositionFromShooter(); // Starting position of snowball projectile
        Vector velocity = new Vector(); // Motion (velocity) of the snowball projectile

        // Applying initial projectile velocity to X, Y and Z
        velocity.setX( VelocityCalculator.calculateX( startPosition ) );
        velocity.setY( VelocityCalculator.calculateY( startPosition ) );
        velocity.setZ( VelocityCalculator.calculateZ( startPosition ) );

        // Calculates distance travel and applies values to local field #velocity
        VelocityCalculator.calculateDistanceTravelAndApply( velocity );
        // Applying calculated velocity to this projectile
        super.setVelocity( velocity );

        Properties.apply( this ); // Applying snowball projectile properties
        super.metadataContainer.putLong( 5, shooter.getEntityId() ); // Set owning entity
    }

    @Override
    public void update( long currentTimeMilliseconds, float deltaTime ) {
        super.update( currentTimeMilliseconds, deltaTime );

        // Check if the snowball projectile has hit an entity yet
        if ( super.hitEntity != null ) {
            super.despawn();
        }

        this.lastUpdatedTime += deltaTime;

        if ( this.lastUpdatedTime >= Values.CLIENT_TICK_RATE ) {
            if ( super.isCollided ) {
                Set<Block> blocks = new HashSet<>( super.collidedWith );
                ProjectileHitBlocksEvent hitBlocksEvent = new ProjectileHitBlocksEvent( blocks, this );
                super.world.getServer().getPluginManager().callEvent( hitBlocksEvent );

                if ( !hitBlocksEvent.isCancelled() ) {
                    super.despawn();
                    this.displaySnowballPoofParticle( super.getLocation() );
                }
            }

            BlockType blockOnPosType = super.getLocation().getBlock().getBlockType();

            // A snowball projectile is set on fire if it goes through lava (but doesn't ignite hit entities)
            if ( blockOnPosType == BlockType.FLOWING_LAVA || blockOnPosType == BlockType.STATIONARY_LAVA ) {
                // Avoid sending metadata updates; If not on fire, update
                if ( !super.isOnFire() ) {
                    super.setOnFire( true );
                }
            }

            // Check if the snowball projectile exceeds max lifetime (See Properties#MAX_LIFETIME)
            if ( this.age >= Properties.MAX_LIFETIME ) {
                this.despawn();
            }
        }
    }

    @Override
    protected void applyCustomDamageEffects( Entity hitEntity ) {
        switch ( hitEntity.getType() ) {
            case BLAZE:
                // Damages Blazes; 3 health points (1.5 hearts)
                ( (EntityLiving) hitEntity ).attack( 3f, DamageSource.PROJECTILE );
                break;
            case ENDER_CRYSTAL:
                // Destroys a ender crystal if hit by a snowball projectile
                // TODO Add implementation for entity "minecraft:ender_crystal"
                break;
        }
    }

    @Override
    public boolean isCritical() {
        return false;
    }

    @Override
    public float getDamage() {
        return 0;
    }

    protected void displaySnowballPoofParticle( Location location ) {
        for ( int i = 0; i < 6; i++ ) {
            super.getWorld().sendParticle( location.add( 0f, 0.5f, 0f ), Particle.SNOWBALL_POOF );
        }
    }

}
