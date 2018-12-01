package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityPolarBear extends EntityAgeable {

    /**
     * Create a new entity polar bear with no config
     *
     * @return empty, fresh polar bear
     */
    static EntityPolarBear create() {
        return GoMint.instance().createEntity( EntityPolarBear.class );
    }

}
