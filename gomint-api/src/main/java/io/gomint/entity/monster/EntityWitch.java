package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityWitch extends EntityLiving<EntityWitch> {

    /**
     * Create a new entity witch with no config
     *
     * @return empty, fresh witch
     */
    static EntityWitch create() {
        return GoMint.instance().createEntity( EntityWitch.class );
    }

}
