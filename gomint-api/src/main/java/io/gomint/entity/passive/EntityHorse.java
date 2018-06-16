package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityHorse extends EntityLiving {

    /**
     * Create a new entity horse with no config
     *
     * @return empty, fresh horse
     */
    static EntityHorse create() {
        return GoMint.instance().createEntity( EntityHorse.class );
    }
}
