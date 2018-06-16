package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityGuardian extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh guardian
     */
    static EntityGuardian create() {
        return GoMint.instance().createEntity( EntityGuardian.class );
    }
}
