package io.gomint.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.EntityLiving;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface EntityVillager extends EntityLiving {

    /**
     * Create a new entity villager with no config
     *
     * @return empty, fresh villager
     */
    static EntityVillager create() {
        return GoMint.instance().createEntity( EntityVillager.class );
    }

    /**
     * Set the profession of a villager
     *
     * @param profession of the villager
     */
    void setProfession( Profession profession );

    /**
     * Get the current profession of a villager
     *
     * @return profession of this villager
     */
    Profession getProfession();

    enum Profession {
        FARMER,
        LIBRARIAN,
        PRIEST,
        BLACKSMITH,
        BUTCHER;
    }

}
