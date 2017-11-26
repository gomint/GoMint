package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedSandstone;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedSandstoneGenerator implements ItemGenerator {

   @Override
   public ItemRedSandstone generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedSandstone( data, amount, nbt );
   }

   @Override
   public ItemRedSandstone generate( short data, byte amount ) {
       return new ItemRedSandstone( data, amount );
   }

}
