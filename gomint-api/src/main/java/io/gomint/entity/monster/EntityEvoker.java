package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityEvoker extends EntityLiving {

    /**
     * create a new entity evoker with no config
     *
     * @return empty, fresh evoker
     */
    static EntityEvoker create() {
        return GoMint.instance().createEntity( EntityEvoker.class );
    }

}
