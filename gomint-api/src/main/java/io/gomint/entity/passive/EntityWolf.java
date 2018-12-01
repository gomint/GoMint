package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityWolf extends EntityAgeable {

    /**
     * Create a new entity wolf with no config
     *
     * @return empty, fresh wolf
     */
    static EntityWolf create() {
        return GoMint.instance().createEntity( EntityWolf.class );
    }

}
