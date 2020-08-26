package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface EntityPillager extends EntityLiving {

    /**
     * Create a new entity horse with no config
     *
     * @return empty, fresh horse
     */
    static EntityPillager create() {
        return GoMint.instance().createEntity( EntityPillager.class );
    }
}
