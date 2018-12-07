package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;
import io.gomint.entity.passive.EntityBat;

public interface EntityBlaze extends EntityLiving {

    /**
     * Create a new entity blaze with no config
     *
     * @return empty, fresh blaze
     */
    static EntityBlaze create() {
        return GoMint.instance().createEntity( EntityBlaze.class );
    }

}
