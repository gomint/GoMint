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
public class EntityDolphin extends EntityAgeableAnimal implements io.gomint.entity.animal.EntityDolphin {
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
        if (isBaby()) {
            this.setSize(0.585f, 0.39f);
        } else {
            this.setSize(0.9f, 0.6f);
        }
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(10);
        this.setHealth(10);
    }

    @Override
    protected void kill() {
        super.kill();

        if (isDead()) {
            return;
        }
        
        if (this.isBaby()) {
            return;
        }

        // Drop items
        Entity lastDamageEntity = this.lastDamageEntity;
        int looting = 0;
        if (lastDamageEntity instanceof EntityHuman) {
            Enchantment enchantment = ((EntityHuman) lastDamageEntity).getInventory().getItemInHand().getEnchantment(EnchantmentLooting.class);
            if (enchantment != null) {
                looting = enchantment.getLevel();
            }
        }
        
        int amount = ThreadLocalRandom.current().nextInt(2 + looting);
        if (amount > 0) {
            
            ItemStack drop;
            if (this.isOnFire()) {
                drop = ItemCookedCod.create(amount);
            } else {
                drop = ItemCod.create(amount);
            }
            
            this.world.dropItem(this.getLocation(), drop);
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }
}
