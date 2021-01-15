package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityWither extends EntityLiving<EntityWither> {

    /**
     * Create a new entity wither with no config
     *
     * @return empty, fresh wither
     */
    static EntityWither create() {
        return GoMint.instance().createEntity( EntityWither.class );
    }

}
