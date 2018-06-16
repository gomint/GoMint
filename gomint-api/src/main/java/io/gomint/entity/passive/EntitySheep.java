package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntitySheep extends EntityLiving {

    /**
     * Create a new entity donkey with no config
     *
     * @return empty, fresh sheep
     */
    static EntitySheep create() {
        return GoMint.instance().createEntity( EntitySheep.class );
    }
}
