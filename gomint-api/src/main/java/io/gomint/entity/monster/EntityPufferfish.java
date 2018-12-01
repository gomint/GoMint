package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityPufferfish extends EntityLiving {

    /**
     * Create a new entity puffer fish with no config
     *
     * @return empty, fresh puffer fish
     */
    static EntityPufferfish create() {
        return GoMint.instance().createEntity( EntityPufferfish.class );
    }

}
