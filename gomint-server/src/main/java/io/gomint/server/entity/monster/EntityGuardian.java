package io.gomint.server.entity.monster;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:guardian" )
public class EntityGuardian extends EntityLiving implements io.gomint.entity.monster.EntityGuardian {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityGuardian( WorldAdapter world ) {
        super( EntityType.GUARDIAN, world );
        this.initEntity();
    }

    /**
     * Create new entity guardian for API
     */
    public EntityGuardian() {
        super( EntityType.GUARDIAN, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.85f, 0.85f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 30 );
        this.setHealth( 30 );
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        super.onCollideWithPlayer( player );

        switch( this.getWorld().getDifficulty() ) {
            case EASY:
                player.attack( 4, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case NORMAL:
                player.attack( 6, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case HARD:
                player.attack( 9, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
