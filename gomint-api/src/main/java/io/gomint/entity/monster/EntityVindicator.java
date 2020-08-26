package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityVindicator extends EntityLiving {

    /**
     * Create a new entity vindicator with no config
     *
     * @return empty, fresh vindicator
     */
    static EntityVindicator create() {
        return GoMint.instance().createEntity( EntityVindicator.class );
    }

}
