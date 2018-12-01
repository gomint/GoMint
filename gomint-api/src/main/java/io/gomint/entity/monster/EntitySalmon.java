package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntitySalmon extends EntityLiving {

    /**
     * Create a new entity salmon with no config
     *
     * @return empty, fresh salmon
     */
    static EntitySalmon create() {
        return GoMint.instance().createEntity( EntitySalmon.class );
    }

}
