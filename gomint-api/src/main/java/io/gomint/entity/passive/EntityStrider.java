package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.monster.EntityPiglin;

/**
 * @author KingAli
 * @version 1.0
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
