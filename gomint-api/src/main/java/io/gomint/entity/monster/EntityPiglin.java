package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface EntityPiglin extends EntityAgeable<EntityPiglin> {

    /**
     * Create a new entity piglin with no config
     *
     * @return empty, fresh zombie piglin
     */
    static EntityPiglin create() {
        return GoMint.instance().createEntity( EntityPiglin.class );
    }

}
