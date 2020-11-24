/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.animal;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:salmon" )
public class EntitySalmon extends EntityAnimal implements io.gomint.entity.animal.EntitySalmon {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntitySalmon( WorldAdapter world ) {
        super( EntityType.SALMON, world );
        this.initEntity();
    }

    /**
     * Create new entity salmon for API
     */
    public EntitySalmon() {
        super( EntityType.SALMON, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.7f, 0.4f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 3 );
        this.setHealth( 3 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
