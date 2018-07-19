package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

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
