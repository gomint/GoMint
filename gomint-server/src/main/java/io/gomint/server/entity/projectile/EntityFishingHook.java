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
public class EntityFishingHook extends EntityProjectile<io.gomint.entity.projectile.EntityFishingHook> implements io.gomint.entity.projectile.EntityFishingHook {

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

        Location position = this.positionFromShooter();

        // Calc new motion
        this.motionFromEntity(position, this.shooter.velocity(), 0f, 0.4f, 1f);

        // Calculate correct yaw / pitch
        this.lookFromMotion();

        // Set owning entity (this draws the rod line)
        this.metadataContainer.putLong(5, player.id());
    }

    @Override
    protected void applyCustomProperties() {
        super.applyCustomProperties();

        // Set size
        this.size(0.25f, 0.25f);
    }

    @Override
    protected EntityFishingHook motionFromHeading(Vector motion, float velocity, float inaccuracy) {
        float distanceTravel = (float) Math.sqrt(MathUtils.square(motion.x()) + MathUtils.square(motion.y()) + MathUtils.square(motion.z()));
        this.velocity(motion.multiply(
            0.6f / distanceTravel + 0.5f + ThreadLocalRandom.current().nextFloat() * 0.0045f,
            0.6f / distanceTravel + 0.5f + ThreadLocalRandom.current().nextFloat() * 0.0045f,
            0.6f / distanceTravel + 0.5f + ThreadLocalRandom.current().nextFloat() * 0.0045f
        ));

        return this;
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
    public boolean critical() {
        return false;
    }

    @Override
    public float damage() {
        return 0;
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);

        if (this.shooter.dead() || ((EntityPlayer) this.shooter).inventory().itemInHand().itemType() != ItemType.FISHING_ROD) {
            this.despawn();
        }

        // TODO: MJ BUG / 1.2.13 / Fishing hooks get applied noclip and gravity in the client, to circumvent we need to send the position every tick
        this.getTransform().position(this.position());

        this.lastUpdateDT += dT;
        if (Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON) {
            if (this.isCollided && this.isInsideLiquid()) {
                if (!this.velocity().equals(WATER_FLOATING_MOTION)) {
                    this.velocity(WATER_FLOATING_MOTION);
                }
            } else if (this.isCollided) {
                if (!this.isReset && this.velocity().length() < 0.0025) {
                    this.velocity(Vector.ZERO);
                    this.isReset = true;
                }
            }
        }
    }

}
