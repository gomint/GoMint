package io.gomint.server.entity.monster;

import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:ravager" )
public class EntityRavager extends EntityLiving implements io.gomint.entity.monster.EntityRavager {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityRavager( WorldAdapter world ) {
        super( EntityType.RAVAGER, world );
        this.initEntity();
    }

    public EntityRavager() {
        super( EntityType.RAVAGER, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 1.95f, 2.2f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 100 );
        this.setHealth( 100 );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public Set<String> getTags() {
        return EntityTags.HOSTILE_MOB;
    }

}
