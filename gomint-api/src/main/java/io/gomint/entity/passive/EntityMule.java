package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityMule extends EntityAgeable {

    /**
     * Create a new entity mule with no config
     *
     * @return empty, fresh mule
     */
    static EntityMule create() {
        return GoMint.instance().createEntity( EntityMule.class );
    }

}
