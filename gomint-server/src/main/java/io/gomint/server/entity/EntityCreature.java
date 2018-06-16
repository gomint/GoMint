/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity;

import io.gomint.server.inventory.ArmorInventory;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
public class EntityCreature extends EntityLiving implements io.gomint.entity.EntityCreature {

    /**
     * Armor inventory for all creatures
     * <p>
     * The extending entity has to init this inventory!
     */
    protected ArmorInventory armorInventory;

    /**
     * Constructs a new EntityLiving
     *
     * @param type  The type of the Entity
     * @param world The world in which this entity is in
     */
    protected EntityCreature( EntityType type, WorldAdapter world ) {
        super( type, world );
    }

    @Override
    public ArmorInventory getArmorInventory() {
        return this.armorInventory;
    }

}
