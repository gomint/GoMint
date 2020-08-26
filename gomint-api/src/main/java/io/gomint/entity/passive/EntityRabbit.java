package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface EntityRabbit extends EntityLiving {

    /**
     * Create a new entity rabbit with no config
     *
     * @return empty, fresh rabbit
     */
    static EntityRabbit create() {
        return GoMint.instance().createEntity( EntityRabbit.class );
    }

}
