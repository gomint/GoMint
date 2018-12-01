package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityTropicalFish extends EntityLiving {

    /**
     * Create a new entity tropical fish with no config
     *
     * @return empty, fresh tropical fish
     */
    static EntityTropicalFish create() {
        return GoMint.instance().createEntity( EntityTropicalFish.class );
    }

}
