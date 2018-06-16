package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityPolarBear extends EntityLiving {

    /**
     * Create a new entity donkey with no config
     *
     * @return empty, fresh polar bear
     */
    static EntityPolarBear create() {
        return GoMint.instance().createEntity( EntityPolarBear.class );
    }
}
