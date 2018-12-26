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

    private static final int WATER_BOTTLE = 0;
    private static final int MUNDANE = 1;
    private static final int LONG_MUNDANE = 2;
    private static final int THICK = 3;
    private static final int AWKWARD = 4;
    private static final int NIGHT_VISION = 5;
    private static final int NIGHT_VISION_LONG = 6;
    private static final int INVISIBILITY = 7;
    private static final int INVISIBILITY_LONG = 8;
    private static final int LEAPING = 9;
    private static final int LEAPING_LONG = 10;
    private static final int LEAPING_II = 11;
    private static final int FIRE_RESISTANCE = 12;
    private static final int FIRE_RESISTANCE_LONG = 13;
    private static final int SWIFTNESS = 14;
    private static final int SWIFTNESS_LONG = 15;
    private static final int SWIFTNESS_II = 16;
    private static final int SLOWNESS = 17;
    private static final int SLOWNESS_LONG = 18;
    private static final int WATER_BREATHING = 19;
    private static final int WATER_BREATHING_LONG = 20;
    private static final int HEALING = 21;
    private static final int HEALING_II = 22;
    private static final int HARMING = 23;
    private static final int HARMING_II = 24;
    private static final int POISON = 25;
    private static final int POISON_LONG = 26;
    private static final int POISON_II = 27;
    private static final int REGENERATION = 28;
    private static final int REGENERATION_LONG = 29;
    private static final int REGENERATION_II = 30;
    private static final int STRENGTH = 31;
    private static final int STRENGTH_LONG = 32;
    private static final int STRENGTH_II = 33;
    private static final int WEAKNESS = 34;
    private static final int WEAKNESS_LONG = 35;
    private static final int DECAY = 36;
    private static final int TURTLE_MASTER = 37;
    private static final int TURTLE_MASTER_LONG = 38;
    private static final int TURTLE_MASTER_II = 39;

    @Override
    public ItemType getType() {
        return ItemType.POTION;
    }

    @Override
    public void onConsume( EntityPlayer player ) {
        switch ( this.getData() ) {
            case WATER_BOTTLE:
            case MUNDANE:
            case LONG_MUNDANE:
            case THICK:
            case AWKWARD:
                // These potions do not have any effects
                break;
            case NIGHT_VISION:
                player.addEffect( PotionEffect.NIGHT_VISION, 0, 3, TimeUnit.MINUTES );
                break;
            case NIGHT_VISION_LONG:
                player.addEffect( PotionEffect.NIGHT_VISION, 0, 8, TimeUnit.MINUTES );
                break;
            case INVISIBILITY:
                player.addEffect( PotionEffect.INVISIBILITY, 0, 3, TimeUnit.MINUTES );
                break;
            case INVISIBILITY_LONG:
                player.addEffect( PotionEffect.INVISIBILITY, 0, 8, TimeUnit.MINUTES );
                break;
            case LEAPING:
                player.addEffect( PotionEffect.JUMP, 0, 3, TimeUnit.MINUTES );
                break;
            case LEAPING_LONG:
                player.addEffect( PotionEffect.JUMP, 0, 8, TimeUnit.MINUTES );
                break;
            case LEAPING_II:
                player.addEffect( PotionEffect.JUMP, 1, 90, TimeUnit.SECONDS );
                break;
            case FIRE_RESISTANCE:
                player.addEffect( PotionEffect.FIRE_RESISTANCE, 0, 3, TimeUnit.MINUTES );
                break;
            case FIRE_RESISTANCE_LONG:
                player.addEffect( PotionEffect.FIRE_RESISTANCE, 0, 8, TimeUnit.MINUTES );
                break;
            case SWIFTNESS:
                player.addEffect( PotionEffect.SPEED, 0, 3, TimeUnit.MINUTES );
                break;
            case SWIFTNESS_LONG:
                player.addEffect( PotionEffect.SPEED, 0, 8, TimeUnit.MINUTES );
                break;
            case SWIFTNESS_II:
                player.addEffect( PotionEffect.SPEED, 1, 90, TimeUnit.SECONDS );
                break;
            case SLOWNESS:
                player.addEffect( PotionEffect.SLOWNESS, 0, 90, TimeUnit.SECONDS );
                break;
            case SLOWNESS_LONG:
                player.addEffect( PotionEffect.SLOWNESS, 0, 4, TimeUnit.MINUTES );
                break;
            case WATER_BREATHING:
                player.addEffect( PotionEffect.WATER_BREATHING, 0, 3, TimeUnit.MINUTES );
                break;
            case WATER_BREATHING_LONG:
                player.addEffect( PotionEffect.WATER_BREATHING, 0, 8, TimeUnit.MINUTES );
                break;
            case HEALING:
                player.setHealth( player.getHealth() + 4 );
                break;
            case HEALING_II:
                player.setHealth( player.getHealth() + 8 );
                break;
            case HARMING:
                player.attack( 4, EntityDamageEvent.DamageSource.HARM_EFFECT );
                break;
            case HARMING_II:
                player.attack( 8, EntityDamageEvent.DamageSource.HARM_EFFECT );
                break;
            case POISON:
                player.addEffect( PotionEffect.POISON, 0, 45, TimeUnit.SECONDS );
                break;
            case POISON_LONG:
                player.addEffect( PotionEffect.POISON, 0, 2, TimeUnit.MINUTES );
                break;
            case POISON_II:
                player.addEffect( PotionEffect.POISON, 1, 22, TimeUnit.SECONDS );
                break;
            case REGENERATION:
                player.addEffect( PotionEffect.REGENERATION, 0, 45, TimeUnit.SECONDS );
                break;
            case REGENERATION_LONG:
                player.addEffect( PotionEffect.REGENERATION, 0, 2, TimeUnit.MINUTES );
                break;
            case REGENERATION_II:
                player.addEffect( PotionEffect.REGENERATION, 1, 22, TimeUnit.SECONDS );
                break;
            case STRENGTH:
                player.addEffect( PotionEffect.STRENGTH, 0, 3, TimeUnit.MINUTES );
                break;
            case STRENGTH_LONG:
                player.addEffect( PotionEffect.STRENGTH, 0, 8, TimeUnit.MINUTES );
                break;
            case STRENGTH_II:
                player.addEffect( PotionEffect.STRENGTH, 1, 90, TimeUnit.SECONDS );
                break;
            case WEAKNESS:
                player.addEffect( PotionEffect.WEAKNESS, 0, 90, TimeUnit.SECONDS );
                break;
            case WEAKNESS_LONG:
                player.addEffect( PotionEffect.WEAKNESS, 0, 4, TimeUnit.MINUTES );
                break;
            case DECAY:
                player.addEffect( PotionEffect.WITHER, 1, 40, TimeUnit.SECONDS );
                break;
            case TURTLE_MASTER:
                player.addEffect( PotionEffect.SLOWNESS, 3, 20, TimeUnit.SECONDS );
                player.addEffect( PotionEffect.DAMAGE_RESISTANCE, 2, 20, TimeUnit.SECONDS );
                break;
            case TURTLE_MASTER_LONG:
                player.addEffect( PotionEffect.SLOWNESS, 3, 40, TimeUnit.SECONDS );
                player.addEffect( PotionEffect.DAMAGE_RESISTANCE, 2, 40, TimeUnit.SECONDS );
                break;
            case TURTLE_MASTER_II:
                player.addEffect( PotionEffect.SLOWNESS, 5, 20, TimeUnit.SECONDS );
                player.addEffect( PotionEffect.DAMAGE_RESISTANCE, 3, 20, TimeUnit.SECONDS );
                break;
        }
    }

}
