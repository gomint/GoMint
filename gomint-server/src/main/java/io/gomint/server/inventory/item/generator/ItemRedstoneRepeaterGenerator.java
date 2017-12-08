package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneRepeater;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneRepeaterGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneRepeater generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneRepeater( data, amount, nbt );
   }

   @Override
   public ItemRedstoneRepeater generate( short data, byte amount ) {
       return new ItemRedstoneRepeater( data, amount );
   }

}
