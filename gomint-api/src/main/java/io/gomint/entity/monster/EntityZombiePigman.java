package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityZombiePigman extends EntityAgeable {

    /**
     * Create a new entity zombie pigman with no config
     *
     * @return empty, fresh zombie pigman
     */
    static EntityZombiePigman create() {
        return GoMint.instance().createEntity( EntityZombiePigman.class );
    }

}
