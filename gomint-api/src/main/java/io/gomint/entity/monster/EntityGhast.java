package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityGhast extends EntityLiving<EntityGhast> {

    /**
     * Create a new entity ghast with no config
     *
     * @return empty, fresh ghast
     */
    static EntityGhast create() {
        return GoMint.instance().createEntity( EntityGhast.class );
    }

}
