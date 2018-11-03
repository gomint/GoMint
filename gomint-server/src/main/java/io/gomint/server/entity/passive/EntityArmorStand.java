package io.gomint.server.entity.passive;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.inventory.item.ItemLeatherBoots;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.Vector;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityCreature;
import io.gomint.server.entity.EntityType;
import io.gomint.server.inventory.ArmorInventory;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.inventory.item.ItemArmor;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:armor_stand" )
public class EntityArmorStand extends EntityCreature implements io.gomint.entity.passive.EntityArmorStand {

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
        this.setSize( 0.5f, 1.975f );
        this.addAttribute( Attribute.HEALTH );
        this.setMaxHealth( 20 );
        this.setHealth( 20 );
        this.armorInventory = new ArmorInventory( this );
    }

    @Override
    public boolean damage( EntityDamageEvent damageEvent ) {
        this.world.getServer().getPluginManager().callEvent( damageEvent );
        if( damageEvent.isCancelled() || !this.isDamageEffective( damageEvent.getDamageSource() ) ) {
            return false;
        }

        ItemStack[] inventoryContent = this.armorInventory.getContents();

        for ( ItemStack itemStack : inventoryContent ) {
            if ( itemStack.getType() != ItemType.AIR ) {
                this.getWorld().dropItem(this.getLocation(), itemStack);
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
    public void interact( EntityPlayer player, Vector clickVector ) {
        // TODO: Adding the ability of changing the armor of this armor stand
    }

}
