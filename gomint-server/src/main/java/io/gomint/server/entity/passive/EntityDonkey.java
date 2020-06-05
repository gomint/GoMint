package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:donkey" )
public class EntityDonkey extends EntityAgeable implements io.gomint.entity.passive.EntityDonkey {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityDonkey( WorldAdapter world ) {
        super( EntityType.DONKEY, world );
        this.initEntity();
    }

    /**
     * Create new entity donkey for API
     */
    public EntityDonkey() {
        super( EntityType.DONKEY, null );
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(30);
        this.setHealth(30);
        if(this.isBaby()) {
            this.setSize(0.6982f, 0.75f);
        }else{
            this.setSize(1.3965f, 1.5f);
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

}
