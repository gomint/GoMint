package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityWitch extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh witch
     */
    static EntityWitch create() {
        return GoMint.instance().createEntity( EntityWitch.class );
    }
}
