package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityGuardian extends EntityLiving {

    /**
     * Create a new entity guardian with no config
     *
     * @return empty, fresh guardian
     */
    static EntityGuardian create() {
        return GoMint.instance().createEntity( EntityGuardian.class );
    }

}
