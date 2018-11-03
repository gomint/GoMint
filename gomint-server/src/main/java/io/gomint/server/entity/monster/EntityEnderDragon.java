package io.gomint.server.entity.monster;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:ender_dragon"  )
public class EntityEnderDragon extends EntityLiving implements io.gomint.entity.monster.EntityEnderDragon {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityEnderDragon( WorldAdapter world ) {
        super( EntityType.ENDER_DRAGON, world );
        this.initEntity();
    }

    /**
     * Create new entity ender dragon for API
     */
    public EntityEnderDragon() {
        super( EntityType.ENDER_DRAGON, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 13.0f, 4.0f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 200 );
        this.setHealth( 200 );
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        super.onCollideWithPlayer( player );

        switch( this.getWorld().getDifficulty() ) {
            case EASY:
                player.attack( 6, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case NORMAL:
                player.attack( 10, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case HARD:
                player.attack( 15, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
