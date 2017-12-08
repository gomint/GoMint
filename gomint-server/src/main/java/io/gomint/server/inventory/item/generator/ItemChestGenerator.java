package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemChest;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemChestGenerator implements ItemGenerator {

   @Override
   public ItemChest generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemChest( data, amount, nbt );
   }

   @Override
   public ItemChest generate( short data, byte amount ) {
       return new ItemChest( data, amount );
   }

}
