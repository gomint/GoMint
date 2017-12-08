package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemSandstone;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemSandstoneGenerator implements ItemGenerator {

   @Override
   public ItemSandstone generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemSandstone( data, amount, nbt );
   }

   @Override
   public ItemSandstone generate( short data, byte amount ) {
       return new ItemSandstone( data, amount );
   }

}
