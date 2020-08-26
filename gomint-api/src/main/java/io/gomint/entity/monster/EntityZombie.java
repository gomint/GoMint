package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
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
