package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemPurpurStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemPurpurStairsGenerator implements ItemGenerator {

   @Override
   public ItemPurpurStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemPurpurStairs( data, amount, nbt );
   }

   @Override
   public ItemPurpurStairs generate( short data, byte amount ) {
       return new ItemPurpurStairs( data, amount );
   }

}
