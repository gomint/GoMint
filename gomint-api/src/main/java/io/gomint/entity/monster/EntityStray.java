package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityStray extends EntityLiving<EntityStray> {

    /**
     * Create a new entity stray with no config
     *
     * @return empty, fresh stray
     */
    static EntityStray create() {
        return GoMint.instance().createEntity( EntityStray.class );
    }

}
