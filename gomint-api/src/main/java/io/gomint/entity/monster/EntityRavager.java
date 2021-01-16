package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface EntityRavager extends EntityLiving<EntityRavager> {

    /**
     * Create a new entity rabbit with no config
     *
     * @return empty, fresh rabbit
     */
    static EntityRavager create() {
        return GoMint.instance().createEntity( EntityRavager.class );
    }
}
