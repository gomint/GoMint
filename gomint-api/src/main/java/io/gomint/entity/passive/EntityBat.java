package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityBat extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh bat
     */
    static EntityBat create() {
        return GoMint.instance().createEntity( EntityBat.class );
    }
}
