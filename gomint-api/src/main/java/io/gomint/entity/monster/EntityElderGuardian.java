package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.Entity;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityElderGuardian extends EntityLiving<EntityElderGuardian> {

    /**
     * Create a new entity elder guardian with no config
     *
     * @return empty, fresh elder guardian
     */
    static EntityElderGuardian create() {
        return GoMint.instance().createEntity( EntityElderGuardian.class );
    }

    /**
     * Set the target where the guardian should shoot its laser to
     *
     * @param entity which should be used to shoot the laser to
     */
    EntityElderGuardian target(Entity<?> entity );

}
