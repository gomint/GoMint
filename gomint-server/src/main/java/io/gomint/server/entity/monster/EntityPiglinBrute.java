/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.monster;

import io.gomint.inventory.item.ItemGoldenAxe;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
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
@RegisterInfo(sId = "minecraft:piglin_brute")
public class EntityPiglinBrute extends EntityLiving implements io.gomint.entity.monster.EntityPiglinBrute {

    /**
     * Constructs a new EntityPiglinBrute
     *
     * @param world The world in which this entity is in
     */
    public EntityPiglinBrute( WorldAdapter world ) {
        super( EntityType.PIGLIN_BRUTE, world );
        this.initEntity();
    }

    /**
     * Create new entity piglin brute for API
     */
    public EntityPiglinBrute() {
        super( EntityType.PIGLIN_BRUTE, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.6f, 1.9f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 50 );
        this.setHealth( 50 );
    }

    @Override
    protected void kill() {
        super.kill();

        if (isDead()) {
            return;
        }

        // Item drop
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (random.nextDouble() <= 0.085) {
            ItemStack goldenAxe = (ItemStack) ItemGoldenAxe.create(1);
            goldenAxe.setDamage(1 + random.nextInt(goldenAxe.getMaxDamage()));
            this.world.dropItem(this.getLocation(), goldenAxe);
        }
        
        if (isLastDamageCausedByPlayer()) {
            this.world.createExpOrb(this.getLocation(), 20);
        }
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
