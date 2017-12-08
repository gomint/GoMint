package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemLeatherChestplate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemLeatherChestplateGenerator implements ItemGenerator {

   @Override
   public ItemLeatherChestplate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemLeatherChestplate( data, amount, nbt );
   }

   @Override
   public ItemLeatherChestplate generate( short data, byte amount ) {
       return new ItemLeatherChestplate( data, amount );
   }

}
