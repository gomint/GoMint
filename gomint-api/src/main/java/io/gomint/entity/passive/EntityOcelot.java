package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityOcelot extends EntityLiving {

    /**
     * Create a new entity cow with no config
     *
     * @return empty, fresh ocelot
     */
    static EntityOcelot create() {
        return GoMint.instance().createEntity( EntityOcelot.class );
    }
}
