package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityTurtle extends EntityLiving {

    /**
     * Create new entity turtle with no config
     *
     * @return empty, fresh turtle
     */
    static EntityTurtle create() {
        return GoMint.instance().createEntity( EntityTurtle.class );
    }
}
