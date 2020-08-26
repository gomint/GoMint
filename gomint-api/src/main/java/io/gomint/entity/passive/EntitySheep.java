package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntitySheep extends EntityAgeable {

    /**
     * Create a new entity sheep with no config
     *
     * @return empty, fresh sheep
     */
    static EntitySheep create() {
        return GoMint.instance().createEntity( EntitySheep.class );
    }

}
