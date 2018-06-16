package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityWither extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh wither
     */
    static EntityWither create() {
        return GoMint.instance().createEntity( EntityWither.class );
    }
}
