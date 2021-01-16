package io.gomint.server.entity.passive;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityCreature;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.inventory.ArmorInventory;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:armor_stand" )
public class EntityArmorStand extends EntityCreature<io.gomint.entity.passive.EntityArmorStand> implements io.gomint.entity.passive.EntityArmorStand {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityArmorStand( WorldAdapter world ) {
        super( EntityType.ARMOR_STAND, world );
        this.initEntity();
    }

    /**
     * Create new entity chicken for API
     */
    public EntityArmorStand() {
        super( EntityType.ARMOR_STAND, null );
        this.initEntity();
    }

    private void initEntity() {
        this.size( 0.5f, 1.975f );
        this.attribute( Attribute.HEALTH );
        this.maxHealth( 20 );
        this.health( 20 );
        this.armorInventory = new ArmorInventory(this.world == null ? null : this.world.getServer().items(), this );
    }

    @Override
    public boolean damage( EntityDamageEvent damageEvent ) {
        this.world.getServer().pluginManager().callEvent( damageEvent );
        if( damageEvent.cancelled() || !this.isDamageEffective( damageEvent.damageSource() ) ) {
            return false;
        }

        ItemStack<?>[] inventoryContent = this.armorInventory.contents();

        for ( ItemStack<?> itemStack : inventoryContent ) {
            if ( itemStack.itemType() != ItemType.AIR ) {
                this.world().dropItem(this.location(), itemStack);
            }
        }

        this.despawn();
        return true;
    }

    private boolean isDamageEffective( EntityDamageEvent.DamageSource damageSource ) {
        switch( damageSource ) {
            case FIRE:
            case FALL:
            case DROWNING:
            case ON_FIRE:
            case CACTUS:
                return false;
            default:
                return true;
        }
    }

    @Override
    public EntityArmorStand interact( EntityPlayer player, Vector clickVector ) {
        // TODO: Adding the ability of changing the armor of this armor stand
        return this;
    }

    @Override
    public Set<String> tags() {
        return EntityTags.PASSIVE;
    }

}
