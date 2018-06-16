package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemNetherBrickFence;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemNetherBrickFenceGenerator implements ItemGenerator {

   @Override
   public ItemNetherBrickFence generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemNetherBrickFence( data, amount, nbt );
   }

   @Override
   public ItemNetherBrickFence generate( short data, byte amount ) {
       return new ItemNetherBrickFence( data, amount );
   }

}
