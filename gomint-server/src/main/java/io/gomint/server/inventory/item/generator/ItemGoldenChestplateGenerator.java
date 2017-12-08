package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemGoldenChestplate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemGoldenChestplateGenerator implements ItemGenerator {

   @Override
   public ItemGoldenChestplate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemGoldenChestplate( data, amount, nbt );
   }

   @Override
   public ItemGoldenChestplate generate( short data, byte amount ) {
       return new ItemGoldenChestplate( data, amount );
   }

}
