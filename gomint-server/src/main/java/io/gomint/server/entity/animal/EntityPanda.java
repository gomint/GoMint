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
import io.gomint.inventory.item.ItemBamboo;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author joserobjr
 * @since 2021-01-12
 */
@RegisterInfo(sId = "minecraft:panda")
public class EntityPanda extends EntityAgeableAnimal<io.gomint.entity.animal.EntityPanda> implements io.gomint.entity.animal.EntityPanda {

    /**
     * Constructs a new EntityPanda
     *
     * @param world The world in which this entity is in
     */
    public EntityPanda(WorldAdapter world) {
        super(EntityType.PANDA, world);
        this.initEntity();
    }

    /**
     * Create new entity panda for API
     */
    public EntityPanda() {
        super(EntityType.PANDA, null);
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(20);
        this.health(20);
        if (this.baby()) {
            this.size(0.68f, 0.6f);
        } else {
            this.size(1.7f, 1.5f);
        }
    }

    @Override
    protected void kill() {
        super.kill();

        if (dead()) {
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

        ThreadLocalRandom random = ThreadLocalRandom.current();
        int amount = random.nextInt(3 + looting);
        Location location = this.location();
        if (amount > 0) {
            ItemStack<?> drop = ItemBamboo.create(amount);
            this.world.dropItem(location, drop);
        }

        if (isLastDamageCausedByPlayer()) {
            this.world.createExpOrb(location, 1 + random.nextInt(3));
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }
    
}
