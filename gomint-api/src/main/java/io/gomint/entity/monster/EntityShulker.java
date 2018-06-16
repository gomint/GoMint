package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityShulker extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh shulker
     */
    static EntityShulker create() {
        return GoMint.instance().createEntity( EntityShulker.class );
    }
}
