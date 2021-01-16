/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.projectile;

import io.gomint.entity.EntityLiving;
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
import io.gomint.world.block.Block;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:arrow")
public class EntityArrow extends EntityProjectile<io.gomint.entity.projectile.EntityArrow> implements io.gomint.entity.projectile.EntityArrow {

    private boolean firedHitEvent = false;
    private boolean isReset = false;

    private boolean canBePickedup;
    private boolean critical;
    private float lastUpdateDT;

    private int powerModifier;
    private int punchModifier;
    private int flameModifier;

    /**
     * Create entity for API
     */
    public EntityArrow() {
        super(null, EntityType.ARROW, null);
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
    public EntityArrow(EntityPlayer player, WorldAdapter world, float force, int powerModifier, int punchModifier, int flameModifier) {
        super(player, EntityType.ARROW, world);

        this.powerModifier = powerModifier;
        this.punchModifier = punchModifier;
        this.flameModifier = flameModifier;

        this.critical = force == 1.0f;

        Location position = this.positionFromShooter();

        // Calc new motion
        this.motionFromEntity(position, this.shooter.velocity(), 0f, force * 3f, 1f);

        // Calculate correct yaw / pitch
        this.lookFromMotion();

        // Set owning entity
        this.metadataContainer.putLong(5, player.id());
    }

    @Override
    protected void applyCustomProperties() {
        super.applyCustomProperties();

        // Set size
        this.size(0.5f, 0.5f);
    }

    @Override
    public boolean critical() {
        return this.critical;
    }

    @Override
    protected void applyCustomKnockback(Entity<?> hitEntity) {
        if (this.punchModifier > 0) {
            float sqrtMotion = (float) Math.sqrt(this.getMotionX() * this.getMotionX() + this.getMotionZ() * this.getMotionZ());
            if (sqrtMotion > 0.0F) {
                Vector toAdd = new Vector(
                    this.getMotionX() * this.punchModifier * 0.6f / sqrtMotion,
                    0.1f,
                    this.getMotionZ() * this.punchModifier * 0.6f / sqrtMotion
                );

                hitEntity.velocity(hitEntity.velocity().add(toAdd));
            }
        }
    }

    @Override
    protected void applyCustomDamageEffects(Entity<?> hitEntity) {
        if (this.flameModifier > 0 && hitEntity instanceof EntityLiving) {
            ((EntityLiving<?>) hitEntity).burning(5, TimeUnit.SECONDS);
        }
    }

    @Override
    public float damage() {
        if (this.powerModifier > 0) {
            return 2 + (this.powerModifier * 0.5f + 0.5f);
        }

        return 2;
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);

        // Arrows which hit are gone
        if (this.hitEntity != null) {
            this.despawn();
        }

        this.lastUpdateDT += dT;
        if (Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON) {
            // Calculate correct yaw / pitch
            this.lookFromMotion();

            if (this.isCollided && !this.canBePickedup && !this.firedHitEvent) { // this.canBePickedup indicates if a event got cancelled
                // Remap
                Set<Block> blocks = new HashSet<>(this.collidedWith);
                ProjectileHitBlocksEvent hitBlockEvent = new ProjectileHitBlocksEvent(blocks, this);
                this.world.getServer().pluginManager().callEvent(hitBlockEvent);
                if (!hitBlockEvent.cancelled()) {
                    this.canBePickedup = true;
                }
            }

            if (this.canBePickedup && !this.isReset && this.velocity().length() < 0.0025) {
                this.velocity(Vector.ZERO);
                this.isReset = true;
            }

            // Despawn after 1200 ticks ( 1 minute )
            if (this.age >= 1200) {
                this.despawn();
            }

            this.lastUpdateDT = 0;
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if (this.canBePickedup && !this.dead()) {
            ItemArrow arrow = ItemArrow.create(1);

            // Check if we have place in out inventory to store this item
            if (!player.inventory().hasPlaceFor(arrow)) {
                return;
            }

            PlayerPickupItemEvent pickupItemEvent = new PlayerPickupItemEvent(player, this, arrow);
            player.world().getServer().pluginManager().callEvent(pickupItemEvent);
            if (pickupItemEvent.cancelled()) {
                return;
            }

            player.inventory().addItem(arrow);
            this.despawn();
        }
    }

}
