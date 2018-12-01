package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityDrowned extends EntityAgeable {

    /**
     * Create a new entity drowned with no config
     *
     * @return empty, fresh drowned
     */
    static EntityDrowned create() {
        return GoMint.instance().createEntity( EntityDrowned.class );
    }

}
