package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemChorusFruit;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemChorusFruitGenerator implements ItemGenerator {

   @Override
   public ItemChorusFruit generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemChorusFruit( data, amount, nbt );
   }

   @Override
   public ItemChorusFruit generate( short data, byte amount ) {
       return new ItemChorusFruit( data, amount );
   }

}
