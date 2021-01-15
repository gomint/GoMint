/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.projectile;

import io.gomint.inventory.item.ItemType;
import io.gomint.math.Location;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:fishing_hook")
public class EntityFishingHook extends EntityProjectile implements io.gomint.entity.projectile.EntityFishingHook {

    private static final Vector WATER_FLOATING_MOTION = new Vector(0, 0.1f, 0);

    private boolean isReset;
    private float lastUpdateDT;

    /**
     * Create entity for API
     */
    public EntityFishingHook() {
        super(null, EntityType.FISHING_HOOK, null);
    }

    /**
     * Construct a new Entity
     *
     * @param player which spawned this hook
     * @param world  The world in which this entity is in
     */
    public EntityFishingHook(EntityPlayer player, WorldAdapter world) {
        super(player, EntityType.FISHING_HOOK, world);

        Location position = this.setPositionFromShooter();

        // Calc new motion
        this.setMotionFromEntity(position, this.shooter.getVelocity(), 0f, 0.4f, 1f);

        // Calculate correct yaw / pitch
        this.setLookFromMotion();

        // Set owning entity (this draws the rod line)
        this.metadataContainer.putLong(5, player.getEntityId());
    }

    @Override
    protected void applyCustomProperties() {
        super.applyCustomProperties();

        // Set size
        this.setSize(0.25f, 0.25f);
    }

    @Override
    protected void setMotionFromHeading(Vector motion, float velocity, float inaccuracy) {
        float distanceTravel = (float) Math.sqrt(MathUtils.square(motion.getX()) + MathUtils.square(motion.getY()) + MathUtils.square(motion.getZ()));
        this.setVelocity(motion.multiply(
            0.6f / distanceTravel + 0.5f + ThreadLocalRandom.current().nextFloat() * 0.0045f,
            0.6f / distanceTravel + 0.5f + ThreadLocalRandom.current().nextFloat() * 0.0045f,
            0.6f / distanceTravel + 0.5f + ThreadLocalRandom.current().nextFloat() * 0.0045f
        ));
    }

    /**
     * Retract the hook to the origin
     *
     * @return damage which should be dealt to the item stack
     */
    public int retract() {
        this.despawn();
        return 2;
    }

    @Override
    public boolean isCritical() {
        return false;
    }

    @Override
    public float getDamage() {
        return 0;
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);

        if (this.shooter.isDead() || ((EntityPlayer) this.shooter).getInventory().itemInHand().itemType() != ItemType.FISHING_ROD) {
            this.despawn();
        }

        // TODO: MJ BUG / 1.2.13 / Fishing hooks get applied noclip and gravity in the client, to circumvent we need to send the position every tick
        this.getTransform().setPosition(this.getPosition());

        this.lastUpdateDT += dT;
        if (Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON) {
            if (this.isCollided && this.isInsideLiquid()) {
                if (!this.getVelocity().equals(WATER_FLOATING_MOTION)) {
                    this.setVelocity(WATER_FLOATING_MOTION);
                }
            } else if (this.isCollided) {
                if (!this.isReset && this.getVelocity().length() < 0.0025) {
                    this.setVelocity(Vector.ZERO);
                    this.isReset = true;
                }
            }
        }
    }

}
