package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLingeringPotion;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLingeringPotionGenerator implements ItemGenerator {

   @Override
   public ItemLingeringPotion generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLingeringPotion( data, amount, nbt );
   }

   @Override
   public ItemLingeringPotion generate( short data, byte amount ) {
       return new ItemLingeringPotion( data, amount );
   }

}
