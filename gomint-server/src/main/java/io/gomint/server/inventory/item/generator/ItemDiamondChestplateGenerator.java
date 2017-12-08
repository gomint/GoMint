package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemDiamondChestplate;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemDiamondChestplateGenerator implements ItemGenerator {

   @Override
   public ItemDiamondChestplate generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemDiamondChestplate( data, amount, nbt );
   }

   @Override
   public ItemDiamondChestplate generate( short data, byte amount ) {
       return new ItemDiamondChestplate( data, amount );
   }

}
