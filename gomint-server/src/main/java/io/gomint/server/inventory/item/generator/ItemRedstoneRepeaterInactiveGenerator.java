package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneRepeaterInactive;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneRepeaterInactiveGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneRepeaterInactive generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneRepeaterInactive( data, amount, nbt );
   }

   @Override
   public ItemRedstoneRepeaterInactive generate( short data, byte amount ) {
       return new ItemRedstoneRepeaterInactive( data, amount );
   }

}
