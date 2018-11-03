package io.gomint.server.entity.monster;

import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityLiving;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

@RegisterInfo( sId = "minecraft:endermite" )
public class EntityEndermite extends EntityLiving implements io.gomint.entity.monster.EntityEndermite{

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityEndermite( WorldAdapter world ) {
        super( EntityType.ENDERMITE, world );
        this.initEntity();
    }

    /**
     * Create new entity blaze for API
     */
    public EntityEndermite() {
        super( EntityType.ENDERMITE, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.4f, 0.3f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 8 );
        this.setHealth( 8 );
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        super.onCollideWithPlayer( player );

        switch( this.getWorld().getDifficulty() ) {
            case EASY: case NORMAL:
                player.attack( 2, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
            case HARD:
                player.attack( 3, EntityDamageEvent.DamageSource.ENTITY_ATTACK );
                break;
        }
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }
}
