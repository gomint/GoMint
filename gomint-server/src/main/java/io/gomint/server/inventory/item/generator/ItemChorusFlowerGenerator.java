package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemChorusFlower;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemChorusFlowerGenerator implements ItemGenerator {

   @Override
   public ItemChorusFlower generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemChorusFlower( data, amount, nbt );
   }

   @Override
   public ItemChorusFlower generate( short data, byte amount ) {
       return new ItemChorusFlower( data, amount );
   }

}
