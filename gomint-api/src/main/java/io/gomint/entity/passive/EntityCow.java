package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityCow extends EntityAgeable {

    /**
     * Create a new entity cow with no config
     *
     * @return empty, fresh cow
     */
    static EntityCow create() {
        return GoMint.instance().createEntity( EntityCow.class );
    }

}
