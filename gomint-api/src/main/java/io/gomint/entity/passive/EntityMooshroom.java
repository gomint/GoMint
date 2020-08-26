package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityMooshroom extends EntityAgeable {

    /**
     * Create a new entity mooshroom with no config
     *
     * @return empty, fresh mooshroom
     */
    static EntityMooshroom create() {
        return GoMint.instance().createEntity( EntityMooshroom.class );
    }

}
