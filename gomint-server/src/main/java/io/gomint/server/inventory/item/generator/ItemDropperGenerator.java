package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDropper;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDropperGenerator implements ItemGenerator {

   @Override
   public ItemDropper generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDropper( data, amount, nbt );
   }

   @Override
   public ItemDropper generate( short data, byte amount ) {
       return new ItemDropper( data, amount );
   }

}
