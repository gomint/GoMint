/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

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
@RegisterInfo( sId = "minecraft:enchanted_golden_apple", id = 259 )
public class ItemEnchantedGoldenApple extends ItemFood<io.gomint.inventory.item.ItemEnchantedGoldenApple> implements io.gomint.inventory.item.ItemEnchantedGoldenApple {

    @Override
    public ItemType itemType() {
        return ItemType.ENCHANTED_GOLDEN_APPLE;
    }

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
        player.effect( PotionEffect.REGENERATION, 4, 30, TimeUnit.SECONDS );
        player.effect( PotionEffect.DAMAGE_RESISTANCE, 0, 5, TimeUnit.MINUTES );
        player.effect( PotionEffect.FIRE_RESISTANCE, 0, 5, TimeUnit.MINUTES );
        player.effect( PotionEffect.ABSORPTION, 3, 2, TimeUnit.MINUTES );
    }

}
