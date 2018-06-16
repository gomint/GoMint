package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntitySquid extends EntityLiving {

    /**
     * Create a new entity donkey with no config
     *
     * @return empty, fresh squid
     */
    static EntitySquid create() {
        return GoMint.instance().createEntity( EntitySquid.class );
    }
}
