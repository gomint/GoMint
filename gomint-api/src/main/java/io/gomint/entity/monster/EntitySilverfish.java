package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntitySilverfish extends EntityLiving {

    /**
     * Create a new entity bat with no config
     *
     * @return empty, fresh silverfish
     */
    static EntitySilverfish create() {
        return GoMint.instance().createEntity( EntitySilverfish.class );
    }
}
