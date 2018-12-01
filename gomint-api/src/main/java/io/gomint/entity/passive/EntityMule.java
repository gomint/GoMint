package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityMule extends EntityAgeable {

    /**
     * Create a new entity mule with no config
     *
     * @return empty, fresh mule
     */
    static EntityMule create() {
        return GoMint.instance().createEntity( EntityMule.class );
    }

}
