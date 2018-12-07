package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityPig extends EntityAgeable {

    /**
     * Create a new entity pig with no config
     *
     * @return empty, fresh pig
     */
    static EntityPig create() {
        return GoMint.instance().createEntity( EntityPig.class );
    }

}
