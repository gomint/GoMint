package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityEnderman extends EntityLiving<EntityEnderman> {

    /**
     * Create a new entity enderman with no config
     *
     * @return empty, fresh enderman
     */
    static EntityEnderman create() {
        return GoMint.instance().createEntity( EntityEnderman.class );
    }

}
