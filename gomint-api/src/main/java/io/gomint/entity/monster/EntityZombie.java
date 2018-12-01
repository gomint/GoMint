package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityZombie extends EntityAgeable {

    /**
     * Create a new entity zombie with no config
     *
     * @return empty, fresh zombie
     */
    static EntityZombie create() {
        return GoMint.instance().createEntity( EntityZombie.class );
    }

}
