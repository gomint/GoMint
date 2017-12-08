package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDeadBush;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDeadBushGenerator implements ItemGenerator {

   @Override
   public ItemDeadBush generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDeadBush( data, amount, nbt );
   }

   @Override
   public ItemDeadBush generate( short data, byte amount ) {
       return new ItemDeadBush( data, amount );
   }

}
