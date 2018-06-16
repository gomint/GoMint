package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFence;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFenceGenerator implements ItemGenerator {

   @Override
   public ItemFence generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFence( data, amount, nbt );
   }

   @Override
   public ItemFence generate( short data, byte amount ) {
       return new ItemFence( data, amount );
   }

}
