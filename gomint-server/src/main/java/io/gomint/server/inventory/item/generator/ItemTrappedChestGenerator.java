package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemTrappedChest;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemTrappedChestGenerator implements ItemGenerator {

   @Override
   public ItemTrappedChest generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemTrappedChest( data, amount, nbt );
   }

   @Override
   public ItemTrappedChest generate( short data, byte amount ) {
       return new ItemTrappedChest( data, amount );
   }

}
