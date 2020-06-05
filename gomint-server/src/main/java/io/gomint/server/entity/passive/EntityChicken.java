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
@RegisterInfo( sId = "minecraft:chicken" )
public class EntityChicken extends EntityAgeable implements io.gomint.entity.passive.EntityChicken {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityChicken( WorldAdapter world ) {
        super( EntityType.CHICKEN, world );
        this.initEntity();
    }

    /**
     * Create new entity chicken for API
     */
    public EntityChicken() {
        super( EntityType.CHICKEN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(8);
        this.setHealth(8);
        if(this.isBaby()) {
            this.setSize(0.2f, 0.35f);
        }else{
            this.setSize(0.4f, 0.7f);
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

}
