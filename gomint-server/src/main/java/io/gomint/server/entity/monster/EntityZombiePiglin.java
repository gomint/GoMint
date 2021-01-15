/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.monster;

import io.gomint.enchant.Enchantment;
import io.gomint.enchant.EnchantmentLooting;
import io.gomint.entity.Entity;
import io.gomint.entity.passive.EntityHuman;
import io.gomint.inventory.item.ItemGoldIngot;
import io.gomint.inventory.item.ItemGoldNugget;
import io.gomint.inventory.item.ItemGoldenSword;
import io.gomint.inventory.item.ItemRottenFlesh;
import io.gomint.math.Location;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author joserobjr
 * @since 2021-01-12
 */
@RegisterInfo(sId = "minecraft:zombie_pigman")
public class EntityZombiePiglin extends EntityAgeable<io.gomint.entity.monster.EntityZombiePiglin> implements io.gomint.entity.monster.EntityZombiePiglin {

    /**
     * Constructs a new EntityZombiePiglin
     *
     * @param world The world in which this entity is in
     */
    public EntityZombiePiglin( WorldAdapter world ) {
        super( EntityType.ZOMBIE_PIGLIN, world );
        this.initEntity();
    }

    /**
     * Create new entity zombie piglin for API
     */
    public EntityZombiePiglin() {
        super( EntityType.ZOMBIE_PIGLIN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.attribute(Attribute.HEALTH);
        this.maxHealth(20);
        this.health(20);
        if(this.baby()) {
            this.size(0.3f, 0.95f);
        }else{
            this.size(0.6f, 1.9f);
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

        Location location = this.location();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int amount = random.nextInt(2 + Math.min(looting, 3));
        if (amount > 0) {
            this.world.dropItem(location, ItemRottenFlesh.create(amount));
        }

        amount = random.nextInt(2 + Math.min(looting, 3));
        if (amount > 0) {
            this.world.dropItem(location, ItemGoldNugget.create(amount));
        }
        
        double chanceIncrease = Math.min(looting, 3) / 100.0;
        double chance = 0.025 + chanceIncrease;
        if (random.nextDouble() <= chance) {
            this.world.dropItem(location, ItemGoldIngot.create(1));
        }
        
        chance = 0.085 + chanceIncrease;
        if (random.nextDouble() <= chance) {
            ItemStack<?> drop = (ItemStack<?>) ItemGoldenSword.create(1);
            drop.damage(random.nextInt(drop.maxDamage()));
            this.world.dropItem(location, drop);
        }
        
        if (isLastDamageCausedByPlayer()) {
            if (baby()) {
                this.world.createExpOrb(location, 12);
            } else {
                this.world.createExpOrb(location, 5);
            }
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> tags() {
        return EntityTags.HOSTILE_MOB;
    }
    
}
