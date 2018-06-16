package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityHusk extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh husk
     */
    static EntityHusk create() {
        return GoMint.instance().createEntity( EntityHusk.class );
    }
}
