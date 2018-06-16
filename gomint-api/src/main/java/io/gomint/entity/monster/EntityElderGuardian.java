package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityElderGuardian extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh elder guardian
     */
    static EntityElderGuardian create() {
        return GoMint.instance().createEntity( EntityElderGuardian.class );
    }
}
