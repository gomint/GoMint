/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.animal;

import io.gomint.inventory.item.ItemBone;
import io.gomint.inventory.item.ItemCod;
import io.gomint.inventory.item.ItemCookedCod;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Location;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.concurrent.ThreadLocalRandom;

@RegisterInfo(sId = "minecraft:cod")
public class EntityCod extends EntityAnimal implements io.gomint.entity.animal.EntityCod {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityCod(WorldAdapter world) {
        super(EntityType.COD, world);
        this.initEntity();
    }

    /**
     * Create new entity cod for API
     */
    public EntityCod() {
        super(EntityType.COD, null);
        this.initEntity();
    }

    private void initEntity() {
        this.setSize(0.5f, 0.3f);
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(3);
        this.setHealth(3);
    }

    @Override
    protected void kill() {
        super.kill();

        if (isDead()) {
            return;
        }

        // Drop items
        Location location = this.getLocation();
        ItemStack cod = isOnFire()? ItemCookedCod.create(1) : ItemCod.create(1);
        this.world.dropItem(location, cod);

        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (random.nextInt(4) == 0) {
            int amount = random.nextInt(1, 3);
            this.world.dropItem(location, ItemBone.create(amount));
        }

        // Drop xp
        if (isLastDamageCausedByPlayer()) {
            this.world.createExpOrb(location, random.nextInt(1, 4));
        }
    }

    @Override
    public void update(long currentTimeMS, float dT) {
        super.update(currentTimeMS, dT);
    }
}
