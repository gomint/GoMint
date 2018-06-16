package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityVex extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh vex
     */
    static EntityVex create() {
        return GoMint.instance().createEntity( EntityVex.class );
    }
}
