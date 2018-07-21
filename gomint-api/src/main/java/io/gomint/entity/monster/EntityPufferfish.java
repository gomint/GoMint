package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityPufferfish extends EntityLiving {

    /**
     * Create a new puffer fish with no config
     *
     * @return empty, fresh pufferfish
     */
    static EntityPufferfish create() {
        return GoMint.instance().createEntity( EntityPufferfish.class );
    }
}
