package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntitySquid extends EntityLiving {

    /**
     * Create a new entity squid with no config
     *
     * @return empty, fresh squid
     */
    static EntitySquid create() {
        return GoMint.instance().createEntity( EntitySquid.class );
    }

}
