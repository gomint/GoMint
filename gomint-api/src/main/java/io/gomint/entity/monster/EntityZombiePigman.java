package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityZombiePigman extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh zombie pigman
     */
    static EntityZombiePigman create() {
        return GoMint.instance().createEntity( EntityZombiePigman.class );
    }
}
