package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityZombieHorse extends EntityLiving {

    /**
     * Create a new entity donkey with no config
     *
     * @return empty, fresh zombie horse
     */
    static EntityZombieHorse create() {
        return GoMint.instance().createEntity( EntityZombieHorse.class );
    }

}
