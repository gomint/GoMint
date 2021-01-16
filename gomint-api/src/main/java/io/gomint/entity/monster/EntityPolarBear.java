package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityPolarBear extends EntityAgeable<EntityPolarBear> {

    /**
     * Create a new entity polar bear with no config
     *
     * @return empty, fresh polar bear
     */
    static EntityPolarBear create() {
        return GoMint.instance().createEntity( EntityPolarBear.class );
    }

}
