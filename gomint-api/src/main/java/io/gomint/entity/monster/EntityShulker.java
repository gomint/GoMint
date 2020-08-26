package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityShulker extends EntityLiving {

    /**
     * Create a new entity shulker with no config
     *
     * @return empty, fresh shulker
     */
    static EntityShulker create() {
        return GoMint.instance().createEntity( EntityShulker.class );
    }

}
