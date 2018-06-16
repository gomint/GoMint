package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemTorch;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemTorchGenerator implements ItemGenerator {

   @Override
   public ItemTorch generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemTorch( data, amount, nbt );
   }

   @Override
   public ItemTorch generate( short data, byte amount ) {
       return new ItemTorch( data, amount );
   }

}
