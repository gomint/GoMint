package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:pig" )
public class EntityPig extends EntityAgeable implements io.gomint.entity.passive.EntityPig {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityPig( WorldAdapter world ) {
        super( EntityType.PIG, world );
        this.initEntity();
    }

    /**
     * Create new entity pig for API
     */
    public EntityPig() {
        super( EntityType.PIG, null );
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(20);
        this.setHealth(20);
        if(this.isBaby()) {
            this.setSize(0.45f, 0.45f);
        }else{
            this.setSize(0.9f, 0.9f);
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
