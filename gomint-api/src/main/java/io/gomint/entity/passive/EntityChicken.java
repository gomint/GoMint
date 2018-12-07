package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityAgeable;
import io.gomint.entity.EntityLiving;

public interface EntityChicken extends EntityAgeable {

    /**
     * Create a new entity chicken with no config
     *
     * @return empty, fresh chicken
     */
    static EntityChicken create() {
        return GoMint.instance().createEntity( EntityChicken.class );
    }

}
