package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author Kaooot
 * @version 1.0
 */
public interface EntityNPC extends EntityLiving {

    /**
     * Create a new entity npc with no config.
     *
     * @return empty, fresh npc
     */
    static EntityNPC create() {
        return GoMint.instance().createEntity( EntityNPC.class );
    }
}
