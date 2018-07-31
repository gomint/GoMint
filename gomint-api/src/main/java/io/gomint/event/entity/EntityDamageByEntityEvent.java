/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.event.entity;

import io.gomint.entity.Entity;
import lombok.ToString;

/**
 * @author geNAZt
 * @version 1.0
 */
@ToString( callSuper = true )
public class EntityDamageByEntityEvent extends EntityDamageEvent {

    private final Entity attacker;

    /**
     * Create a new entity based cancellable event
     *
     * @param entity for which this event is
     */
    public EntityDamageByEntityEvent( Entity entity, Entity attacker, DamageSource damageSource, float damage ) {
        super( entity, damageSource, damage );
        this.attacker = attacker;
    }

    /**
     * Get the entity which attacked
     *
     * @return attacking entity
     */
    public Entity getAttacker() {
        return this.attacker;
    }

}
