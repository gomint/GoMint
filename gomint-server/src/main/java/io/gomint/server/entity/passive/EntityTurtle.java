package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:turtle" )
public class EntityTurtle extends EntityAgeable implements io.gomint.entity.passive.EntityTurtle {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityTurtle( WorldAdapter world ) {
        super( EntityType.TURTLE, world );
        this.initEntity();
    }

    /**
     * Create new entity turtle for API
     */
    public EntityTurtle() {
        super( EntityType.TURTLE, null );
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(30);
        this.setHealth(30);
        if(this.isBaby()) {
            this.setSize(0.36f, 0.12f);
        }else{
            this.setSize(1.2f, 0.4f);
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
