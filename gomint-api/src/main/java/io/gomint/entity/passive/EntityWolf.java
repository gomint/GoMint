package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityWolf extends EntityLiving {

    /**
     * Create a new entity donkey with no config
     *
     * @return empty, fresh cow
     */
    static EntityWolf create() {
        return GoMint.instance().createEntity( EntityWolf.class );
    }
}
