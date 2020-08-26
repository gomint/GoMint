package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
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
