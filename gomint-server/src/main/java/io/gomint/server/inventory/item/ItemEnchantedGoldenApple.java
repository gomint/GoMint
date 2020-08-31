package io.gomint.server.inventory.item;
import io.gomint.entity.potion.PotionEffect;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:appleenchanted", id = 466 )
public class ItemEnchantedGoldenApple extends ItemFood implements io.gomint.inventory.item.ItemEnchantedGoldenApple {



    @Override
    public float getSaturation() {
        return 1.2f;
    }

    @Override
    public float getHunger() {
        return 4;
    }

    @Override
    public void onConsume( EntityPlayer player ) {
        super.onConsume( player );

        // Apply effects
        player.addEffect( PotionEffect.REGENERATION, 4, 30, TimeUnit.SECONDS );
        player.addEffect( PotionEffect.DAMAGE_RESISTANCE, 0, 5, TimeUnit.MINUTES );
        player.addEffect( PotionEffect.FIRE_RESISTANCE, 0, 5, TimeUnit.MINUTES );
        player.addEffect( PotionEffect.ABSORPTION, 3, 2, TimeUnit.MINUTES );
    }

    @Override
    public ItemType getItemType() {
        return ItemType.ENCHANTED_GOLDEN_APPLE;
    }

}