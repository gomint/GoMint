package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlockMovedByPiston;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlockMovedByPistonGenerator implements ItemGenerator {

   @Override
   public ItemBlockMovedByPiston generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlockMovedByPiston( data, amount, nbt );
   }

   @Override
   public ItemBlockMovedByPiston generate( short data, byte amount ) {
       return new ItemBlockMovedByPiston( data, amount );
   }

}
