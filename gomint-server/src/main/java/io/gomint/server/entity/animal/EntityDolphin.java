/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.animal;

import io.gomint.enchant.Enchantment;
import io.gomint.enchant.EnchantmentLooting;
import io.gomint.entity.Entity;
import io.gomint.entity.passive.EntityHuman;
import io.gomint.inventory.item.ItemCod;
import io.gomint.inventory.item.ItemCookedCod;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author joserobjr
 * @since 2021-01-12
 */
@RegisterInfo(sId = "minecraft:dolphin")
public class EntityDolphin extends EntityAgeableAnimal<io.gomint.entity.animal.EntityDolphin> implements io.gomint.entity.animal.EntityDolphin {
    /**
     * Constructs a new EntityDolphin
     *
     * @param world The world in which this entity is in
     */
    public EntityDolphin(WorldAdapter world) {
        super(EntityType.DOLPHIN, world);
        this.initEntity();
    }

    /**
     * Create new entity dolphin for API
     */
    public EntityDolphin() {
        super(EntityType.DOLPHIN, null);
        this.initEntity();
    }

    private void initEntity() {
        if (baby()) {
            this.size(0.585f, 0.39f);
        } else {
            this.size(0.9f, 0.6f);
        }
        this.attribute(Attribute.HEALTH);
        this.maxHealth(10);
        this.health(10);
    }

    @Override
    protected void kill() {
        super.kill();

        if (dead()) {
            return;
        }
        
        if (this.baby()) {
            return;
        }

        // Drop items
        Entity<?> lastDamageEntity = this.lastDamageEntity;
        int looting = 0;
        if (lastDamageEntity instanceof EntityHuman) {
            Enchantment enchantment = ((EntityHuman<?>) lastDamageEntity).inventory().itemInHand().enchantment(EnchantmentLooting.class);
            if (enchantment != null) {
                looting = enchantment.level();
            }
        }
        
        int amount = ThreadLocalRandom.current().nextInt(2 + looting);
        if (amount > 0) {
            
            ItemStack<?> drop;
            if (this.burning()) {
                drop = ItemCookedCod.create(amount);
            } else {
                drop = ItemCod.create(amount);
            }
            
            this.world.dropItem(this.location(), drop);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }

}
