package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemEnderChest;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemEnderChestGenerator implements ItemGenerator {

   @Override
   public ItemEnderChest generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemEnderChest( data, amount, nbt );
   }

   @Override
   public ItemEnderChest generate( short data, byte amount ) {
       return new ItemEnderChest( data, amount );
   }

}
