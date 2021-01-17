package io.gomint.server.inventory.item;
import io.gomint.inventory.item.ItemType;

import io.gomint.entity.potion.PotionEffect;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.registry.RegisterInfo;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:golden_apple" )
public class ItemGoldenApple extends ItemFood<io.gomint.inventory.item.ItemGoldenApple> implements io.gomint.inventory.item.ItemGoldenApple {



    @Override
    public float getSaturation() {
        return 1.2F;
    }

    @Override
    public float getHunger() {
        return 4;
    }

    @Override
    public void onConsume( EntityPlayer player ) {
        super.onConsume( player );

        // Apply effects
        player.effect( PotionEffect.ABSORPTION, 0, 2, TimeUnit.MINUTES );
        player.effect( PotionEffect.REGENERATION, 1, 5, TimeUnit.SECONDS );
    }

    @Override
    public ItemType itemType() {
        return ItemType.GOLDEN_APPLE;
    }

}
