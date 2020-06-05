package io.gomint.server.entity.passive;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:fox" )
public class EntityFox extends EntityAgeable implements io.gomint.entity.passive.EntityFox {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityFox( WorldAdapter world ) {
        super( EntityType.FOX, world );
        this.initEntity();
    }

    public EntityFox() {
        super( EntityType.FOX, null );
        this.initEntity();
    }

    private void initEntity() {
        this.addAttribute(Attribute.HEALTH);
        this.setMaxHealth(20);
        this.setHealth(20);
        this.setSize(0.7f, 0.6f);
        //No Information for baby size ??
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

}
