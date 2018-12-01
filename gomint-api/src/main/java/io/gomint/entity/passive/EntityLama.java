package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityLama extends EntityAgeable {

    /**
     * Create a new entity lama with no config
     *
     * @return empty, fresh lama
     */
    static EntityLama create() {
        return GoMint.instance().createEntity( EntityLama.class );
    }

}
