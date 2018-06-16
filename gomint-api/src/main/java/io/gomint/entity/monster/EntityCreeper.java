package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityCreeper extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh cave creeper
     */
    static EntityCreeper create() {
        return GoMint.instance().createEntity( EntityCreeper.class );
    }
}
