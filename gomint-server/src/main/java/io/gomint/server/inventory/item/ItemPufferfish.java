package io.gomint.server.inventory.item;

import io.gomint.entity.potion.PotionEffect;
import io.gomint.inventory.item.ItemType;

import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 462 )
public class ItemPufferfish extends ItemFood implements io.gomint.inventory.item.ItemPufferfish {



    @Override
    public float getSaturation() {
        return 0.1f;
    }

    @Override
    public float getHunger() {
        return 1;
    }

    @Override
    public void onConsume( EntityPlayer player ) {
        super.onConsume( player );

        // Apply effects
        player.addEffect( PotionEffect.NAUSEA, 1, 15, TimeUnit.SECONDS );
        player.addEffect( PotionEffect.HUNGER, 2, 15, TimeUnit.SECONDS );
        player.addEffect( PotionEffect.POISON, 3, 1, TimeUnit.MINUTES );
    }

    @Override
    public ItemType getType() {
        return ItemType.PUFFERFISH;
    }

}
