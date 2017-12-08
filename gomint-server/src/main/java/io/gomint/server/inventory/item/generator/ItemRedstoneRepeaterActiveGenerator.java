package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneRepeaterActive;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneRepeaterActiveGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneRepeaterActive generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneRepeaterActive( data, amount, nbt );
   }

   @Override
   public ItemRedstoneRepeaterActive generate( short data, byte amount ) {
       return new ItemRedstoneRepeaterActive( data, amount );
   }

}
