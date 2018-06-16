package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityEnderDragon extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh ender dragon
     */
    static EntityEnderDragon create() {
        return GoMint.instance().createEntity( EntityEnderDragon.class );
    }

}
