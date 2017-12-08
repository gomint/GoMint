package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemFire;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemFireGenerator implements ItemGenerator {

   @Override
   public ItemFire generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemFire( data, amount, nbt );
   }

   @Override
   public ItemFire generate( short data, byte amount ) {
       return new ItemFire( data, amount );
   }

}
