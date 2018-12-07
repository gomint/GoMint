package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityCod extends EntityLiving {

    /**
     * Create a new cod with no config
     *
     * @return empty, fresh cod
     */
    static EntityCod create() {
        return GoMint.instance().createEntity( EntityCod.class );
    }

}
