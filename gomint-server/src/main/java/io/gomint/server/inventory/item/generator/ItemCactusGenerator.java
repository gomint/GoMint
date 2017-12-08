package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCactus;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCactusGenerator implements ItemGenerator {

   @Override
   public ItemCactus generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCactus( data, amount, nbt );
   }

   @Override
   public ItemCactus generate( short data, byte amount ) {
       return new ItemCactus( data, amount );
   }

}
