package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemIronChestplate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemIronChestplateGenerator implements ItemGenerator {

   @Override
   public ItemIronChestplate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemIronChestplate( data, amount, nbt );
   }

   @Override
   public ItemIronChestplate generate( short data, byte amount ) {
       return new ItemIronChestplate( data, amount );
   }

}
