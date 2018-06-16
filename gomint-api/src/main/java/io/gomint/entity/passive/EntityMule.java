package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityMule extends EntityLiving {

    /**
     * Create a new entity cow with no config
     *
     * @return empty, fresh mule
     */
    static EntityMule create() {
        return GoMint.instance().createEntity( EntityMule.class );
    }
}
