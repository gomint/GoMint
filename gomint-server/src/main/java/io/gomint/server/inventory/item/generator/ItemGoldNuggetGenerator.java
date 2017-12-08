package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldNugget;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldNuggetGenerator implements ItemGenerator {

   @Override
   public ItemGoldNugget generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldNugget( data, amount, nbt );
   }

   @Override
   public ItemGoldNugget generate( short data, byte amount ) {
       return new ItemGoldNugget( data, amount );
   }

}
