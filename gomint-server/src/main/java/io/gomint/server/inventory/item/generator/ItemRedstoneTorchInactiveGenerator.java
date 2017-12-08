package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneTorchInactive;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneTorchInactiveGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneTorchInactive generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneTorchInactive( data, amount, nbt );
   }

   @Override
   public ItemRedstoneTorchInactive generate( short data, byte amount ) {
       return new ItemRedstoneTorchInactive( data, amount );
   }

}
