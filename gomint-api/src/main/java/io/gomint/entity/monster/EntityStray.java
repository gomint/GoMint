package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityStray extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh stray
     */
    static EntityStray create() {
        return GoMint.instance().createEntity( EntityStray.class );
    }
}
