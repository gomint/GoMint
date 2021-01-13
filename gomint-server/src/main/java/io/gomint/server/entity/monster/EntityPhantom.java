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
import io.gomint.inventory.item.ItemPhantomMembrane;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author joserobjr
 * @since 2021-01-12
 */
@RegisterInfo( sId = "minecraft:phantom" )
public class EntityPhantom extends EntityLiving implements io.gomint.entity.monster.EntityPhantom {

    /**
     * Constructs a new EntityPhantom
     *
     * @param world The world in which this entity is in
     */
    public EntityPhantom( WorldAdapter world ) {
        super( EntityType.PHANTOM, world );
        this.initEntity();
    }

    /**
     * Create new entity phantom for API
     */
    public EntityPhantom() {
        super( EntityType.PHANTOM, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.9f, 0.5f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
    }

    @Override
    protected void kill() {
        super.kill();

        if (isDead()) {
            return;
        }
        
        if (!isLastDamageCausedByPlayer()) {
            return;
        }

        Location location = this.getLocation();

        // Drop items
        Entity lastDamageEntity = this.lastDamageEntity;
        int looting = 0;
        if (lastDamageEntity instanceof EntityHuman) {
            Enchantment enchantment = ((EntityHuman) lastDamageEntity).getInventory().getItemInHand().getEnchantment(EnchantmentLooting.class);
            if (enchantment != null) {
                looting = enchantment.getLevel();
            }
        }

        int amount = ThreadLocalRandom.current().nextInt(3 + Math.min(looting, 3));
        if (amount > 0) {
            ItemStack drop = ItemPhantomMembrane.create(amount);
            this.world.dropItem(this.getLocation(), drop);
        }
        
        // Drop xp
        this.world.createExpOrb(location, 5);
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> getTags() {
        return EntityTags.HOSTILE_MOB;
    }

}
