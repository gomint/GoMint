package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemBlockOfGold;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemBlockOfGoldGenerator implements ItemGenerator {

   @Override
   public ItemBlockOfGold generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemBlockOfGold( data, amount, nbt );
   }

   @Override
   public ItemBlockOfGold generate( short data, byte amount ) {
       return new ItemBlockOfGold( data, amount );
   }

}
