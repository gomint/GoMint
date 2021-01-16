package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityDrowned extends EntityAgeable<EntityDrowned> {

    /**
     * Create a new entity drowned with no config
     *
     * @return empty, fresh drowned
     */
    static EntityDrowned create() {
        return GoMint.instance().createEntity( EntityDrowned.class );
    }

}
