package io.gomint.server.entity.monster;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:vex" )
public class EntityVex extends EntityLiving implements io.gomint.entity.monster.EntityVex {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */

    public EntityVex( WorldAdapter world ) {
        super( EntityType.VEX, world );
        this.initEntity();
    }

    /**
     * Create new entity stray for API
     */
    public EntityVex() {
        super( EntityType.VEX, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.4f, 0.8f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 14 );
        this.setHealth( 14 );
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        super.onCollideWithPlayer( player );

        switch( this.getWorld().getDifficulty() ) {
            case EASY:
                player.attack( 5, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case NORMAL:
                player.attack( 9, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case HARD:
                player.attack( 13, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
