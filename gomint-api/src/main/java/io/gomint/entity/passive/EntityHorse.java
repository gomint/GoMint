package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityHorse extends EntityAgeable {

    /**
     * Create a new entity horse with no config
     *
     * @return empty, fresh horse
     */
    static EntityHorse create() {
        return GoMint.instance().createEntity( EntityHorse.class );
    }

}
