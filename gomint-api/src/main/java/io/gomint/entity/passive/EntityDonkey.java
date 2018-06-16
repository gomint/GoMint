package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

public interface EntityDonkey extends EntityLiving{

    /**
     * Create a new entity donkey with no config
     *
     * @return empty, fresh donkey
     */
    static EntityDonkey create() {
        return GoMint.instance().createEntity( EntityDonkey.class );
    }

}
