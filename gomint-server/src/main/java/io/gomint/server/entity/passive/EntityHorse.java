package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:horse" )
public class EntityHorse extends EntityAgeable implements io.gomint.entity.passive.EntityHorse {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityHorse( WorldAdapter world ) {
        super( EntityType.HORSE, world );
        this.initEntity();
    }

    /**
     * Create new entity horse for API
     */
    public EntityHorse() {
        super( EntityType.HORSE, null );
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(30);
        this.setHealth(30);
        if(this.isBaby()) {
            this.setSize(0.6982f, 9.8f);
        }else{
            this.setSize(1.3965f, 1.6f);
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
