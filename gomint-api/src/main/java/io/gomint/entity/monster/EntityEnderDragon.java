package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityEnderDragon extends EntityLiving<EntityEnderDragon> {

    /**
     * Create a new entity ender dragon with no config
     *
     * @return empty, fresh ender dragon
     */
    static EntityEnderDragon create() {
        return GoMint.instance().createEntity( EntityEnderDragon.class );
    }

}
