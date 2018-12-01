package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityMooshroom extends EntityLiving {

    /**
     * Create a new entity mooshroom with no config
     *
     * @return empty, fresh mooshroom
     */
    static EntityMooshroom create() {
        return GoMint.instance().createEntity( EntityMooshroom.class );
    }

}
