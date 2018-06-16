package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityZombie extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh zombie
     */
    static EntityZombie create() {
        return GoMint.instance().createEntity( EntityZombie.class );
    }
}
