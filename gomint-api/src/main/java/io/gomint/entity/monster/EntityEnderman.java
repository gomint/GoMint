package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityEnderman extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh enderman
     */
    static EntityEnderman create() {
        return GoMint.instance().createEntity( EntityEnderman.class );
    }
}
