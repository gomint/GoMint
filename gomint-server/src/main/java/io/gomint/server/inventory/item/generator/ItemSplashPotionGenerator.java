package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSplashPotion;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSplashPotionGenerator implements ItemGenerator {

   @Override
   public ItemSplashPotion generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSplashPotion( data, amount, nbt );
   }

   @Override
   public ItemSplashPotion generate( short data, byte amount ) {
       return new ItemSplashPotion( data, amount );
   }

}
