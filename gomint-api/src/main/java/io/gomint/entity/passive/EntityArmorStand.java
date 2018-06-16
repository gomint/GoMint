package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityCreature;

public interface EntityArmorStand extends EntityCreature {

    /**
     * Create a new entity armor stand with no config
     *
     * @return empty, fresh armor stand
     */
    static EntityArmorStand create() {
        return GoMint.instance().createEntity( EntityArmorStand.class );
    }

}
