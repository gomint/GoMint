package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemRedstoneTorchActive;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemRedstoneTorchActiveGenerator implements ItemGenerator {

   @Override
   public ItemRedstoneTorchActive generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemRedstoneTorchActive( data, amount, nbt );
   }

   @Override
   public ItemRedstoneTorchActive generate( short data, byte amount ) {
       return new ItemRedstoneTorchActive( data, amount );
   }

}
