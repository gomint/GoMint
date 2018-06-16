package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityGhast extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh ghast
     */
    static EntityGhast create() {
        return GoMint.instance().createEntity( EntityGhast.class );
    }
}
