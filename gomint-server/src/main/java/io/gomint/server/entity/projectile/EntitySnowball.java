package io.gomint.server.entity.projectile;

import io.gomint.event.entity.EntityDamageEvent.DamageSource;
import io.gomint.event.entity.projectile.ProjectileHitBlocksEvent;
import io.gomint.math.Location;
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

/**
 * The actual implementation of {@link io.gomint.entity.projectile.EntitySnowball}. Currently adopting
 * gravity, drag and any other properties related to the {@link EntityEnderpearl} projectile.
 *
 * @author Clockw1seLrd
 * @version 1.0
 * @see io.gomint.entity.projectile.EntitySnowball
 */
@RegisterInfo(sId = "minecraft:snowball")
public class EntitySnowball extends EntityThrowable implements io.gomint.entity.projectile.EntitySnowball {

    private float lastUpdatedTime;

    /**
     * Create entity for API
     */
    public EntitySnowball() {
        super(null, EntityType.SNOWBALL, null);
    }

    /**
     * Constructs a new {@code EntitySnowball} projectile instance.
     *
     * @param shooter Shooter of this projectile
     * @param world   World in which the projectile is being spawned
     */
    public EntitySnowball(EntityLiving shooter, WorldAdapter world) {
        super(shooter, EntityType.SNOWBALL, world);

        Location position = super.setPositionFromShooter(); // Starting position of snowball projectile

        // Calculate motion
        this.setMotionFromEntity(position, this.shooter.getVelocity(), 0f, 1.5f, 1f);

        // Calculate correct yaw / pitch
        this.setLookFromMotion();

        super.metadataContainer.putLong(5, shooter.getEntityId()); // Set owning entity
    }

    @Override
    public void update(long currentTimeMilliseconds, float deltaTime) {
        super.update(currentTimeMilliseconds, deltaTime);

        // Check if the snowball projectile has hit an entity yet
        if (super.hitEntity != null) {
            super.despawn();
        }

        this.lastUpdatedTime += deltaTime;

        if (this.lastUpdatedTime >= Values.CLIENT_TICK_RATE) {
            if (super.isCollided) {
                Set<Block> blocks = new HashSet<>(super.collidedWith);
                ProjectileHitBlocksEvent hitBlocksEvent = new ProjectileHitBlocksEvent(blocks, this);
                super.world.getServer().getPluginManager().callEvent(hitBlocksEvent);

                if (!hitBlocksEvent.isCancelled()) {
                    super.despawn();
                    this.displaySnowballPoofParticle(super.getLocation());
                }
            }

            BlockType blockOnPosType = super.getLocation().getBlock().getBlockType();

            // A snowball projectile is set on fire if it goes through lava (but doesn't ignite hit entities)
            if (blockOnPosType == BlockType.FLOWING_LAVA || blockOnPosType == BlockType.STATIONARY_LAVA) {
                // Avoid sending metadata updates; If not on fire, update
                if (!super.isOnFire()) {
                    super.setOnFire(true);
                }
            }

            // Check if the snowball projectile exceeds max lifetime
            if (this.age >= 1200) {
                this.despawn();
            }

            // Update yaw and pitch if neede
            this.setLookFromMotion();
        }
    }

    @Override
    protected void applyCustomDamageEffects(Entity hitEntity) {
        switch (hitEntity.getType()) {
            case BLAZE:
                // Damages Blazes; 3 health points (1.5 hearts)
                ((EntityLiving) hitEntity).attack(3f, DamageSource.PROJECTILE);
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

    protected void displaySnowballPoofParticle(Location location) {
        for (int i = 0; i < 6; i++) {
            super.getWorld().sendParticle(location.add(0f, 0.5f, 0f), Particle.SNOWBALL_POOF);
        }
    }

}
