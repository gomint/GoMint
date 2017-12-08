package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPoppedChorusFruit;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPoppedChorusFruitGenerator implements ItemGenerator {

   @Override
   public ItemPoppedChorusFruit generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPoppedChorusFruit( data, amount, nbt );
   }

   @Override
   public ItemPoppedChorusFruit generate( short data, byte amount ) {
       return new ItemPoppedChorusFruit( data, amount );
   }

}
