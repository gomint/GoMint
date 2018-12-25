package io.gomint.server.inventory.item;

import io.gomint.entity.potion.PotionEffect;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.inventory.item.ItemType;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.category.ItemConsumable;
import io.gomint.server.registry.RegisterInfo;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 373 )
public class ItemPotion extends ItemStack implements io.gomint.inventory.item.ItemPotion, ItemConsumable {



    @Override
    public ItemType getType() {
        return ItemType.POTION;
    }

    @Override
    public void onConsume( EntityPlayer player ) {
        switch ( this.getData() ) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                // These potions do not have any effects
                break;
            case 5:
                player.addEffect( PotionEffect.NIGHT_VISION, 0, 3, TimeUnit.MINUTES );
                break;
            case 6:
                player.addEffect( PotionEffect.NIGHT_VISION, 0, 8, TimeUnit.MINUTES );
                break;
            case 7:
                player.addEffect( PotionEffect.INVISIBILITY, 0, 3, TimeUnit.MINUTES );
                break;
            case 8:
                player.addEffect( PotionEffect.INVISIBILITY, 0, 8, TimeUnit.MINUTES );
                break;
            case 9:
                player.addEffect( PotionEffect.JUMP, 0, 3, TimeUnit.MINUTES );
                break;
            case 10:
                player.addEffect( PotionEffect.JUMP, 0, 8, TimeUnit.MINUTES );
                break;
            case 11:
                player.addEffect( PotionEffect.JUMP, 1, 90, TimeUnit.SECONDS );
                break;
            case 12:
                player.addEffect( PotionEffect.FIRE_RESISTANCE, 0, 3, TimeUnit.MINUTES );
                break;
            case 13:
                player.addEffect( PotionEffect.FIRE_RESISTANCE, 0, 8, TimeUnit.MINUTES );
                break;
            case 14:
                player.addEffect( PotionEffect.SPEED, 0, 3, TimeUnit.MINUTES );
                break;
            case 15:
                player.addEffect( PotionEffect.SPEED, 0, 8, TimeUnit.MINUTES );
                break;
            case 16:
                player.addEffect( PotionEffect.SPEED, 1, 90, TimeUnit.SECONDS );
                break;
            case 17:
                player.addEffect( PotionEffect.SLOWNESS, 0, 90, TimeUnit.SECONDS );
                break;
            case 18:
                player.addEffect( PotionEffect.SLOWNESS, 0, 4, TimeUnit.MINUTES );
                break;
            case 19:
                player.addEffect( PotionEffect.WATER_BREATHING, 0, 3, TimeUnit.MINUTES );
                break;
            case 20:
                player.addEffect( PotionEffect.WATER_BREATHING, 0, 8, TimeUnit.MINUTES );
                break;
            case 21:
                player.setHealth( player.getHealth() <= player.getMaxHealth() - 2 ? 0 : player.getHealth() + 2 );
                break;
            case 22:
                player.setHealth( player.getHealth() <= player.getMaxHealth() - 4 ? 0 : player.getHealth() + 4 );
                break;
            case 23:
                EntityDamageEvent event = new EntityDamageEvent( player, EntityDamageEvent.DamageSource.HARM_EFFECT, 2 );
                player.damage( event );
                break;
            case 24:
                EntityDamageEvent damageEvent = new EntityDamageEvent( player, EntityDamageEvent.DamageSource.HARM_EFFECT, 4 );
                player.damage( damageEvent );
                break;
            case 25:
                player.addEffect( PotionEffect.POISON, 0, 45, TimeUnit.SECONDS );
                break;
            case 26:
                player.addEffect( PotionEffect.POISON, 0, 2, TimeUnit.MINUTES );
                break;
            case 27:
                player.addEffect( PotionEffect.POISON, 1, 22, TimeUnit.SECONDS );
                break;
            case 28:
                player.addEffect( PotionEffect.REGENERATION, 0, 45, TimeUnit.SECONDS );
                break;
            case 29:
                player.addEffect( PotionEffect.REGENERATION, 0, 2, TimeUnit.MINUTES );
                break;
            case 30:
                player.addEffect( PotionEffect.REGENERATION, 1, 22, TimeUnit.SECONDS );
                break;
            case 31:
                player.addEffect( PotionEffect.STRENGTH, 0, 3, TimeUnit.SECONDS );
                break;
            case 32:
                player.addEffect( PotionEffect.STRENGTH, 0, 8, TimeUnit.SECONDS );
                break;
            case 33:
                player.addEffect( PotionEffect.STRENGTH, 1, 90, TimeUnit.SECONDS );
                break;
            case 34:
                player.addEffect( PotionEffect.WEAKNESS, 0, 90, TimeUnit.SECONDS );
                break;
            case 35:
                player.addEffect( PotionEffect.WEAKNESS, 0, 4, TimeUnit.MINUTES );
                break;
            case 36:
                player.addEffect( PotionEffect.WITHER, 1, 40, TimeUnit.SECONDS );
                break;
            case 37:
                player.addEffect( PotionEffect.SLOWNESS, 3, 20, TimeUnit.SECONDS );
                player.addEffect( PotionEffect.DAMAGE_RESISTANCE, 2, 20, TimeUnit.SECONDS );
                break;
            case 38:
                player.addEffect( PotionEffect.SLOWNESS, 3, 40, TimeUnit.SECONDS );
                player.addEffect( PotionEffect.DAMAGE_RESISTANCE, 2, 40, TimeUnit.SECONDS );
                break;
            case 39:
                player.addEffect( PotionEffect.SLOWNESS, 5, 20, TimeUnit.SECONDS );
                player.addEffect( PotionEffect.DAMAGE_RESISTANCE, 3, 20, TimeUnit.SECONDS );
                break;
        }
    }

}
