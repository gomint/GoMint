package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityDonkey extends EntityAgeable {

    /**
     * Create a new entity donkey with no config
     *
     * @return empty, fresh donkey
     */
    static EntityDonkey create() {
        return GoMint.instance().createEntity( EntityDonkey.class );
    }

}
