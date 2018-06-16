package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityPig extends EntityLiving {

    /**
     * Create a new entity donkey with no config
     *
     * @return empty, fresh pig
     */
    static EntityPig create() {
        return GoMint.instance().createEntity( EntityPig.class );
    }
}
