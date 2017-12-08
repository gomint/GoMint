package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemCobblestoneStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemCobblestoneStairsGenerator implements ItemGenerator {

   @Override
   public ItemCobblestoneStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemCobblestoneStairs( data, amount, nbt );
   }

   @Override
   public ItemCobblestoneStairs generate( short data, byte amount ) {
       return new ItemCobblestoneStairs( data, amount );
   }

}
