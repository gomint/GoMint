package io.gomint.entity.monster;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityEndermite extends EntityLiving<EntityEndermite> {

    /**
     * Create a new entity endermite with no config
     *
     * @return empty, fresh endermite
     */
    static EntityEndermite create() {
        return GoMint.instance().createEntity( EntityEndermite.class );
    }

}
