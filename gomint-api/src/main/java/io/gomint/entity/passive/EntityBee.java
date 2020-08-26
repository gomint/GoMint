package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface EntityBee extends EntityAgeable {

    /**
     * Create a new entity horse with no config
     *
     * @return empty, fresh horse
     */
    static EntityBee create() {
        return GoMint.instance().createEntity( EntityBee.class );
    }
}
