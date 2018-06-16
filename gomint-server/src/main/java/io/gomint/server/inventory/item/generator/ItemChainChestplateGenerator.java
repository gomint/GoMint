package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemChainChestplate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemChainChestplateGenerator implements ItemGenerator {

   @Override
   public ItemChainChestplate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemChainChestplate( data, amount, nbt );
   }

   @Override
   public ItemChainChestplate generate( short data, byte amount ) {
       return new ItemChainChestplate( data, amount );
   }

}
