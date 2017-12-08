package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPumpkin;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPumpkinGenerator implements ItemGenerator {

   @Override
   public ItemPumpkin generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPumpkin( data, amount, nbt );
   }

   @Override
   public ItemPumpkin generate( short data, byte amount ) {
       return new ItemPumpkin( data, amount );
   }

}
