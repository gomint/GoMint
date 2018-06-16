package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFarmland;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFarmlandGenerator implements ItemGenerator {

   @Override
   public ItemFarmland generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFarmland( data, amount, nbt );
   }

   @Override
   public ItemFarmland generate( short data, byte amount ) {
       return new ItemFarmland( data, amount );
   }

}
