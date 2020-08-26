package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityBat extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh bat
     */
    static EntityBat create() {
        return GoMint.instance().createEntity( EntityBat.class );
    }

}
