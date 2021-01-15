package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface EntityHoglin extends EntityAgeable<EntityHoglin> {

    /**
     * Create a new entity hoglin with no config
     *
     * @return empty, fresh zombie hoglin
     */
    static EntityHoglin create() {
        return GoMint.instance().createEntity( EntityHoglin.class );
    }
}
