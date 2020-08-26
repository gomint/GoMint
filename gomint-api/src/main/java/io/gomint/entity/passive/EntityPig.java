package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
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
