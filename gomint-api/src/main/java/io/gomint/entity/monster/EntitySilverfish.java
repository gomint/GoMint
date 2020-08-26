package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntitySilverfish extends EntityLiving {

    /**
     * Create a new entity silverfish with no config
     *
     * @return empty, fresh silverfish
     */
    static EntitySilverfish create() {
        return GoMint.instance().createEntity( EntitySilverfish.class );
    }

}
