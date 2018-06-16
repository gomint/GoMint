package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityEndermite extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh endermite
     */
    static EntityEndermite create() {
        return GoMint.instance().createEntity( EntityEndermite.class );
    }
}
