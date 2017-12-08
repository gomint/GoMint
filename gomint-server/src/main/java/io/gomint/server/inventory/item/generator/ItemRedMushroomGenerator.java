package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedMushroom;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedMushroomGenerator implements ItemGenerator {

   @Override
   public ItemRedMushroom generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedMushroom( data, amount, nbt );
   }

   @Override
   public ItemRedMushroom generate( short data, byte amount ) {
       return new ItemRedMushroom( data, amount );
   }

}
