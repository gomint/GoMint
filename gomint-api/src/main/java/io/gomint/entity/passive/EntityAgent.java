package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author Kaooot
 * @version 1.0
 */
public interface EntityAgent extends EntityLiving {

    /**
     * Create a new entity agent with no config
     *
     * @return empty, fresh agent
     */
    static EntityAgent create() {
        return GoMint.instance().createEntity( EntityAgent.class );
    }
}
