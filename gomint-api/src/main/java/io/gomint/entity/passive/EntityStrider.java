package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author KingAli
 * @version 1.0
 * @stability 3
 */
public interface EntityStrider extends EntityAgeable {

    /**
     * Create a new entity strider with no config
     *
     * @return empty, fresh zombie strider
     */
    static EntityStrider create() {
        return GoMint.instance().createEntity( EntityStrider.class );
    }
}
