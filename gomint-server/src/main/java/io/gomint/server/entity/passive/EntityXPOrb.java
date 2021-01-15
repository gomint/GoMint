/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.passive;

import io.gomint.GoMint;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.math.MathUtils;
import io.gomint.math.Vector;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.world.Gamemode;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:xp_orb")
public class EntityXPOrb extends Entity implements io.gomint.entity.passive.EntityXPOrb {

    private int xpAmount;
    private long pickupTime;

    // Since xp orbs can travel a bit
    private EntityPlayer closestPlayer;

    private float lastUpdateDT;

    /**
     * Construct a new Entity
     *
     * @param world    The world in which this entity is in
     * @param xpAmount amount of xp this orb stores
     */
    public EntityXPOrb(WorldAdapter world, int xpAmount) {
        super(EntityType.XP_ORB, world);
        this.setHasCollision(false);
        this.setSize(0.25f, 0.25f);

        GRAVITY = 0.04f;
        DRAG = 0.02f;

        this.xpAmount = xpAmount;
        setPickupDelay(1250, TimeUnit.MILLISECONDS);
    }

    /**
     * Create entity for API usage
     */
    public EntityXPOrb() {
        super(EntityType.XP_ORB, null);
        this.setHasCollision(false);
        this.setSize(0.25f, 0.25f);

        GRAVITY = 0.04f;
        DRAG = 0.02f;
    }

    @Override
    public int getXpAmount() {
        return xpAmount;
    }

    @Override
    public void setXpAmount(int xpAmount) {
        this.xpAmount = xpAmount;
    }

    @Override
    public long getPickupTime() {
        return pickupTime;
    }

    @Override
    public void setPickupDelay(long duration, TimeUnit timeUnit) {
        this.pickupTime = ((GoMintServer) GoMint.instance()).currentTickTime() + timeUnit.toMillis(duration);
    }

    @Override
    public boolean damage(EntityDamageEvent damageEvent) {
        return (damageEvent.damageSource() == EntityDamageEvent.DamageSource.VOID ||
            damageEvent.damageSource() == EntityDamageEvent.DamageSource.ON_FIRE ||
            damageEvent.damageSource() == EntityDamageEvent.DamageSource.ENTITY_EXPLODE)
            && super.damage(damageEvent);
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        // Entity base tick (movement)
        super.update(currentTimeMS, dT);

        this.lastUpdateDT += dT;
        if (Values.CLIENT_TICK_RATE - this.lastUpdateDT < MathUtils.EPSILON) {
            if (this.world.getServer().currentTickTime() > this.getPickupTime() && !this.isDead()) {
                if (this.closestPlayer == null || this.closestPlayer.getGamemode() == Gamemode.SPECTATOR ||
                    this.closestPlayer.isDead() || this.closestPlayer.getHealth() <= 0 ||
                    this.closestPlayer.getLocation().distanceSquared(this.getLocation()) > 64) {
                    this.closestPlayer = null;

                    for (io.gomint.entity.EntityPlayer p : this.world.onlinePlayers()) {
                        if (p.getGamemode() != Gamemode.SPECTATOR && p.getLocation().distanceSquared(this.getLocation()) <= 64) {
                            this.closestPlayer = (EntityPlayer) p;
                            break;
                        }
                    }
                }

                if (this.closestPlayer != null) {
                    float dX = (this.closestPlayer.getPositionX() - this.getPositionX()) / 8.0f;
                    float dY = (this.closestPlayer.getPositionY() + this.closestPlayer.getEyeHeight() / 2.0f - this.getPositionY()) / 8.0f;
                    float dZ = (this.closestPlayer.getPositionZ() - this.getPositionZ()) / 8.0f;

                    float distance = MathUtils.sqrt(dX * dX + dY * dY + dZ * dZ);
                    float diff = 1.0f - distance;

                    if (diff > 0.0D) {
                        diff = diff * diff;

                        Vector motion = this.getVelocity();
                        this.setVelocity(motion.add(
                            dX / distance * diff * 0.1f,
                            dY / distance * diff * 0.1f,
                            dZ / distance * diff * 0.1f
                        ));
                    }
                }
            }

            if (this.age > 6000) {    // 5 Minutes
                this.despawn();
            }

            this.lastUpdateDT = 0;
        }
    }

    @Override
    protected void fall() {

    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        // Check if we can pick it up
        if (this.world.getServer().currentTickTime() > this.getPickupTime() && !this.isDead()) {
            if (player.canPickupXP()) {
                player.addXP(this.xpAmount);
                this.despawn();
            }
        }
    }

    @Override
    public Set<String> getTags() {
        return EntityTags.PASSIVE;
    }

}
