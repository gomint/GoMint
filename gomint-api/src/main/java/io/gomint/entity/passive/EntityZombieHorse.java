package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityZombieHorse extends EntityLiving {

    /**
     * Create a new entity zombie horse with no config
     *
     * @return empty, fresh zombie horse
     */
    static EntityZombieHorse create() {
        return GoMint.instance().createEntity( EntityZombieHorse.class );
    }

}
