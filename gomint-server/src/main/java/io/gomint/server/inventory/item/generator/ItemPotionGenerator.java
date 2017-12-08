package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPotion;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPotionGenerator implements ItemGenerator {

   @Override
   public ItemPotion generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPotion( data, amount, nbt );
   }

   @Override
   public ItemPotion generate( short data, byte amount ) {
       return new ItemPotion( data, amount );
   }

}
