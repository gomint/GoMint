package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityParrot extends EntityLiving {

    /**
     * Create new entity parrot with no config
     *
     * @return empty, fresh parrot
     */
    static EntityParrot create() {
        return GoMint.instance().createEntity( EntityParrot.class );
    }
}
