package io.gomint.server.inventory.item.generator;

import io.gomint.server.inventory.item.ItemStoneBrickStairs;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public class ItemStoneBrickStairsGenerator implements ItemGenerator {

   @Override
   public ItemStoneBrickStairs generate( short data, byte amount, NBTTagCompound nbt ) {
       return new ItemStoneBrickStairs( data, amount, nbt );
   }

   @Override
   public ItemStoneBrickStairs generate( short data, byte amount ) {
       return new ItemStoneBrickStairs( data, amount );
   }

}
