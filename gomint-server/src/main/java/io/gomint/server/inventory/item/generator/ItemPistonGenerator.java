package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPiston;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPistonGenerator implements ItemGenerator {

   @Override
   public ItemPiston generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPiston( data, amount, nbt );
   }

   @Override
   public ItemPiston generate( short data, byte amount ) {
       return new ItemPiston( data, amount );
   }

}
